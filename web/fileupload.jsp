<%--
  Created by IntelliJ IDEA.
  User: AlexY
  Date: 2016/6/26
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload</title>
</head>
<body>

    <form action="uploadServlet " method="post" enctype="multipart/form-data">

        username: <input type="text" name="username"> <br>
        file: <input type="file" name="file"> <br>
        file 2 : <input type="file" name="file2"> <br>


        <input type="submit" value="submit">


    </form>



</body>
</html>
