#Read the csv into a data frame
churn_data<-read.csv("churn.all",header=T)

# View the data frame created:
View(churn_data)

# Summary of all active customers
summary_all<-summary(churn_data)
summary_churn<-summary(subset(churn_data,Status=='TRUE'))
summary_active<-summary(subset(churn_data,Status=='FALSE'))

# Combine all the data frames into one file
write.csv(rbind(summary_all,summary_churn,summary_active),file="summary_file.csv")

#The pattern to observe while looking at the summary file is to observe substantial difference between the summaries of churn and active customers,especially themean, median, and the 1st and 3rd quartile.

# Check the correlation between numerical variables
cor_data<-churn_data
cor_data$Status<-NULL
cor_data$voice.mail.plan<-NULL
cor_data$international.plan <-NULL
cor_data$phone.number<-NULL
cor_data$state<-NULL

# Calculate the correlation, which returns a correlation matrix
correlation_all<-cor(cor_data)
write.csv(correlation_all,file="correlation_file.csv")

# Looking at correlation_file.csv , we can see that four pairs of columns are heavily correlated and we should remove them:
churn_data$total.day.charge<-NULL
churn_data$total.eve.charge<-NULL
churn_data$total.night.charge<-NULL
churn_data$total.intl.charge<-NULL

# We will remove the features phone number and state.We need to remove these columns from all the files.Another way of removing correlation is to perform dimensionality reduction such as PCA(preferred when feature set is large)
churn_data$state<-NULL
churn_data$phone.number<-NULL
write.csv(churn_data,file="churn_data_clean.all.csv",row.names = F)

#Feature Enrichment;
#The features like total day calls and total eve calls measure frequency of usage whereas features such as total day minutes and total eve minutes measure volume of usage. Another interesting feature to look at would be the average minutes per call.We can measure the average by dividing the total minutes by total calls, for example,the feature average minutes per day call = total day minutes / total day calls and similarly,average minutes per eve call = total eve minutes/ total eve calls.
churn_data$avg.minute.day <- churn_data$total.day.minutes/churn_data$total.day.calls
churn_data$avg.minute.eve <- churn_data$total.eve.minutes/churn_data$total.eve.calls

churn_data$avg.minute.night <- churn_data$total.night.minutes/churn_data$total.night.calls
churn_data$avg.minute.intl <- churn_data$total.intl.minutes/churn_data$total.intl.calls

# Spliting Train and Test Data using caTools/Caret
library (caret)
set.seed (111)
trainIndex <- createDataPartition (churn_data$Status, p = .7,list = FALSE,times = 1)

train <- churn_data[trainIndex, ]
test <- churn_data[-trainIndex, ]

# See the distribution of churn and active accounts across the train and test sets
table(train$Status)
table(test$Status)

# Save the train & test sets as csv files
write.csv(train,file="churn_data_clean.all.csv",row.names = F)
write.csv(test,file="churn_data_clean_test.all.csv",row.names = F)

# CHURN MODEL

churnTrain<-read.csv("churn_data_clean.all.csv")
churnTest<-read.csv("churn_data_clean_test.all.csv")

#Using RandomForest
library(randomForest)
churnRF <-randomForest(Status~.,data=churnTrain,ntree=100,proximity=TRUE)

#Accuracy on Training set
table(predict(churnRF),churnTrain$Status)


#Accuracy on Test set
churnPred<-predict(churnRF,newdata=churnTest)
table(churnPred, churnTest$Status)
