#Classifying using the Naïve Bayes approach
#------------------------------------------------

library(e1071)
library(caret)

ep <- read.csv("electronics-purchase.csv")

set.seed(1000)
train.idx <- createDataPartition(ep$Purchase, p = 0.67, list = FALSE)

epmod <- naiveBayes(Purchase ~ . , data = ep[train.idx,])

epmod

pred <- predict(epmod, ep[-train.idx,])

tab <- table(ep[-train.idx,]$Purchase, pred, dnn = c("Actual", "Predicted"))

tab