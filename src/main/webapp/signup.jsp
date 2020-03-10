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
    <title>signup</title>
</head>
<body>
<div style="width:10%;height:50%;margin:10% auto;padding: 10px;">
<form method="post" action="signup">
    <p><label for="firstName">First Name</label><br/><input type="text" name="firstName" id="firstName" value="" size="20"></p>
    <p><label for="lastName">Last Name</label><br/><input type="text" name="lastName" id="lastName" value="" size="20"></p>
    <p><label for="ssn">Social Security Number</label><br/><input type="text" name="ssn" id="ssn" value="" size="20"></p>
    <p><label for="address">Address</label><br/><input type="text" name="address" id="address" value="" size="20"></p>
    <p><label for="phoneNumber">Phone Number</label><br/><input type="text" name="phoneNumber" id="phoneNumber" value="" size="20"></p>
    <p><label for="name">User Name</label><br/><input type="text" name="name" id="name" value="" size="20"></p>
    <p><label for="pass">Password</label><input type="password" name="pass" id="pass" value="" size="20"></p>
    <p><input type="submit"></p>
</form>
    <p><a href="./login">login</a></p>
</div>
</body>
</html>