from flask import Flask,request,jsonify
import pickle

app = Flask(__name__)
model = pickle.load(open("svm.pkl","rb"))
import numpy as np

@app.route("/")
def home():
    return "Mental Health Test"

@app.route("/predict", methods=["POST"])
def predict():
    q1 = request.form.get("q1")
    q2 = request.form.get("q2")
    q3 = request.form.get("q3")
    q4 = request.form.get("q4")
    q5 = request.form.get("q5")
    q6 = request.form.get("q6")
    q7 = request.form.get("q7")
    q8 = request.form.get("q8")
    q9 = request.form.get("q9")
    q10 = request.form.get("q10")
    q11 = request.form.get("q11")
    q12 = request.form.get("q12")
    q13 = request.form.get("q13")
    q14 = request.form.get("q14")
    q15 = request.form.get("q15")

    question_array = np.array([[q1,q2,q3,q4,q5,q6,q7,q8,q9,q10,q11,q12,q13,q14,q15]])

    prediction = int(10-int(model.predict(question_array)[0]))

    return jsonify({"result":int(prediction)})

if __name__ == "__main__":
    app.run(debug = True)


