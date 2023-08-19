from flask import Flask, request, jsonify
from flask_restful import Resource, Api

from classification import classify_and_save
from prediction import get_monthly_prediction
from collections import OrderedDict


app = Flask(__name__)

@app.route('/predict', methods=['GET'])
def predict():
    
    df_classified = classify_and_save(input_path='./최종데이터.xlsx')

    predicted_amounts = get_monthly_prediction(df_classified)
    
     # Adding dummy values
    predicted_amounts += [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000]
    max_value = max(predicted_amounts)
    
    # Creating dictionary from predicted_amounts
    percentages = [(amount / max_value) * 100 for amount in predicted_amounts]
    
    # Creating dictionary with the percentages
    categories_dict = {}
    for i, percentage in enumerate(percentages):
        # Formatting with leading zeros
        categories_dict[f"category{str(i+1).zfill(2)}"] = int(percentage)
    
    return jsonify(data=categories_dict)
    
@app.route('/amount', methods=['GET'])
def amount():
    return jsonify({"amount": 120560})
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)