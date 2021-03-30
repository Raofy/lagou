<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2021/3/30
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title>登陆界面</title>
</head>
<style>
    body{
        background: #46b8da;
    }
</style>
<body>
<!-- 标题登录 -->
<div class="row" style="margin-top:70px;">
    <div class="col-sm-6 col-sm-offset-3">
        <h1 class="text-center">管理员登录</h1>
    </div>
</div>
<!-- 表单 -->
<div class="row">
    <div class="col-sm-6 col-sm-offset-3">
        <form action="loginServlet" method="post">
            <div class="form-group">
                <%
                    //通过请求获取cookie数据
                    Cookie[] cookies = request.getCookies();
                    //遍历Cookie信息
                    for (Cookie cookie: cookies){
                        //如果找到存入的用户信息那么就读取出来
                        if ("userinfo".equals(cookie.getName())){
                            //获取cookie的值
                            String value = cookie.getValue();
                            //拆分用户名和密码
                            String[] split = value.split("-");
                            //将值设置到页面
                            pageContext.setAttribute("username",split[0]);
                            pageContext.setAttribute("password",split[1]);
                            System.out.println(split[0]);
                            System.out.println(split[1]);
                            break;
                        }
                    }
                %>


                <!-- 账号的input -->
                <label for="username">账号</label>
                <input type="text" class="form-control" name="username" id="username" placeholder="请输入用户名">
            </div>
            <div class="form-group">
                <!-- 账号的input -->
                <label for="password">密码</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">
            </div>
            <!-- 清除浮动-->
            <div class="clearfix"></div>
            <button type="submit" class="btn btn-success" id="login" name="loginreq" style="margin-top:10px; width: 100px;">登录
            </button>
            <button type="submit" class="btn btn-success" name="registreq" style="margin-top:10px; width: 100px;">注册
            </button>
            <button type="submit" class="btn btn-danger" name="findpwdreq" style="margin-top:10px; width: 100px;">找回密码
            </button>
            <p> <input type="checkbox" name="remember" id="remember" value="0">七天内免登录</p>
            <script type="text/javascript">
                $(function (){
                    //设置七天免登录复选框绑定事件
                    $("#remember").click(function (){
                        //判断复选框是否为选中的状态 如果选中的话就将值设计为1
                        if ($(this).is(":checked")){
                            //选中的话就将值设置为1
                            $(this).val("1")
                        }else {
                            //否则的话就将值设置为0
                            $(this).val("0")
                        }
                    });
                    //判断页面的对象是否为空pageContext
                    if (${not empty username}&& ${ not empty password}){
                        $("#username").val("${username}");
                        $("#password").val("${password}");
                        $("#remember").val("1");
                        $("#remember").attr("checked",true);
                    }
                });
            </script>
        </form>
    </div>
</div>
</body>


</html>
