"""
Building a decision tree regression and then predicting the output of a particular instance
"""

from numpy import *
import pylab as pl
from sklearn.utils import shuffle
from sklearn.metrics import mean_squared_error
from sklearn import datasets
from sklearn.tree import DecisionTreeRegressor

# Load the boston dataset 
boston = datasets.load_boston()
# Shuffle it and seperate it into training and testing set
# Sample from the dataset uniformly
X, y = shuffle(boston.data, boston.target)
# Training and testing set is divided in the ration 7:3
offset = int(0.7*len(X))
X_train, y_train = X[:offset], y[:offset]
X_test, y_test = X[offset:], y[offset:]

# Setup a Decision Tree Regressor so that it learns a tree with depth 5
regressor = DecisionTreeRegressor(max_depth=5)
    
# Fit the learner to the training data
regressor.fit(X_train, y_train)

x = [11.95, 0.00, 18.100, 0, 0.6590, 5.6090, 90.00, 1.385, 24, 680.0, 20.20, 332.09, 12.13]
# Use the model to predict the output of a particular sample
y = regressor.predict(x)
print "Prediction for " + str(x) + " = " + str(y)

# Find the MSE on the training set
train_err = mean_squared_error(y_train, regressor.predict(X_train))
print "Training Error = " + str(train_err)
 
# Find the MSE on the testing set
test_err = mean_squared_error(y_test, regressor.predict(X_test))
print "Testing Error = " + str(test_err)
