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
    <tr><th>id</th><th>name</th><th></th></tr>
    <c:forEach items="${reports}" var="report">
        <tr>
            <td>${report.id}</td>
            <td>${report.lastName}</td>
            <td><a data-id="${report.id}" href="edit_report?id=${report.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="create_report">Add</a></p>
<p><a href="./home">home</a></p>
</body>
</html>