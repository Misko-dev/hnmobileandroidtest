# hnmobileandroidtest
Mobile test application for job offer from Reign.cl

For the development of the application use the following libraries:

https://github.com/orhanobut/hawk  ---> To manage data locally (offline)

https://github.com/square/retrofit ---> To consume the rest service (news API)

https://github.com/ybq/Android-SpinKit ---> To make use of an animation

The application works quite simply.

When you start the application, if you have a stable internet connection 
I saw Wi-Fi or mobile data, a news list will be automatically loaded.
In the case of not having an internet connection,the news list will not be loaded 
and you will be notified by means of a Toast that you must 
connect to the internet and then swipe down on the screen to load the news list.

Now, once the news list has been loaded, if you touch one of this news, 
it will be validated if it has a url to redirect it to an Activity where the website 
where this news was published will be displayed on a WebView.
If there is no url, you will not be redirected to this Activity, 
you will only be notified by a Toast that the news does not have a url.

Finally in the event that you as a user have started the application having a stable internet connection 
and then disconnect from the internet and try to update the news list by sliding down on the screen. 
The last news list that you obtained when you updated will be reloaded and to update the list you must 
obviously reconnect to the internet.
