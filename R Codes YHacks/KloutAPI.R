
library(RKlout)


#########################################Klout APIs#################################################

klout_api_key = 'ta3nc6y9pvnsbk7fsaw6kg7t'
#########################################Klout  APIs#################################################
twitter_handle = 'JayLohokare'

kloutScore <- RKlout(klout_api_key,twitter_handle)

print (kloutScore)