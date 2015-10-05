"""
Plots Model Complexity graphs for Support Vector Machines by changing the kernel

"""

from numpy import *
import pylab as pl
from sklearn import datasets
from sklearn.utils import shuffle
from sklearn.svm import SVR
from sklearn.metrics import mean_squared_error

# Load the boston dataset and seperate it into training and testing set
boston = datasets.load_boston()
X, y = shuffle(boston.data, boston.target)
offset = int(0.7*len(X))
X_train, y_train = X[:offset], y[:offset]
X_test, y_test = X[offset:], y[offset:]

# -----------------------------------
# Learn a SVM with a linear kernel
clf = SVR(kernel='poly', degree=1)
clf.fit(X_train, y_train)

train_err = mean_squared_error(y_train, clf.predict(X_train))
test_err = mean_squared_error(y_test, clf.predict(X_test))

print "Linear Kernel"
print train_err, test_err

# -----------------------------------
# Learn a SVM with a polynomial kernel
clf = SVR(kernel='poly', degree=2)
# Fit the learner to the training data
clf.fit(X_train, y_train)

# Find the MSE on the training and testing set
train_err = mean_squared_error(y_train, clf.predict(X_train))
test_err = mean_squared_error(y_test, clf.predict(X_test))

print "Poly Kernel with degree 2"
print train_err, test_err

# -----------------------------------
# Learn a SVM with a RBF kernel with degree 2
clf = SVR(kernel='rbf', degree=2)
# Fit the learner to the training data
clf.fit(X_train, y_train)

# Find the MSE on the training and testing set
train_err = mean_squared_error(y_train, clf.predict(X_train))
test_err = mean_squared_error(y_test, clf.predict(X_test))

print "RBF Kernel with degree 2"
print train_err, test_err

# -----------------------------------
# Learn a SVM with a RBF kernel with degree 3
clf = SVR(kernel='rbf', degree=3)
# Fit the learner to the training data
clf.fit(X_train, y_train)

# Find the MSE on the training and testing set
train_err = mean_squared_error(y_train, clf.predict(X_train))
test_err = mean_squared_error(y_test, clf.predict(X_test))

print "RBF Kernel with degree 3"
print train_err, test_err
