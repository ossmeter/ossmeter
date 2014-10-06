#Sparkle

Sparkle is a Java library for creating Tufte's sparklines. 

##Motivation
Sparklines are a great way to compactly show a lot of data. There exist one or two JavaScript libraries that can render sparklines (e.g. http://prag.ma/code/sparky/, http://omnipotent.net/jquery.sparkline/), but if you wish to show numerous sparklines on the same web page you need to send all of the data to the client. Which is costly.

Sparkle, therefore, renders the data to PNG format on the server, thereby reducing the amount of data transmitted. 


##Usage
Please note: Sparkle is still in its very early stages and could be optimised in many, many ways. 

###Scales
Sparkle comes with two built-in scale types: ```LinearScale``` and ```DateScale```. You can define new scales by extending ```Scale``` and implementing the ```scale()```.

##Feedback


