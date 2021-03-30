<%@ page import="com.fuyi.student.model.StudentClass" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div id="div1">
    <%
        Object student = request.getSession().getAttribute("studentclass");
        StudentClass student1=(StudentClass)student;
        pageContext.setAttribute("StudentClass",student1);
//做一个判断  如果学号wei空
        if (null==pageContext.getAttribute("StudentClass")){
    %>
    <h1>该班级编号不存在
            <%
}
else {
    %>
        <table class="table">

            <thead>
            <h2 style="position:center;">查询结果如下</h2>
            <tr class="info">
                <th>班级编号</th>
                <th>班级名称</th>
                <th>年级名称</th>
                <th>班主任</th>
                <th>班级口号</th>
                <th>班级人数</th>
            </tr>
            </thead>
            <tbody class="">
            <tr class="bg-success">
                <td>${StudentClass.class_id}</td>
                <td>${StudentClass.class_name}</td>
                <td>${StudentClass.grade_name}</td>
                <td>${StudentClass.class_teacher}</td>
                <td>${StudentClass.class_slogan}</td>
                <td>${StudentClass.class_num}</td>
            </tr>
            </tbody>
        </table>
            <%
}
    %>
</div>
</body>
</html>
