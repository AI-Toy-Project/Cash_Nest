"""
import keras
from keras.layers import TimeDistributed
import numpy as np
import pandas as pd
from keras.models import Sequential
from keras.layers import Dense, LSTM
from keras.layers import Dropout
from keras.callbacks import EarlyStopping, ModelCheckpoint,ReduceLROnPlateau
from keras import optimizers
from sklearn.model_selection import KFold
from sklearn.preprocessing import MinMaxScaler
import tensorflow as tf
import torch
from sklearn.metrics import r2_score
from tensorflow.keras.optimizers import RMSprop
from tensorflow.keras.callbacks import ReduceLROnPlateau
from keras import models
from keras.models import load_model
"""

import numpy as np
import pandas as pd
from sklearn.preprocessing import MinMaxScaler 
from keras.models import load_model

def load_and_preprocess_data(df):
    df.columns = ['거래일시', '정상구분', '가맹점명', '금액', '카테고리']
    df['거래일시'] = pd.to_datetime(df['거래일시'])
    return df


def handle_cancelled_rows(df):
    df = df[df['정상구분'] != '할인']
    df = df[df['정상구분'].notna()]

    cancelled_rows = df[df['정상구분'] == '취소']
    for index, row in cancelled_rows.iterrows():
        target_amount = -row['금액']
        same_merchant = df['가맹점명'] == row['가맹점명']
        same_amount = df['금액'] == target_amount
        is_normal = df['정상구분'] == '정상'

        current_month = row['거래일시'].month
        last_month = current_month - 1 if current_month > 1 else 12

        # 이번달에서 동일한 '정상' 결재건 찾기
        matched_rows_current_month = df[same_merchant & same_amount & is_normal & (df['거래일시'].dt.month == current_month)]
        if not matched_rows_current_month.empty:
            df.drop(matched_rows_current_month.index, inplace=True)
            df.drop(index, inplace=True)
            continue

        # 지난달에서 동일한 '정상' 결재건 찾기
        matched_rows_last_month = df[same_merchant & same_amount & is_normal & (df['거래일시'].dt.month == last_month)]
        if not matched_rows_last_month.empty:
            df.drop(matched_rows_last_month.index, inplace=True)
            df.drop(index, inplace=True)
            
    # 카테고리별 one-hot encoding
    df_onehot = pd.get_dummies(df['카테고리'])
    df_encoded = pd.concat([df['거래일시'], df['금액'], df_onehot], axis=1)

    # 일별, 카테고리별로 소비금액 합산
    df_sum = df_encoded.groupby('거래일시').apply(lambda x: x.iloc[:, 2:].multiply(x['금액'], axis=0).sum()).reset_index()

    # 원하는 카테고리 순서로 컬럼 재정렬
    columns_order = ['거래일시', '생활', '이동수단', '쇼핑', '취미생활', '교육', '의료', '음식', '여행']
    df_sum = df_sum[columns_order]

    df = pd.DataFrame(df_sum)
    df['거래일시'] = pd.to_datetime(df['거래일시'])
    df = df.set_index('거래일시').resample('D').sum().reset_index()
    
    return df


def normalize_data(df):
    scaler = MinMaxScaler()
    df_scaled = scaler.fit_transform(df.iloc[:, 1:])
    df_scaled = pd.DataFrame(df_scaled, columns=df.columns[1:])
    return df_scaled, scaler


def prepare_training_data(df_scaled, seq_length=92):
    X, y = [], []
    for i in range(len(df_scaled) - seq_length):
        X.append(df_scaled.iloc[i:i+seq_length].values)
        y.append(df_scaled.iloc[i+seq_length].values)
    return np.array(X), np.array(y)


def predict_next_month_consumption(df, df_scaled, model, scaler, seq_length=92):
    last_data = df_scaled[-seq_length:].values
    predicted = []

    for _ in range(30):  # Adjust the number of months you want to predict
        input_data = last_data[np.newaxis, ...]
        prediction = model.predict(input_data)
        predicted.append(prediction[0])

        # Include the predicted data for the next month
        last_data = np.concatenate((last_data[1:], prediction), axis=0)

    # Inverse transform and aggregate predicted results
    predicted = np.array(predicted)
    predicted_real = scaler.inverse_transform(predicted)

    # Aggregate predicted monthly consumption for each category
    categories = df.columns[1:]
    monthly_predicted = []

    for i, month in enumerate(predicted_real.T):  # Transpose to iterate over categories
        monthly_sum = month.sum()
        monthly_predicted.append(monthly_sum)
    
    return monthly_predicted


def get_monthly_prediction(df):
    df = load_and_preprocess_data(df)
    df = handle_cancelled_rows(df)
    df_scaled, scaler = normalize_data(df)
    X, y = prepare_training_data(df_scaled)
    model = load_model('lstm_first.h5')
    monthly_predicted = predict_next_month_consumption(df, df_scaled, model, scaler, seq_length=92)
    return monthly_predicted