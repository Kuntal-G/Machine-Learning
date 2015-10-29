#Preparaaing Column names
colNames = c ("age", "workclass", "fnlwgt", "education", "educationnum", "maritalstatus", "occupation", "relationship", "race", "sex", "capitalgain","capitalloss", "hoursperweek", "nativecountry", "incomelevel") 


# Loading data

train = read.table ("adult.data", header = FALSE, sep = ",", strip.white = TRUE, col.names = colNames, na.strings = "?", stringsAsFactors = TRUE) 
test = read.table ("adult.test", header = FALSE, sep = ",", strip.white = TRUE, col.names = colNames, na.strings = "?", stringsAsFactors = TRUE)

#Check Structure of data
str (train)
table (complete.cases (train))

# Summarize all data sets with NAs only 
summary (train [!complete.cases(train),]) 

# Distribution of the income level factor in the entire training data set.
 table (train$incomelevel) 


## Cleaning Data set
#Data sets with NAs are removed below:

cleanTrain = train [!is.na (train$workclass) & !is.na (train$occupation), ]
cleanTrain = cleanTrain [!is.na (cleanTrain$nativecountry), ]
cleanTrain$fnlwgt = NULL



cleanTest = test [!is.na (test$workclass) & !is.na (test$occupation), ]
cleanTest = cleanTest [!is.na (cleanTest$nativecountry), ]
cleanTest$fnlwgt = NULL



##Dataset Exploration
library(ggplot2)
library(gridExtra)

#Explore age variable
summary (cleanTrain$age)
boxplot (age ~ incomelevel, data = cleanTrain, main = "Age distribution for different income levels", xlab = "Income Levels", ylab = "Age", col = "blue")


incomeBelow50K = (cleanTrain$incomelevel == "<=50K")
 xlimit = c (min (cleanTrain$age), max (cleanTrain$age))
 ylimit = c (0, 1600) 

#Histogram of age with respect to different income level
hist1 = qplot (age, data = cleanTrain[incomeBelow50K,], margins = TRUE, binwidth = 2, xlim = xlimit, ylim = ylimit, colour = incomelevel) 

hist2 = qplot (age, data = cleanTrain[!incomeBelow50K,], margins = TRUE, binwidth = 2, xlim = xlimit, ylim = ylimit, colour = incomelevel)

grid.arrange (hist1, hist2, nrow = 2)


#Explore the Years of Education Variable
summary (cleanTrain$educationnum)

boxplot (educationnum ~ incomelevel, data = cleanTrain, main = "Years of Education distribution for different income levels", xlab = "Income Levels", ylab = "Years of Education", col = "green") 


#Explore the Hours Per Week variable
summary (cleanTrain$hoursperweek)

boxplot (hoursperweek ~ incomelevel, data = cleanTrain, main = "Hours Per Week distribution for different income levels", xlab = "Income Levels", ylab = "Hours Per Week", col = "salmon") 


#Explore the correlation between continuous variables 
corMat = cor (cleanTrain[, c("age", "educationnum", "capitalgain", "capitalloss", "hoursperweek")]) 
corMat 


##Explore Categorical Variables

#Explore Sex variable
table (cleanTrain[,c("sex", "incomelevel")])

#Explore Workclass,occupation variables
qplot (incomelevel, data = cleanTrain, fill = workclass) + facet_grid (. ~ workclass) 
qplot (incomelevel, data = cleanTrain, fill = occupation) + facet_grid (. ~ occupation) 


##Model Building--Boosting
library(caret)
set.seed (32323) 

trCtrl = trainControl (method = "cv", number = 10) 

boostFit = train (incomelevel ~ age + workclass + education + educationnum + maritalstatus + occupation + relationship + race + capitalgain + capitalloss + hoursperweek + nativecountry, trControl = trCtrl, method = "gbm", data = cleanTrain, verbose = FALSE) 

#Confusion Matrix of Training data
confusionMatrix (cleanTrain$incomelevel, predict (boostFit, cleanTrain))



##Model Validation
cleanTest$predicted = predict (boostFit, cleanTest) confusionMatrix (cleanTest$incomelevel, cleanTest$predicted) 

#Validation Test set data

confusionMatrix (cleanTest$incomelevel, cleanTest$predicted) 


