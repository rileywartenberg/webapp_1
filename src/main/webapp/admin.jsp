<%--
  Created by IntelliJ IDEA.
  User: toshihirokuboi
  Date: 2019-11-19
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin login</title>
</head>
<body>
<div style="width:10%;height:50%;margin:10% auto;padding: 10px;">
<form method="post" action="admin">
    <p><label for="name">User Name</label><br/><input type="text" name="name" id="name" value="" size="20"></p>
    <p><label for="pass">Password</label><input type="password" name="pass" id="pass" value="" size="20"></p>
    <p><input type="submit"></p>
</form>
    <p><a href="./login">user login</a></p>
    <p><a href="./signup">signup</a></p>
</div>
</body>
</html>