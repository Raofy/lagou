<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.fuyi.student.model.StudentClass" %>
<%@ page import="com.fuyi.student.model.Page" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%--转发来的请求--%>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title>班级</title>
</head>

<style>
    a {
        cursor: pointer;
    }
    table{
        background: #c8e5bc ;
    }
    body{
        background: #c8e5bc;
    }

</style>
<body>
<script src="${pageContext.request.contextPath}/js/StudentClass.js"></script>

<%
    Object page1 = request.getSession().getAttribute("page");
    Page<StudentClass> page2=(Page)page1;
    pageContext.setAttribute("page",page2);
%>
<!-- 导航条 -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="mainServlet">学生管理系统 &nbsp;<span class="badge">vs1.0</span></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <%--     将登陆的用户的账号通过Session的对象的获取到  设置到的导航条相应的位置上      --%>
                <% HttpSession session1= request.getSession();
                    Object username = session1.getAttribute("user");
                %>
                <li><a href="#">欢迎<%=username%>
                </a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">用户中心 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">修改密码</a></li>
                        <!-- 分割线的修饰 -->
                        <li role="separator" class="divider"></li>
                        <li><a href="#">修改用户名</a></li>
                        <!-- 分割线的修饰 -->
                        <li role="separator" class="divider"></li>
                        <li><a href="#">退出登录</a></li>
                        <!-- 分割线的修饰 -->
                        <li role="separator" class="divider"></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<!-- 页面的主体部分 -->
<div class="container-fluid">
    <!--栅格系统 -->
    <div class="row">
        <!-- 主体占页面的10分 -->
        <!-- 页头 -->
        <!-- 页体-->
        <div class="col-sm-10 ">
            <!-- 页头 -->
            <div class="page-header" style="margin-top:-10px">
                <h1><a id="flush" href="findStudentClassByPageServlet">班级管理</a></h1>
            </div>
            <div>
                <!-- 动态的标签页码 -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a class="btn btn-primary" href="#usertablediv"
                                                              aria-controls="usertablediv" role="tab" data-toggle="tab"
                                                              id="studentcolum">班级列表</a></li>
                    <li role="presentation"><a class="btn btn-primary" href="#adduser" aria-controls="adduser"
                                               role="tab"
                                               data-toggle="tab">创建班级</a></li>
                    <li role="presentation"><a class="btn btn-primary" href="#" aria-controls="deleteuser"
                                               role="tab"  data-toggle="modal" data-target="#myModal">修改班级信息</a></li>
                    <li role="presentation"><a id="deleteclass" class="btn btn-primary" >删除班级</a>
                    </li>
                    <li role="presentation"><a class="btn btn-primary" href="#searchuser" aria-controls="searchuser"
                                               role="tab" data-toggle="tab">查询班级</a></li>
                    <!-- 可动标签页码  根据需求可扩展 -->
                </ul>
                <!--切换内容 -->
                <div class="tab-content" >
                    <!-- 第一个选择的div层  处理用户列表的动态标签页  处理用户列表的面板 -->
                    <div role="tabpanel" class="tab-pane active " id="usertablediv">

                        <div class="panel panel-default ">
                            <!-- 栅格系统  包含table和分页-->
                            <div class="row">
                                <div class="col-xs-12 .col-sm-6 .col-md-8">

                                    <!--表格-->

                                    <table id="usertable" class="table ">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>班级编号</th>
                                            <th>班级名称</th>
                                            <th>年级名称</th>
                                            <th>班主任</th>
                                            <th>班级口号</th>
                                            <th>班级人数</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <% int i=1;%>
                                        <c:forEach var="tr" items="${page.list}" >
                                            <tr class="info">
                                                <td><input type="checkbox" value="${tr.class_id}" name="box"><%=i%></td>
                                                <td>${tr.class_id}</td>
                                                <td>${tr.class_name}</td>
                                                <td>${tr.grade_name}</td>
                                                <td>${tr.class_teacher}</td>
                                                <td>${tr.class_slogan}</td>
                                                <td>${tr.class_num}</td>
                                            </tr>
                                            <%i++;%>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <!-- 快速浮动放到右边 -->
                                    <div class="pull-right">
                                        <!-- 添加一个分页的组件 -->
                                        <nav aria-label="Page navigation">
                                            <ul class="pagination">
                                                <!-- 默认在第一页 -->
                                                <li class="disabled">
                                                    <a href="#" aria-label="Previous">
                                                        <span aria-hidden="true">&laquo;</span>
                                                    </a>
                                                </li>
                                                <c:forEach var="i" begin="1" end="${page.totalPage}">
                                                    <%--如果是相等的话就显示激活的状态 --%>
                                                    <c:if test="${page.currentPage==i}">
                                                        <li class="active"><a href="${pageContext.request.contextPath}/findStudentClassByPageServlet?currentPage=${i}&rows=5">${i}</a></li>
                                                    </c:if>
                                                    <%--不相等的话就显示默认的状态 --%>
                                                    <c:if test="${page.currentPage!=i}">
                                                        <li><a href="${pageContext.request.contextPath}/findStudentClassByPageServlet?currentPage=${i}&rows=5">${i}</a></li>
                                                    </c:if>
                                                </c:forEach>
                                                <li>
                                                    <a href="#" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <span aria-hidden="true">共${page.totalCount}条记录</span>
                                                    <span aria-hidden="true">共${page.totalPage}页</span>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 第二个选择的div层 处理创建班级的信息的-->
                    <div role="tabpanel" class="tab-pane" id="adduser">
                        <!-- 添加面板 -->
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <!-- 做一个栅格 -->
                                <div class="row">
                                    <div class="col-xs-12 .col-sm-6 .col-md-8">
                                        <!-- 添加一个表单 -->
                                        <form id="addform">
                                            <!-- 班级名称 -->
                                            <div class="form-group">
                                                <label>班级编号</label>
                                                <input type="text" class="form-control" name="class_id" placeholder="班级id">
                                            </div>
                                            <div class="form-group">
                                                <label>班级名称</label>
                                                <input type="text" class="form-control" name="class_name" placeholder="班级名称">
                                            </div>
                                            <!-- 年级 -->
                                            <div class="form-group">
                                                <label>年级</label>
                                                <input type="text" class="form-control" name="grade_name"
                                                       placeholder="年级">
                                            </div>
                                            <!-- 班主任名称 -->
                                            <div class="form-group">
                                                <label>班主任名称</label>
                                                <input type="text" class="form-control" name="class_teacher" placeholder="班主任名称">
                                            </div>
                                            <!-- 班级口号 -->
                                            <div class="form-group">
                                                <label>班级口号</label>
                                                <input type="text" class="form-control" name="class_slogan"
                                                       placeholder="班级口号">
                                            </div>
                                        </form>
                                        <!-- 可扩展 -->
                                        <button class="btn btn-primary" id="addstudentbutton">添加</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- 第三个修改班级的信息 模态框 --%>
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">修改信息</h4>
                                </div>
                                <div class="modal-body">
                                    <!-- 添加一个表单 -->
                                    <form id="repeatClassInfoForm">
                                        <!-- 请输入要修改的班级编号 -->
                                        <div class="form-group">
                                            <label>请输入要修改的班级编号</label>
                                            <input type="text" class="form-control" name="class_id_before"
                                                   placeholder="班级编号">
                                        </div>
                                        <!-- 请输入修改后的班级编号 -->
                                        <div class="form-group">
                                            <label>请输入修改后的班级编号</label>
                                            <input type="text" class="form-control" name="class_id_later"
                                                   placeholder="班级编号">
                                        </div>
                                        <!-- 年级名称 -->
                                        <div class="form-group">
                                            <label>年级名称</label>
                                            <input type="text" class="form-control" name="grade_name"
                                                   placeholder="年级名称">
                                        </div>
                                        <!-- 班级名称 -->
                                        <div class="form-group">
                                            <label>班级名称</label>
                                            <input type="text" class="form-control" name="class_name" placeholder="班级名称">
                                        </div>
                                        <!-- 班主任 -->
                                        <div class="form-group">
                                            <label>班主任</label>
                                            <input type="text" class="form-control" name="class_teacher"
                                                   placeholder="班主任">
                                        </div>
                                        <!-- 班级口号 -->
                                        <div class="form-group">
                                            <label>班级口号</label>
                                            <input type="text" class="form-control" name="class_slogan"
                                                   placeholder="班级口号">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" id="repeatClassInfo">Save changes
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- 第五个查找的按钮 -->
                    <div role="tabpanel" class="tab-pane" id="searchuser">
                        <!-- 添加面板 -->
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <!-- 做一个栅格 -->
                                <div class="row">
                                    <div class="col-l-sm-offset-1 col-sm-10">
                                        <!-- 添加一个表单 -->
                                        <form action="searchStudentClassServlet" method="post" id="searchClassForm">
                                            <!-- 学号 -->
                                            <div class="form-group">
                                                <label>请输入你要查询的班级编号</label>
                                                <input id="searchbyid" type="text" class="form-control" name="class_id"
                                                       placeholder="Id">
                                            </div>
                                            <button id="serchClass"  data-toggle="modal" data-target="myModalInfo">查找</button>
                                            <!-- 可扩展 -->
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
