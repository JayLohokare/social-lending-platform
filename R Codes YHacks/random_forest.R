
library(randomForest)
library(readr)

rand <- read_csv("rand.csv")
train <- rand[1:98,]
test <- rand[99,]
cols <- names(train)[1:4]
 
test2.data <- data.frame(
  loan_history_score = c (89), 
  repay_score = c(89),
  social_media_score = c(12), 
  income_score = c(100)
)

test2.data

clf <- randomForest(factor(credit_class) ~ .,
                    data=train, 
                    importance=TRUE, 
                    ntree=2000)


Prediction <- predict(clf, test2.data[cols])
table(test$credit_class, predict(clf, test[cols]))
submit <- data.frame(Local_history_score = test$loan_history_score, Credit_Class = Prediction)

temp <- submit$Credit_Class
print (temp[1])

