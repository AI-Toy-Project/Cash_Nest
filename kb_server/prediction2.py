
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
from keras.optimizers import rmsprop_v2
from tensorflow.keras.optimizers import Adam
from xgboost import XGBRegressor


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
    
    desired_categories = ["교육", "이동수단", "취미생활", "쇼핑", "생활", "여행", "음식", "의료"]

    df['카테고리'] = pd.Categorical(df['카테고리'], categories=desired_categories)
        
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

def sum_by_category(df):
    category_sums = df.drop(columns="거래일시").sum()
    sum_list = category_sums.tolist()
    
    return sum_list

def get_monthly_prediction2(df):
    df = load_and_preprocess_data(df)
    df = handle_cancelled_rows(df)
    result = sum_by_category(df)
    
    return result