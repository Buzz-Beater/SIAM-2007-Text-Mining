import re
file_path = [
    "Data/TrainingData.txt",
    "Data/TestData.txt"
]
label_path = [
    "Data/TrainCategoryMatrix.csv",
    "Data/TestTruth.csv"
]
TRAIN = 0
TEST = 1
def text_filter(text, flag = False):
    text = text[:-2]
    for i in range(len(text)):
        if (text[i] == "~"):
            text = text[i + 1:]
            break
    text = re.sub("[_.]", " ", text)
    if flag:
        # filter out all CAPITAL characters
        text = re.sub("[A-Z]", " ", text)
    word_list = set(text.split())
    text = " ".join(word_list)
    return text

def preprocess(func):
    text_in_file = open(file_path[func], "r")
    texts = []
    label_in_file = open(label_path[func])
    labels = []
    for text in text_in_file.readlines():
        text = text_filter(text)
        texts.append(text)
    for row in label_in_file.readlines():
        row = row[:-2]
        label_list = row.split(",")
        cur_label = []
        for index in range(len(label_list)):
            if (label_list[index] == "1"):
                cur_label.append(index)
        labels.append(cur_label)
    if func == TRAIN:
        training_set = []
        for i in range(len(texts)):
            for label in labels[i]:
                training_entry = (label, texts[i])
                training_set.append(training_entry)
        return training_set
    else:
        return texts,labels
