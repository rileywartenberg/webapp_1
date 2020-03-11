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
    <title>home</title>
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
    <li><a href="./customers">customers</a></li>
    <li><a href="./create_reservations">make a reservations</a></li>
    <li><a href="./logout">logout</a></li>
    </ul>
<p>${message}</p>
<table>
    <thead>Reservations</thead>
    <tr><th>id</th><th>room</th><th>checkIn</th><th>checkIn</th><th>checkOut</th></tr>
    <c:forEach items="${reservations}" var="reservation">
        <tr>
            <td>${reservation.id}</td>
            <td>${reservation.room}</td>
            <td>${reservation.checkin}</td>
            <td>${reservation.checkout}</td>
            <td>${reservation.rate}</td>
            <td>${reservation.adults}</td>
            <td>${reservation.kids}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>