<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <title>index</title>
</head>
<style>
    a {
        cursor: pointer;
    }
    #carousel-example-generic{
        width: 1000px;
        height: 500px;
    }
    body{
        background: #c8e5bc;
    }
</style>

<body>
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
            <a class="navbar-brand" href="#">学生管理系统 &nbsp;<span class="badge">vs1.0</span></a>
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
        <!-- 菜单占页面的2份 -->
        <div class="col-sm-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div  class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo"
                               aria-expanded="false" aria-controls="collapseTwo">
                                <span class="glyphicon glyphicon-home"></span> 班级管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <!-- 班级管理 -->
                            <ul class="list-group">
                                <li  class="list-group-item"><a href="findStudentClassByPageServlet" id="Classli">班级列表</a></li>
                            </ul>

                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree"
                               aria-expanded="false" aria-controls="collapseThree">
                                <span class="glyphicon glyphicon-user"></span> 学生管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <!-- 学生管理列表组 -->
                            <ul class="list-group">
                                <li class="list-group-item"><a href="findStudentByPageServlet" id="studentli">学生列表</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 这个div包裹要替换的部分 -->
<%--        <div>--%>
<%--            <!-- 主体中心的部分切换时变换的部分占页面的10分 -->--%>
<%--            <div  id="content1" class="col-sm-10">--%>
<%--                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">--%>
<%--                    <!-- Indicators -->--%>
<%--                    <ol class="carousel-indicators">--%>
<%--                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>--%>
<%--                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>--%>
<%--                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>--%>
<%--                    </ol>--%>
<%--                    <!-- Wrapper for slides -->--%>
<%--                    <div class="carousel-inner" role="listbox">--%>
<%--                        <div class="item active">--%>
<%--                            <img src="img/2.jpg" alt="...">--%>
<%--                        </div>--%>
<%--                        <div class="item">--%>
<%--                            <img src="img/3.jpg" alt="...">--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <!-- Controls -->--%>
<%--                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">--%>
<%--                        <span class="sr-only">Previous</span>--%>
<%--                    </a>--%>
<%--                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">--%>
<%--                        <span class="sr-only">Next</span>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
    </div>
</div>
</body>


</html>
