# Classifying using support vector machines
#-----------------------------------------------

library(e1071)
library(caret)

bn <- read.csv("banknote-authentication.csv")

bn$class <- factor(bn$class)

set.seed(1000)
t.idx <- createDataPartition(bn$class, p=0.7, list=FALSE)

mod <- svm(class ~ ., data = bn[t.idx,])

table(bn[t.idx,"class"], fitted(mod), dnn = c("Actual", "Predicted"))

pred <- predict(mod, bn[-t.idx,])
table(bn[-t.idx, "class"], pred, dnn = c("Actual", "Predicted"))

plot(mod, data=bn[t.idx,], skew ~ variance)

plot(mod, data=bn[-t.idx,], skew ~ variance)

mod <- svm(class ~ ., data = bn[t.idx,], class.weights=c("0"=0.3, "1"=0.7 ))