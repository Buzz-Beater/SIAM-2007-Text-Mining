from tgrocery import *
from DataPreProcess import *
def text_train(module_name):
    print "training"
    groceryUtils = Grocery(module_name)
    train_src = preprocess(TRAIN)
    groceryUtils.train(train_src)
    groceryUtils.save()

def text_predict(module_name):
    print "predicting"
    groceryUtils = Grocery(module_name)
    groceryUtils.load()
    (predict_src, truth) = preprocess(TEST)
    predict_value = []
    predict_confidence = []
    print len(predict_src)
    for test_entry in predict_src:
        rst = groceryUtils.predict(test_entry)
        rst_values = rst.dec_values
        label = []
        for index in range(len(rst_values)):
            if rst_values[index] > threshold:
                label.append("1")
            else:
                label.append("-1")
            global min_confidence
            if rst_values[index] < min_confidence:
                min_confidence = rst_values[index]
        predict_value.append(label)
        predict_confidence.append(rst_values)
    return predict_value, predict_confidence

def list2csv(result, file):
    print "formating results"
    texts = []
    for row in result:
        string = ",".join(row) + "\n"
        texts.append(string)
    file.writelines(texts)

module_name = "siam2007"
threshold = 0
min_confidence = 0
text_train(module_name)
(prediction, confidence) = text_predict(module_name)
print min_confidence
confidence_values = []
for conf in confidence:
    tmp = []
    for key in conf:
        tmp.append(str(conf[key] - min_confidence))
    confidence_values.append(tmp)
predict_out_file = open("ScoringSoftware/MyResult.csv", "w")
confidence_out_file = open("ScoringSoftware/MyConfidence.csv", "w")
list2csv(prediction, predict_out_file)
list2csv(confidence_values, confidence_out_file)