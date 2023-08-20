from flask import Flask, request, jsonify
from flask_restful import Resource, Api

from classification import classify_and_save
from prediction import get_monthly_prediction
from prediction2 import get_monthly_prediction2
from collections import OrderedDict

import math

app = Flask(__name__)

@app.route('/predict', methods=['GET'])
def predict():
    
    df_classified = classify_and_save(input_path='./최종데이터.xlsx')
    predicted_amounts = get_monthly_prediction(df_classified)
    
    df_classified2 = classify_and_save(input_path='./현재소비데이터.xlsx')
    predicted_amounts2 = get_monthly_prediction2(df_classified2)
    
    
    # predicted_amounts 값을 교차로 합칩니다.
    combined_amounts = [val for pair in zip(predicted_amounts2, predicted_amounts) for val in pair]
    
    # 로그 변환 (1을 더하는 이유는 0에 로그를 취할 수 없기 때문입니다.)
    log_transformed = [math.log(amount + 1) for amount in combined_amounts]
    
    # 로그로 변환된 값의 최대 및 최소값을 구합니다.
    max_log_value = max(log_transformed)
    min_log_value = min(log_transformed)
    
    # 0-100 범위로 정규화
    percentages = [(log_value - min_log_value) / (max_log_value - min_log_value) * 100 for log_value in log_transformed]
    
    categories_dict = {}
    for i, percentage in enumerate(percentages):
        categories_dict[f"category{str(i+1).zfill(2)}"] = int(percentage)
    
    return jsonify({
        "category01": categories_dict["category01"],
        "category02": categories_dict["category02"],
        "category03": categories_dict["category03"],
        "category04": categories_dict["category04"],
        "category05": categories_dict["category05"],
        "category06": categories_dict["category06"],
        "category07": categories_dict["category07"],
        "category08": categories_dict["category08"],
        "category09": categories_dict["category09"],
        "category10": categories_dict["category10"],
        "category11": categories_dict["category11"],
        "category12": categories_dict["category12"],
        "category13": categories_dict["category13"],
        "category14": categories_dict["category14"],
        "category15": categories_dict["category15"],
        "category16": categories_dict["category16"]
    })
    
@app.route('/amount', methods=['GET'])
def amount():
    return jsonify({"amount": 120560})
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)