<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.fuyi.student.model.Student" %>
<%@ page import="com.fuyi.student.model.Page" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>学生页面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>
<style>
    a {
        cursor: pointer;
    }
    body{
        background: #c8e5bc;
    }
    table{
        background: #c8e5bc ;
    }
</style>
<body>
<script src="${pageContext.request.contextPath}/js/student.js"></script>
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
                <%--     将登陆的用户的账号通过Session的对象的获取到  设置到的导航条相应的位置上--%>
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
                <h1><a id="flush" href="findStudentByPageServlet">学生管理</a></h1>
            </div>
            <div>
                <!-- 动态的标签页码 -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a class="btn btn-primary" href="#usertablediv"
                                                              aria-controls="usertablediv" role="tab" data-toggle="tab"
                                                              id="studentcolum">学生列表</a></li>
                    <li role="presentation"><a class="btn btn-primary" href="#adduser" aria-controls="adduser"
                                               role="tab"
                                               data-toggle="tab">添加</a></li>


                    <li role="presentation"><a class="btn btn-primary" href="#deleteuser" aria-controls="deleteuser"
                                               role="tab" data-toggle="tab" id="deleteuserbutton">删除</a></li>

                    <li role="presentation"><a class="btn btn-primary" data-toggle="modal" data-target="#myModal">修改</a>
                    </li>
                    <%-- 修改信息 模态框--%>
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
                                    <form id="repeatInfoForm">
                                        <!-- 学号 -->
                                        <div class="form-group">
                                            <label>请输入要修改的学号</label>
                                            <input type="text" class="form-control" name="stu_id_before"
                                                   placeholder="学号">
                                        </div>
                                        <!-- 学号 -->
                                        <div class="form-group">
                                            <label>请输入修改后的学号</label>
                                            <input type="text" class="form-control" name="stu_id_later"
                                                   placeholder="学号">
                                        </div>
                                        <!-- 姓名 -->
                                        <div class="form-group">
                                            <label>姓名</label>
                                            <input type="text" class="form-control" name="stu_name"
                                                   placeholder="姓名">
                                        </div>
                                        <!-- 性别 -->
                                        <div class="form-group">
                                            <label>性别</label>
                                            <input type="text" class="form-control" name="stu_sex" placeholder="性别">
                                        </div>
                                        <!-- 出生日期 -->
                                        <div class="form-group">
                                            <label>出生日期</label>
                                            <input type="text" class="form-control" name="stu_birthday"
                                                   placeholder="出生日期">
                                        </div>
                                        <!-- 邮箱 -->
                                        <div class="form-group">
                                            <label>邮箱</label>
                                            <input type="text" class="form-control" name="stu_email"
                                                   placeholder="邮箱">
                                        </div>
                                        <!-- 班级 -->
                                        <div class="form-group">
                                            <label>班级</label>
                                            <input type="text" class="form-control" name="stu_class"
                                                   placeholder="班级">
                                        </div>
                                        <!-- 备注 -->
                                        <div class="form-group">
                                            <label>备注</label>
                                            <input type="text" class="form-control" name="stu_comment"
                                                   placeholder="备注">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                    <button type="button" class="btn btn-primary" id="repeatStuInfo">Save changes
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>

                    <li role="presentation"><a class="btn btn-primary" href="#searchuser" aria-controls="searchuser"
                                               role="tab" data-toggle="tab">查找</a></li>
                    <!-- 可动标签页码  根据需求可扩展 -->
                </ul>
                <!--切换内容 -->
                <div class="tab-content">
                    <!-- 第一个选择的div层  处理用户列表的动态标签页  处理用户列表的面板 -->
                    <div role="tabpanel" class="tab-pane active " id="usertablediv">
                        <%
                            Object page1 = request.getSession().getAttribute("StudentPage");
                            Page<Student> StduentPage=(Page<Student>)page1;
                            pageContext.setAttribute("studentPage",StduentPage);
                            int num=1;
                        %>
                        <div class="panel panel-default ">
                            <!-- 栅格系统  包含table和分页-->
                            <div class="row">
                                <div class="col-xs-12 .col-sm-6 .col-md-8">
                                    <!--表格-->
                                    <table id="usertable" class="table ">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>学号</th>
                                            <th>姓名</th>
                                            <th>性别</th>
                                            <th>出生日期</th>
                                            <th>邮箱</th>
                                            <th>班级</th>
                                            <th>备注</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <%--使用jslt表达式--%>
                                        <c:forEach var="tr" items="${studentPage.list}">
                                            <tr class="info">
                                                <td><input type="checkbox" name="box" value="${tr.stu_id}"><%=num%></td>
                                                <td>${tr.stu_id}</td>
                                                <td>${tr.name}</td>
                                                <td>${tr.sex}</td>
                                                <td>${tr.birthday}</td>
                                                <td>${tr.email}</td>
                                                <td>${tr.class_id}</td>
                                                <td>${tr.comment}</td>
                                            </tr>
                                            <%
                                                num++;
                                            %>
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
                                                <c:forEach var="i" begin="1" end="${studentPage.totalPage}">
                                                    <c:if test="${studentPage.currentPage==i}">
                                                        <li class="active"><a href="#">${i}</a></li>
                                                    </c:if>
                                                    <c:if test="${studentPage.currentPage!=i}">
                                                        <li><a href="${pageContext.request.contextPath}/findStudentByPageServlet?currentPage=${i}&rows=5">${i}</a></li>
                                                    </c:if>
                                                </c:forEach>
                                                <li>
                                                    <a href="#" aria-label="Next">
                                                        <span aria-hidden="true">&raquo;</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 第二个选择的div层 处理添加用户的信息的-->
                    <div role="tabpanel" class="tab-pane" id="adduser">
                        <!-- 添加面板 -->
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <!-- 做一个栅格 -->
                                <div class="row">
                                    <div class="col-xs-12 .col-sm-6 .col-md-8">
                                        <!-- 添加一个表单 -->
                                        <form id="addform">
                                            <!-- 学号 -->
                                            <div class="form-group">
                                                <label>学号</label>
                                                <input type="text" class="form-control" name="stu_id" placeholder="学号">
                                            </div>
                                            <!-- 姓名 -->
                                            <div class="form-group">
                                                <label>姓名</label>
                                                <input type="text" class="form-control" name="stu_name"
                                                       placeholder="姓名">
                                            </div>
                                            <!-- 性别 -->
                                            <div class="form-group">
                                                <label>性别</label>
                                                <input type="text" class="form-control" name="stu_sex" placeholder="性别">
                                            </div>
                                            <!-- 出生日期 -->
                                            <div class="form-group">
                                                <label>出生日期</label>
                                                <input type="text" class="form-control" name="stu_birthday"
                                                       placeholder="出生日期">
                                            </div>
                                            <!-- 邮箱 -->
                                            <div class="form-group">
                                                <label>邮箱</label>
                                                <input type="text" class="form-control" name="stu_email"
                                                       placeholder="邮箱">
                                            </div>
                                            <!-- 班级 -->
                                            <div class="form-group">
                                                <label>班级</label>
                                                <input type="text" class="form-control" name="stu_class"
                                                       placeholder="班级">
                                            </div>
                                            <!-- 备注 -->
                                            <div class="form-group">
                                                <label>备注</label>
                                                <input type="text" class="form-control" name="stu_comment"
                                                       placeholder="备注">
                                            </div>
                                        </form>
                                        <!-- 可扩展 -->
                                        <button class="btn btn-primary" id="addstudentbutton">添加</button>
                                        <script>

                                        </script>
                                    </div>
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
                                        <form action="searchStudentServlet" method="post">
                                            <!-- 学号 -->
                                            <div class="form-group">
                                                <label>请输入你要查询的学号</label>
                                                <input id="searchbyid" type="text" class="form-control" name="stu_id"
                                                       placeholder="Id">
                                            </div>
                                            <button type="submit"  data-toggle="modal" data-target="#SearchModalLabel">查找</button>
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
