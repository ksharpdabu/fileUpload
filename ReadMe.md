

#java Servlet和 jquery ajax实现显示文件上传进度
#use java servlet and jquery ajax to show the progress of uploading file




##demo1

这种方式的服务端 使用commons FileUpload库的 ProgressListener 来监控进度，也就说进度情况是保存在服务端的，如果客户端想要知道进度，需要再次发起请求。

如果客户端想在一个页面完成，可以调用js到服务端获得 上传进度信息。


###缺点:
客户端除了要发送post请求来上传文件外，还要多次发送get请求来获取进度信息。


##demo2
demo2其实是demo1的改进版。
和demo1其实差不多，只不过demo2使用了两个servlet来处理，而demo4只是用一个servlet分别调用post和get。

这个实现客户端更新进度的方法的关键是：
这种方式的服务端 使用commons FileUpload库的 ProgressListener 来监控进度，也就说进度情况是保存在服务端的，如果客户端想要知道进度，需要再次发起请求。

将post请求中的上传进度 封装到  get请求中，然后由js发起get请求，来获得xml，解析后显示到html页面


###缺点:
客户端除了要发送post请求来上传文件外，还要多次发送get请求来获取进度信息。

原文：
http://www.codeguru.com/java/article.php/c13913/AJAX-File-Upload-Progress-for-Java.htm


##demo3
使用Jquery的ajax来上传文件，关键是使用FormData对象用来上传文件

优点：方法简单

缺点： 无




##demo4
使用 ajax来上传文件，关键是使用FormData对象用来上传文件

优点：方法简单

缺点： 无

截图：
![demo4](https://github.com/ksharpdabu/fileUpload/blob/master/srceenImage/demo4.png)




##demo5
demo5是demo4的一种特殊的情况，就是页面上没有Form元素。同样需要使用FormData对象用来上传文件
使用 ajax来上传文件，关键是使用FormData对象用来上传文件

优点：方法简单

缺点： 无




