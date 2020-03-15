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
    <title>Reservations Create Form</title>
</head>
<body>
<div style="width:30%;height:50%;margin:10% auto;padding: 10px;">
<form method="post" action="create_reservations">
<p>${message}</p>
   <%-- <p><label for="id">Customer Id</label><br/><input type="text" name="cid" id="id" value="" size="30"></p>
    <p><label for="id">id</label><br/><input type="text" name="id" id="id" value="${reservations.id}" size="30"></p> --%>
    <p><label for="room">Room Code</label><br/><input type="text" name="room" id="room" value="${reservations.room}" size="30"></p>
    <p><label for="checkin">checkin</label><br/><input type="text" name="checkin" id="checkin" value="${reservations.checkin}" size="30"></p>
    <p><label for="checkout">checkout</label><br/><input type="text" name="checkout" id="checkout" value="${reservations.checkout}" size="30"></p>
    <%-- <p><label for="rate">rate</label><br/><input type="text" name="rate" id="rate" value="${reservations.rate}" size="30"></p> --%>
    <p><label for="ccnum">Credit Card Number</label><br/><input type="text" name="ccnum" id="ccnum" value="${reservations.ccnum}" size="30"></p>
    <p><label for="adults">adults</label><br/><input type="text" name="adults" id="adults" value="${reservations.adults}" size="30"></p>
    <p><label for="kids">kids</label><br/><input type="text" name="kids" id="kids" value="${reservations.kids}" size="30"></p>

    <p><input type="submit">
</form>
    <p><a href="./home">home</a></p>
</div>
</body>
</html>