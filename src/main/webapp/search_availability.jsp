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
<form method="post" action="search_availability">
<p>${message}</p>
    <p><label for="checkinDate">Check In Date</label><br/><input type="text" name="checkinDate" id="checkinDate" value="" size="30"></p>
    <p><label for="checkoutDate">Check Out Date</label><br/><input type="text" name="checkoutDate" id="checkoutDare" value="" size="30"></p>
    <p><label for="minRate">Minimum Price</label><br/><input type="text" name="minRate" id="minRate" value="" size="30"></p>
    <p><label for="maxRate">Maximum Price</label><br/><input type="text" name="maxRate" id="maxRate" value="" size="30"></p>
    <p><label for="bedType">Bed Type</label><br/><input type="text" name="bedType" id="bedType" value="" size="30"></p>
    <p><label for="beds">Number of Beds</label><br/><input type="text" name="beds" id="beds" value="" size="30"></p>
    <p><label for="maxOccupancy">Number of Occupants</label><br/><input type="text" name="maxOccupancy" id="maxOccupancy" value="" size="30"></p>
    <p><input type="submit">
</form>
    <p><a href="./home">home</a></p>
</div>
</body>
</html>