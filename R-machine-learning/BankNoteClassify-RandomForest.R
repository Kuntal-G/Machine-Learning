# Using random forest models for classification
#-------------------------------------------------

library(randomForest)
library(caret)

bn <- read.csv("banknote-authentication.csv")
bn$class <- factor(bn$class)

set.seed(1000)
sub.idx <- createDataPartition(bn$class, p=0.7, list=FALSE)

mod <- randomForest(x = bn[sub.idx,1:4], y=bn[sub.idx,5],ntree=500, keep.forest=TRUE)

pred <- predict(mod, bn[-sub.idx,])

table(bn[-sub.idx,"class"], pred, dnn = c("Actual", "Predicted"))

probs <- predict(mod, bn[-sub.idx,], type = "prob")

pred <- prediction(probs[,2], bn[-sub.idx,"class"])
perf <- performance(pred, "tpr", "fpr")
plot(perf)