
$(function(){
// 添加的操作
$("#addstudentbutton").click(function () {
    //2.发送Ajax的get请求
    //a 创建xhr对象
    $.ajax({
        cache: true,
        type: "POST",
        url: "addStudentServlet",
        data: $("#addform").serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            alert(data);
            //默认点击事件  刷新页面
            if (document.all) {
                document.getElementById("flush").click();
            }
            // 其它浏览器
            else {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("flush").dispatchEvent(e);
            }
        }
    });

});

// 修改的操作
$("#repeatStuInfo").click(function () {
    //2.发送Ajax的post请求
    //a 创建xhr对象
    $.ajax({
        cache: true,
        type: "POST",
        url: "repeatStuInfoServlet",
        data: $("#repeatInfoForm").serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            alert(data)
            if (document.all) {
                document.getElementById("flush").click();
            }
            // 其它浏览器
            else {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("flush").dispatchEvent(e);
            }

        }
    });
});
//删除的操作
$("#deleteuserbutton").click(function () {
    var qx = $("input[name='box']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
    //2.发送Ajax的get请求
    //a 创建xhr对象
    var xhr;
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else {
        xhr = new ActiveXObject("Microsoft.XMLHttp");
    }
    //b 发送请求 传递参数
    xhr.open("GET", "deleteStudentServlet?username=" + qx, true);
    xhr.send();
    //c 处理响应并更新页面布局
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            //弹出响应的请求返回的信息
            alert(xhr.responseText);
            //默认点击事件  刷新页面
            if (document.all) {
                document.getElementById("flush").click();
            }
            // 其它浏览器
            else {
                var e = document.createEvent("MouseEvents");
                e.initEvent("click", true, true);
                document.getElementById("flush").dispatchEvent(e);
            }
        }
    }
});
});