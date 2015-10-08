# Perform preliminary analyses on time series data
#--------------------------------------------------------
wm <- read.csv("walmart.csv")

plot(wm$Adj.Close, type = "l")

d <- diff(wm$Adj.Close)
plot(d, type = "l")


hist(d, prob = TRUE, ylim = c(0,0.8), main = "Walmart stock", col = "blue")
lines(density(d), lwd = 3)

wmm <- read.csv("walmart-monthly.csv")
wmm.ts <- ts(wmm$Adj.Close)
d <- diff(wmm.ts)
wmm.return <- d/lag(wmm.ts, k=-1)
hist(wmm.return, prob = TRUE, col="blue")
diff(wmm$Adj.Close, lag = 2)



# Using time series objects
#---------------------------------------------------
s <- read.csv("ts-example.csv")

s.ts <- ts(s)
class(s.ts)


plot(s.ts)

s.ts.a <- ts(s, start = 2002)
s.ts.a

plot(s.ts.a)

s.ts.m <- ts(s, start = c(2002,1), frequency = 12)
s.ts.m
plot(s.ts.m)  

s.ts.q <- ts(s, start = 2002, frequency = 4)
s.ts.q
plot(s.ts.q)

start(s.ts.m)

end(s.ts.m)

frequency(s.ts.m)

prices <- read.csv("prices.csv")
prices.ts <- ts(prices, start=c(1980,1), frequency = 12)

plot(prices.ts)

plot(prices.ts, plot.type = "single", col = 1:2)
legend("topleft", colnames(prices.ts), col = 1:2, lty = 1)


# Building an automated ARIMA model
#--------------------------------------
infy <- read.csv("infy-monthly.csv")

infy.ts <- ts(infy$Adj.Close, start = c(1999,3), freq = 12)

infy.arima <- auto.arima(infy.ts)

infy.forecast <- forecast(infy.arima, h=10)

plot(infy.forecast)
