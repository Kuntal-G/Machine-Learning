# Performing variable selection in linear regression
#----------------------------------
library(caret)
library(MASS)

auto <- read.csv("auto-mpg.csv")

auto$cylinders <- factor(auto$cylinders, levels = c(3,4,5,6,8), labels = c("3cyl", "4cyl", "5cyl", "6cyl", "8cyl"))

set.seed(1000)
t.idx <- createDataPartition(auto$mpg, p = 0.7, list = FALSE)

names(auto)

fit <- lm(mpg ~ ., data = auto[t.idx, -c(1,8,9)])

step.model <- stepAIC(fit, direction = "backward")

summary(step.model)
