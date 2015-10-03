# Churn Analytics


[Churn Analytics](https://www.kuntalganguly.com)



The goal of churn analytics is to understand the primary drivers to churn and predict
churn. Churn can have a very specific meaning depending upon the industry or even
the organization we are talking about, but in general it is related to the extension of
the contract between a service provider and a subscriber.

Businesses need to have an effective strategy for managing customer churn because it
costs more to attract new customers than to retain existing ones. Customer churn can
take different forms, such as switching to a competitor’s service, reducing the time spent
using the service, reducing the number of services used, or switching to a lower-cost
service. Companies in the retail, media, telecommunication, and banking industries use
churn modeling to create better products, services, and experiences that lead to a higher
customer retention rate.Churn models enable companies to predict which customers are most likely to
churn, and to understand the factors that cause churn to occur.


In this dataset, the target variable is the last column, Status, which stores the
information about whether a user churned or not. True stands for churn customers and
False for active. We have a total of 4293 active and 707 churn customers in the dataset.
Let's have a look at the column definition; this is a good starting point to understand
a dataset:

Column Data Type
State Discrete
account length Continuous
area code Continuous
phone number Discrete
international plan Discrete
voice mail plan Discrete
number v-mail messages Continuous
total day minutes Continuous
total day calls Continuous
total day charge Continuous
total eve minutes Continuous
total eve calls Continuous
total eve charge Continuous
total night minutes Continuous
total night calls Continuous
total night charge Continuous
total intl minutes Continuous
total intl calls Continuous
total intl charge Continuous
number customer service calls Continuous
Status Discrete


The dataset, as seen in this table, has various telecom service usage metrics from
rows eight to 19. They cover attributes such as total number of calls, total charge,
and total minutes used by different slices of the data. The slices include time,
day or night, and usage type such as international call. Row 20 has the number
of customer service calls made and row 21 is the status of the subscriber, which
is our target variable.


train using logistic regression:
mahout trainlogistic --input churn_data_clean.all.csv --output
logistic_model --target Status --predictors account.length area.code
international.plan voice.mail.plan number.vmail.messages total.day.
minutes total.day.calls total.eve.minutes total.eve.calls total.night.
minutes total.night.calls total.intl.minutes total.intl.calls number.
customer.service.calls avg.minute.day avg.minute.eve avg.minute.night
avg.minute.intl --types n w w w n n n n n n n n n n n n n n --features 19
--passes 100 --rate 50 --categories 2


Then, we test the model using runlogistic , and we check the AUC and confusion
matrix over the training set:
mahout runlogistic --auc --confusion --input churn_data_clean.all.csv
--model logistic_model



we check the performance over the test set:
mahout runlogistic --auc --confusion --input churn_data_clean_test.all.
csv --model logistic_model


trainAdaptiveLogistic command to train an ensemble of logistic
regression. The configuration parameter passed is 100 passes over the data with
20 threads:
mahout trainAdaptiveLogistic --input churn_data_clean.all.csv --output
logistic_model --target Status --predictors account.length area.code
international.plan voice.mail.plan number.vmail.messages total.day.
minutes total.day.calls total.eve.minutes total.eve.calls total.night.
minutes total.night.calls total.intl.minutes total.intl.calls number.
customer.service.calls avg.minute.day avg.minute.eve avg.minute.night
avg.minute.intl --types n w w w n n n n n n n n n n n n n n --features 19
--passes 100 --categories 2 --threads 20


second step is to validate the model accuracy over the training dataset. We check
the AUC and confusion matrix for this purpose:
mahout validateAdaptiveLogistic --input churn_data_clean.all.csv --model
logistic_model --auc --confusion

The second step is to validate the model accuracy over the test dataset. We check the
AUC and confusion matrix for this purpose:
mahout validateAdaptiveLogistic --input churn_data_clean_test.all.csv
--model logistic_model --auc --confusion

random forest implementation in Mahout doesn't work with the header line,
we remove the header. We will use sed for this purpose:
sed -i '1d' churn_data_clean.all.csv
sed -i '1d' churn_data_clean_test.all.csv
The next step is to create a directory on HDFS and copy the files to this
HDFS directory:
hadoop fs -mkdir chapter9
hadoop fs -put churn_data_clean.all.csv chapter9
hadoop fs -put churn_data_clean_test.all.csv chapter9
The next step is to create the description file. We create it in the hdfs folder
created previously:
hadoop jar $MAHOUT_HOME/mahout-core-0.9-job.jar org.apache.mahout.
classifier.df.tools.Describe -p chapter9/churn_data_clean.all.csv -f
chapter9/churn.info -d 1 n 3 c 10 n l 4 n

Then we proceed to train the model. We will build 100 trees in the forest:
hadoop jar $MAHOUT_HOME/mahout-examples-0.9-job.jar org.apache.mahout.
classifier.df.mapreduce.BuildForest -Dmapred.max.split.size=1874231 -d
chapter9/churn_data_clean.all.csv -ds chapter9/churn.info -sl 4 -p -t 100
-o chapter9_final-forest
The last step is to test the model's performance over test and train sets:
hadoop jar $MAHOUT_HOME/mahout-examples-0.9-job.jar org.apache.mahout.
classifier.df.mapreduce.TestForest -i chapter9/churn_data_clean.all.
csv -ds chapter9/churn.info -m chapter9_final-forest -a -mr -o chapter9_
final-pred

We repeat the last step with the test dataset and check the performance of the model:
hadoop jar $MAHOUT_HOME/mahout-examples-0.9-job.jar org.apache.mahout.
classifier.df.mapreduce.TestForest -i chapter9/churn_data_clean_test.all.
csv -ds chapter9/churn.info -m chapter9_final-forest -a -mr -o chapter9_
final-pred_test



 table(predict(churnRF),churnTrain$Status)
       
        False True
  False  2980  166
  True     26  329
> (2980+329)/nrow(churnTrain)
[1] 0.9451585
> 
> churnPred<-predict(churnRF,newdata=churnTest)
> table(churnPred, churnTest$Status)
         
churnPred False True
    False  1274   63
    True     13  149
> (1274+149)/nrow(churnTest)
[1] 0.9492995
> 
