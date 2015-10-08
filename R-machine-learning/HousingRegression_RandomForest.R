# Building random forest models for regression
#-----------------------------------------------------------------
library(randomForest)
library(caret)

bn <- read.csv("BostonHousing.csv")
set.seed(1000)
t.idx <- createDataPartition(bh$MEDV, p=0.7, list=FALSE)

mod <- randomForest(x = bh[t.idx,1:13], y=bh[t.idx,14],ntree=1000,  xtest = bh[-t.idx,1:13], ytest = bh[-t.idx,14], importance=TRUE, keep.forest=TRUE)

mod

mod$importance

plot(bh[t.idx,14], predict( mod, newdata=bh[t.idx,]), xlab = "Actual", ylab = "Predicted")

plot(bh[t.idx,14], mod$predicted, xlab = "Actual", ylab = "Predicted")

plot(bh[-t.idx,14], mod$test$predicted, xlab = "Actual", ylab = "Predicted")
