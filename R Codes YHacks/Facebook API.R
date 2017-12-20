library(Rfacebook)
library(RCurl)

#fb_oauth <- fbOAuth(app_id="389540558153177", app_secret="81a55e45e030981bc4c6fda4bbb8ff69",extended_permissions = TRUE)


#friends <- getFriends(token=fb_oauth, simply = TRUE)
 

#fb_oauth <- fbOAuth(app_id="389540558153177", app_secret="81a55e45e030981bc4c6fda4bbb8ff69")
#save(fb_oauth, file="fb_oauth")
load("fb_oauth")
me <- getUsers("me", token=fb_oauth)
friends <- getFriends(token=fb_oauth )
print (friends)