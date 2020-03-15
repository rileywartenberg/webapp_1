<%--
  Created by IntelliJ IDEA.
  User: toshihirokuboi
  Date: 2019-11-21
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="home.css">
    <title>Reservation</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }
        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
    <ul>
    <li><a href="./home">home</a></li>
    </ul>
<p>${message}</p>
<table>
    <thead>Room_Availability</thead>
    <tr><th>room</th><th>bedType</th><th>beds</th><th>maxOccupancy</th><th>stay length</th><th>availability</th><th>popularity</th><th>nextAvailable</th></tr>
    <c:forEach items="${availabilities}" var="availability">
        <tr>
            <td>${availability.roomName}</td>
            <td>${availability.bedType}</td>
            <td>${availability.beds}</td>
            <td>${availability.maxOccupancy}</td>
            <td>${availability.length}</td>
            <td>${availability.available}</td>
            <td>${availability.popularity}</td>
            <td>${availability.nextDate}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>