import re
def preprocess(filepath):
    in_file = open(filepath, "r")
    texts = []
    for text in in_file.readlines():
        texts.append(text)
