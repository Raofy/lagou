<%@ page import="com.fuyi.student.model.Student" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查询结果</title>
</head>
<body>
<%
    Object student = request.getSession().getAttribute("student");
    Student student1=(Student)student;
    pageContext.setAttribute("student",student1);
//做一个判断  如果学号wei空
    if (null==pageContext.getAttribute("student")){
%>
<h1>该学号不存在
        <%
}
else {
    %>
    <div class="modal-body">
        <!-- 学号 -->
        <div class="form-group">
            <label>学号${student.stu_id}</label>
        </div>
        <!-- 姓名 -->
        <div class="form-group">
            <label>姓名${student.name}</label>
        </div>
        <!-- 性别 -->
        <div class="form-group">
            <label>性别${student.sex}</label>
        </div>
        <!-- 出生日期 -->
        <div class="form-group">
            <label>出生日期${student.birthday}</label>
        </div>
        <!-- 邮箱 -->
        <div class="form-group">
            <label>邮箱${student.email}</label>
        </div>
        <!-- 班级 -->
        <div class="form-group">
            <label>班级${student.class_id}</label>
        </div>
        <!-- 备注 -->
        <div class="form-group">
            <label>备注${student.comment}</label>
        </div>
    </div>
        <%
}
    %>



</body>
</html>

