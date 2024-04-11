from flask import Flask, flash, request, redirect, url_for, render_template, jsonify
import os
import tensorflow
from werkzeug.utils import secure_filename
import requests
import json
import base64
from keras.models import load_model
from keras.preprocessing.image import load_img , img_to_array
import cv2
from chat import get_response
from chat_hindi import get_response_hindi
import numpy as np
app = Flask(__name__,static_url_path='/static',template_folder='templates')
UPLOAD_FOLDER = 'static/uploads/'  # Define the upload folder where images will be stored.

app.secret_key = "secret key"
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 16 * 1024 * 1024
f = open('info.json')
class_dic = json.load(f)
plant = ""
ALLOWED_EXTENSIONS = set(['png', 'jpg', 'jpeg', 'gif'])

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@app.route('/')
def home():
    return render_template('loading.html')
@app.route('/still')
def still():
    return render_template('stilllogo.html')
@app.route('/entry')
def anime():
    return render_template('logo.html')
@app.route('/test')
def tested():
    return render_template('test.html')
@app.route('/process_value', methods=['POST'])
def process_value():
    global model
    global classes
    value = request.form.get('value')
    # Process the value as needed
    global plant
    plant = value
    print(f'Received value: {value}')
    print(type(plant))
    model = load_model("C:/Users/Isha Desai/Downloads/demo1 (5)/demo1/models/"+plant+'_cnn_model.h5')
    classes = list(range(len(class_dic[plant])))
    print(classes)
    return jsonify({'message': 'Value received successfully'})


final_class = ""
def predict(filename , model):
    global final_class
    print('hi')
    print(class_dic[plant])
    img_array=cv2.imread(filename)
    #print(img_array)
    img=cv2.resize(img_array,(28,28))
    new_arr = np.array(img, dtype=np.uint8).reshape(-1,28,28,3)
    print('hello')
    result = model.predict(new_arr)
    print(result)
    dict_result = {}
    for i in range(len(class_dic[plant])):
        dict_result[result[0][i]] = classes[i]

    res = result[0]
    res.sort()
    res = res[::-1]
    prob = res[:3]
    global class_result
    prob_result = []
    class_result = []
    for i in range(3):
        prob_result.append((prob[i]*100).round(2))
        class_result.append(dict_result[prob[i]])
        print(class_result)
        print('isha')
    print('meow')
    print(plant)
    print(0)
    print(class_dic[plant])
    print(4)
    final_class = class_dic[plant][str(class_result[0])]
    print(final_class)
    return class_result , prob_result


@app.route('/upload', methods=['POST'])
def upload():
    try:
        # Get the data URL from the request
        data_url = request.form['imageDataUrl']

        # Extract the base64-encoded image data
        _, encoded_data = data_url.split(',', 1)
        image_data = base64.b64decode(encoded_data)

        # Save the image to the 'uploads' folder
        file_path = os.path.join('uploads', 'uploaded_image.png')
        with open(file_path, 'wb') as f:
            f.write(image_data)
        class_result , prob_result = predict('C:/Users/Isha Desai/Downloads/demo1 (5)/demo1/uploads/uploaded_image.png' , model)
        print(class_result)

        return 'done'

    except Exception as e:
        return f'Error: {str(e)}'
    
@app.route('/getanalysisandtitle', methods=['GET'])
def getanalysisandtitle():
    print(3)
    print(2)
    final_class = class_dic[plant][str(class_result[0])]
    print(final_class)
    title = final_class['title']
    defn = final_class['def']
    return jsonify({'title': title,
                    'def': defn})

@app.route('/predict', methods=['POST'])
def predicted():
    data = request.get_json()
    message = data['message']


    language = detect_language(message)
    
    if language == 'hindi':
        response = get_response_hindi(message)
    else:
        response = get_response(message)

    return jsonify({'answer': response})

def detect_language(text):
    
    #  i assume it's Hindi if the text contains Devanagari script characters
    if any('\u0900' <= char <= '\u097F' for char in text):
        return 'hindi'
    else:
        return 'english'


if __name__ == "__main__":
    os.makedirs('uploads', exist_ok=True)
    app.run(debug = True)
