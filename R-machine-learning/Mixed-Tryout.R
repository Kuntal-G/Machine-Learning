# Plotting  a Google map of an area
install.packages("RgoogleMaps")
library(RgoogleMaps)

shu.map <- GetMap(center = c(40.742634, -74.246215), zoom=17)
PlotOnStaticMap(shu.map)

shu.map = GetMap(center = c(40.742634, -74.246215), zoom=16, destfile = "shu.jpeg", format = "jpeg")

shu.map = GetMap(center = c(40.742634, -74.246215), zoom=16, destfile = "shu.jpeg", format = "jpeg", maptype = "satellite")
PlotOnStaticMap(shu.map)

#  Exploiting vectorized operations

first.name <- c("John", "Jane", "Tom", "Zach")
last.name <- c("Doe", "Smith", "Glock", "Green")
paste(first.name,last.name)

new.last.name <- c("Dalton")
paste(first.name,new.last.name)

username <- function(first, last) {
        tolower(paste0(last, substr(first,1,1)))
 }
username(first.name,last.name)

auto <- read.csv("auto-mpg.csv")
auto$kmpg <- auto$mpg*1.6

sum(1,2,3,4,5)
mean(1,2,3,4,5)
mean(c(1,2,3,4,5))


# Slicing, dicing and combining data with data tables

auto <- read.csv("auto-mpg.csv", stringsAsFactors=FALSE)
auto$cylinders <- factor(auto$cylinders, levels = c(3,4,5,6,8), labels = c("3cyl", "4cyl", "5cyl", "6cyl", "8cyl"))

install.packages("data.table")
library(data.table)
autoDT <- data.table(auto)

autoDT[, mean(mpg), by=cylinders]
autoDT[, meanmpg := mean(mpg), by=cylinders]
autoDT[1:5,c(1:3,9:10), with=FALSE]

setkey(autoDT,cylinders)
tables()

autoDT["4cyl",c(1:3,9:10),with=FALSE]
autoDT[.("4cyl"),c(1:3,9:10),with=FALSE]

autoDT[, list(meanmpg=mean(mpg), minmpg=min(mpg), maxmpg=max(mpg)), by=cylinders]

autoDT[,c("medianmpg","sdmpg") := list(median(mpg),sd(mpg)), by=cylinders]
autoDT[1:5,c(3,9:12), with=FALSE]

autoDT[,.N ,by=cylinders]
autoDT["4cyl",.N]

autoDT[,medianmpg:=NULL]

emp <- read.csv("employees.csv", stringsAsFactors=FALSE)
dept <- read.csv("departments-1.csv", stringsAsFactors=FALSE)
empDT <- data.table(emp)
deptDT <- data.table(dept)
setkey(empDT,"DeptId")
combine <- empDT[deptDT]
combine[,.N]

dept <- read.csv("departments-2.csv", stringsAsFactors=FALSE)
deptDT <- data.table(dept)
# The following line gives an error 
combine <- empDT[deptDT]
# Avoid the error using allow.cartesian=TRUE
combine <- empDT[deptDT, allow.cartesian=TRUE]
combine[,.N]

mash <- empDT[deptDT, nomatch=0]
mash[,.N]

empDT[deptDT, max(.SD), by=.EACHI, .SDcols="Salary"]
empDT[,.(AvgSalary = lapply(.SD,mean)), by="DeptId",.SDcols="Salary"]
empDT[deptDT,list(DeptName, AvgSalary = lapply(.SD,mean)),by=.EACHI,.SDcols="Salary"]

