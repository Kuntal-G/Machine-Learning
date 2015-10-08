# Building regression trees
#-------------------------------------------
library(rpart)
library(rpart.plot)
library(caret)

bh <- read.csv("BostonHousing.csv")

set.seed(1000)
t.idx <- createDataPartition(bh$MEDV, p=0.7, list = FALSE)

bfit <- rpart(MEDV ~ ., data = bh[t.idx,])

bfit

prp(bfit, type=2, nn=TRUE, fallen.leaves=TRUE, faclen=4, varlen=8, shadow.col="gray")

bfit$cptable

plotcp(bfit)

# in the command below, replacing the cp value based on thes results
bfitpruned <- prune(bfit, cp=0.01192653)
prp(bfitpruned, type=2, nn=TRUE, fallen.leaves=TRUE, faclen=4, varlen=8, shadow.col="gray")

preds.t <- predict(bfitpruned, bh[t.idx,])
sqrt(mean((preds.t-bh[t.idx,"MEDV"])^2))

preds.v <- predict(bfitpruned, bh[-t.idx,])
sqrt(mean((preds.v - bh[-t.idx,"MEDV"])^2))

fit <- rpart(MEDV ~ ., data = bh[t.idx,], control = rpart.control(minsplit = 10, cp = 0.001, minbucket = 5))

ed <- read.csv("education.csv")
ed$region <- factor(ed$region)
set.seed(1000)
t.idx <- createDataPartition(ed$expense, p = 0.7, list = FALSE)
fit <- rpart(expense ~ region+urban+income+under18, data = ed[t.idx,])
prp(fit, type=2, nn=TRUE, fallen.leaves=TRUE, faclen=4, varlen=8, shadow.col="gray")

