library(mongolite)
library("nnet")

users <- mongo(collection = "users", db = "r_db", url = "mongodb://localhost", verbose = TRUE)
user_info <- mongo(collection = "user_info", db = "r_db", url = "mongodb://localhost", verbose = TRUE)

currentDate<-Sys.Date()

month<-format(currentDate,"%m")
year<-format(currentDate,"%Y")

#month<-6
#year<-2017

user <- users$aggregate(
  
  paste0( '[{"$match":{"month" : ', as.integer(month) , ',"year" :', year, '}}]' )
  
)



###################

for(i in 1 : dim(user)[1]) {
  name <- user[i, "name"]
  balance_score_intermediate <- user[i, "total_bank_balance"]
  income_score_intermediate <- user[i, "totalamount_transactions_credit"]
  social_media_score_intermediate <- user[i, "tweets_sentiment"]
  loan_history_score <- user[i, "loan_history_score"]
  repay_score <- user[i, "repay_score"]
  balance_score <- balance_score_intermediate / 100000
  income_score <- income_score_intermediate / 100000
  social_media_score <- (social_media_score_intermediate + 5) * 10
  
  total_score <- (repay_score * 0.35) + (social_media_score * 0.05) + (loan_history_score * 0.2) + (income_score * 0.2) + (balance_score * 0.2)
  
  print(total_score)
  #data <- data.frame(social_media_score, loan_history_score, balance_score, income_score, repay_score)
  #print(data)
  #predicted_class = predict_class(data)
  #print(predicted_class)
  
  if (total_score > 80) {
    result[i] = '5'
  }
  
  else if (total_score > 60 && total_score <= 80) {
    result[i] = '4'
  }
  
  else if (total_score > 40 && total_score <= 60) {
    result[i] = '3'
  }
  
  else if (total_score > 20 && total_score <= 40) {
    result[i] = '2'
  }
  else
    result[i] = '1'
  
  
  print(result[i])
  user_info$update(query = paste0('{"name":"', name,'"}'), update = paste0('{"$set":{"credit_score": ', result[i], '}}'), upsert = TRUE)
  
}