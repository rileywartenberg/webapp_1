<%--
  Created by IntelliJ IDEA.
  User: toshihirokuboi
  Date: 2019-11-21
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Form</title>
</head>
<body>
<div style="width:30%;height:50%;margin:10% auto;padding: 10px;">
<form method="post" action="delete_reservations">
<p>${message}</p>
    <p><label for="id">id</label><br/><input type="text" name="id" id="id" value="${reservations.id}" size="30"></p>

    <p><input type="submit">

</form>
    <p><a href="./home">home</a></p>
</div>
</body>
</html>