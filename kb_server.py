from flask import Flask, request, jsonify
from flask_restful import Resource, Api

app = Flask(__name__)

@app.route('/predict', methods=['GET'])
def predict():
    
    return jsonify({
        "category1": 100000,
        "category2": 150000,
        "category3": 155900,
        "category4": 200000,
        "category5": 250000,
        "category6": 255900,
        "category7": 300000,
        "category8": 350000,
    })

@app.route('/amount', methods=['GET'])
def amount():
    
    return jsonify({"amount": 120560})
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=True)


