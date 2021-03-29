<%--
  Created by IntelliJ IDEA.
  User: 小黑
  Date: 2021/3/23
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试servlet</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/test?methodName=add">新增课程</a>
<a href="${pageContext.request.contextPath}/test?methodName=update">更新课程</a>
<a href="${pageContext.request.contextPath}/test?methodName=delete">删除课程</a>
<a href="${pageContext.request.contextPath}/test2?methodName=addCourse">反射方式增加课程</a>
</body>
</html>
