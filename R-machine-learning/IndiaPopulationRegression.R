#############Regression in India Population####################################
library(ggplot2)
library(caret)
library(splines)
library(mgcv)


data = read.csv("/home/kuntal/Downloads/pop.csv")
str(data) 


qplot(year,population,color=year,data=data)


model_plot = lm(year~population,data=data)
summary(model_plot)

#it leads to non linear plot for our data
qplot(year,population,color=year,data=data,geom=c("point","smooth"), span=1)


##following lm is meant for straight line , but our data doesn't suit that
qplot(year,population,color=year,data=model_plot,geom=c("point","smooth"), method="lm") 

#so spline package will help here
dat <- gamSim(1,n=150,scale=2)

#removing all NA from data
noNadata <- data[complete.cases(data),]

#READ ABOUT KNOTS
#http://stats.stackexchange.com/questions/7316/setting-knots-in-natural-cubic-splines-in-r
#I increased it to 50 , because here finally I found an expected output properly
model_smooth <- smooth.spline(noNadata$population~noNadata$year,nknots=50)


qplot(year,population,color=year,data=model_smooth,geom=c("point","smooth"), span=1)
##Now we can check the confidence level
newplotdata <- data.frame(year=2090)
predict(model_smooth, newplotdata) 


