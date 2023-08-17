from flask import Flask, request, jsonify
from flask_restful import Resource, Api

from classification import classify_and_save
from prediction import get_monthly_prediction


app = Flask(__name__)

@app.route('/predict', methods=['GET'])
def predict():
    
    df_classified = classify_and_save(input_path='최종데이터.xlsx')
    predicted_amounts = get_monthly_prediction(df_classified)
    
    print(predicted_amounts)
    return jsonify(predicted_amounts)
    
    # return jsonify({
    #     "category1": 100000,
    #     "category2": 150000,
    #     "category3": 155900,
    #     "category4": 200000,
    #     "category5": 250000,
    #     "category6": 255900,
    #     "category7": 300000,
    #     "category8": 350000,
    #     "category9": 100000,
    #     "category10": 150000,
    #     "categor11": 155900,
    #     "category12": 200000,
    #     "category13": 250000,
    #     "category14": 255900,
    #     "category15": 300000,
    #     "category16": 350000,
    # })

@app.route('/amount', methods=['GET'])
def amount():
    
    return jsonify({"amount": 120560})
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)


