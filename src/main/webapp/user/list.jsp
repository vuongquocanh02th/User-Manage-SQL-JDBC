<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
    <center>
        <h1>User Management</h1>
        <h2>
            <a href="/users?action=create">Add New User</a>
        </h2>
    </center>
    <form  method="post">
        <input type="hidden" name="action" value="search">
        <input type="text" name="country" placeholder="Enter country">
        <button type="submit">Search</button>
    </form>
    <form method="post">
        <input type="hidden" name="action" value="sort">
        <button type="submit">Sort By Name</button>
    </form>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="listUser" items="${listUser}">
            <tr>
                <td><c:out value="${listUser.id}"></c:out></td>
                <td><c:out value="${listUser.name}"></c:out></td>
                <td><c:out value="${listUser.email}"></c:out></td>
                <td><c:out value="${listUser.country}"></c:out></td>
                <td>
                    <a href="/users?action=edit&id=${listUser.id}">Edit</a>
                    <a href="/users?action=delete&id=${listUser.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
