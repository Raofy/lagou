<%--
  Created by IntelliJ IDEA.
  User: 小黑
  Date: 2021/3/25
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
    <input type="file" name="upload">
    <br>
    <input type="text" name="name">
    <input type="text" name="password">
    <input type="submit" value="文件上传">
</form>

</body>
</html>
