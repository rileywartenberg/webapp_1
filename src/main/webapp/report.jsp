<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reports</title>
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
Message:
<p>${message}</p>
<table>
    <thead>Reports</thead>
    <tr><th>Room</th><th>January</th><th>February</th><th>March</th><th>April</th><th>May</th><th>June</th><th>July</th><th>August</th><th>September</th><th>October</th><th>November</th><th>December</th><th>Total</th></tr>
    <c:forEach items="${reports}" var="report">
        <tr>
            <td>${report.roomname}</td>
            <td>${report.january}</td>
            <td>${report.february}</td>
            <td>${report.march}</td>
            <td>${report.april}</td>
            <td>${report.may}</td>
            <td>${report.june}</td>
            <td>${report.july}</td>
            <td>${report.august}</td>
            <td>${report.september}</td>
            <td>${report.october}</td>
            <td>${report.november}</td>
            <td>${report.december}</td>
            <td>${report.total}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="customers">customerPortal</a></p>
<p><a href="./logout">logout</a></p>
</body>
</html>
