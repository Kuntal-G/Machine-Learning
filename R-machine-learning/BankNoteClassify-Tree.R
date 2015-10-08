# Building plotting and evaluating classification trees
#------------------------------------------------------
library(ROCR)
library(rpart)
library(rpart.plot)
library(caret)

bn <- read.csv("banknote-authentication.csv")
set.seed(1000)
train.idx <- createDataPartition(bn$class, p = 0.7, list = FALSE)

mod <- rpart(class ~ ., data = bn[train.idx, ], method = "class", 
  control = rpart.control(minsplit = 20, cp = 0.01))
  
mod

prp(mod, type = 2, extra = 104, nn = TRUE, fallen.leaves = TRUE, 
  faclen = 4, varlen = 8, shadow.col = "gray")
  
mod$cptable

# Replace 5 on the following line with the appropriate value for your data
mod.pruned = prune(mod, mod$cptable[5, "CP"])

prp(mod.pruned, type = 2, extra = 104, nn = TRUE, fallen.leaves = TRUE, 
  faclen = 4, varlen = 8, shadow.col = "gray")
  
pred.pruned <- predict(mod, bn[-train.idx,], type = "class")

table(bn[-train.idx,]$class, pred.pruned, dnn = c("Actual", "Predicted"))


pred.pruned <- predict(mod, bn[-train.idx,], type = "prob")

pred <- prediction(pred.pruned[,2], bn[-train.idx,"class"])
perf <- performance(pred, "tpr", "fpr")
plot(perf)