# Using AdaBoost to combine classification tree models


library(caret)
library(ada)

bn <- read.csv("banknote-authentication.csv")

bn$class <- factor(bn$class)
set.seed(1000)
t.idx <- createDataPartition(bn$class, p=0.7, list=FALSE)

cont <- rpart.control()

mod <- ada(class ~ ., data = bn[t.idx,], iter=50, loss="e", type="discrete", control = cont)

mod

pred <- predict(mod, newdata = bn[-t.idx,], type = "vector")

table(bn[-t.idx, "class"], pred, dnn = c("Actual", "Predicted"))