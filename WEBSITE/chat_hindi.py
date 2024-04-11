import random
import json
import torch
from model import NeuralNet
from indicnlp.tokenize import indic_tokenize
from nltk_utils_hindi import bag_of_words, tokenize

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

with open('intents_hindi.json', 'r', encoding='utf-8') as json_data:
    intents = json.load(json_data)

FILE = "data_hindi.pth"
data = torch.load(FILE)

input_size = data["input_size"]
hidden_size = data["hidden_size"]
output_size = data["output_size"]
all_words = data['all_words']
tags = data['tags']
model_state = data["model_state"]

model = NeuralNet(input_size, hidden_size, output_size).to(device)
model.load_state_dict(model_state)
model.eval()

bot_name = "Jessica"

def tokenize_hindi(text):
    tokens = indic_tokenize.trivial_tokenize(text)
    return tokens

def get_response_hindi(msg):
    sentence = tokenize_hindi(msg)
    X = bag_of_words(sentence, all_words)
    X = X.reshape(1, X.shape[0])
    X = torch.from_numpy(X).to(device)

    output = model(X)
    _, predicted = torch.max(output, dim=1)

    tag = tags[predicted.item()]

    probs = torch.softmax(output, dim=1)
    prob = probs[0][predicted.item()]
    
    print(f"Tag: {tag}, Probability: {prob.item()}")

    if prob.item() > 0.75:
        for intent in intents['intents']:
            if tag == intent["tag"]:
                return random.choice(intent['responses'])

    return "मुझे समझ में नहीं आया..."

if __name__ == "__main__":
    print("चलो बातचीत करते हैं! (type 'quit' to exit)")
    while True:
        sentence = input("तुम: ")
        if sentence == "quit":
            break

        resp = get_response_hindi(sentence)
        print(resp)
