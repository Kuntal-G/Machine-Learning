"""
Plots Learning curves for Boosting-AdaBoostRegressor

"""

from numpy import *
import matplotlib.pyplot as pl
from sklearn.utils import shuffle
from sklearn.metrics import mean_squared_error
from sklearn import datasets
from sklearn.ensemble import AdaBoostRegressor

# Load the boston dataset and seperate it into training and testing set
boston = datasets.load_boston()
X, y = shuffle(boston.data, boston.target)
offset = int(0.7*len(X))
X_train, y_train = X[:offset], y[:offset]
X_test, y_test = X[offset:], y[offset:]

# Varying the training set size so that we have 20 different sizes
sizes = linspace(1, len(X_train), 20)
train_err = zeros(len(sizes))
test_err = zeros(len(sizes))

for i, s in enumerate(sizes):
	# Fit a model with 10 base learners
	regressor = AdaBoostRegressor(n_estimators=10)
	regressor.fit(X_train[:s], y_train[:s])

	# Find the MSE on the training and testing set
	train_err[i] = mean_squared_error(y_train[:s], regressor.predict(X_train[:s]))
	test_err[i] = mean_squared_error(y_test, regressor.predict(X_test))

# Plot training and test error as a function of the training size
pl.figure()
pl.title('Boosting: Performance vs Training Size')
pl.plot(sizes, test_err, lw=2, label = 'test error')
pl.plot(sizes, train_err, lw=2, label = 'training error')
pl.legend()
pl.xlabel('Training Size')
pl.ylabel('MS Error')
pl.show()

