

$(function (){
// 创建班级的操作
$("#addstudentbutton").click(function () {
    //2.发送Ajax的get请求
    //a 创建xhr对象
    $.ajax({
        cache: true,
        type: "POST",
        url: "addStudentClassServlet",
        data: $("#addform").serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            alert(data);
            // 默认点击事件  刷新页面
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
//删除班级操作
$("#deleteclass").click(function () {
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
    xhr.open("GET", "deleteStudentClassServlet?classname=" + qx, true);
    xhr.send();
    //c 处理响应并更新页面布局
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            //弹出响应的请求返回的信息
            alert(xhr.responseText);
            //默认点击事件  刷新页面
            $("#flush").click();
        }
    }
});
//修改的操作
$("#repeatClassInfo").click(function (){
    //发送ajaxget请求
    $.ajax({
        cache: true,
        type: "POST",
        url: "repeatStudentClassServlet",
        data: $("#repeatClassInfoForm").serialize(),// 你的formid
        async: false,
        error: function (request) {
            alert("Connection error");
        },
        success: function (data) {
            alert(data)
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
    })
});
});



//接收jacksson数据实现动态载入数据
// //$(function  (){
//     $.ajax({
//         type: "POST",
//         url: "studentClassTable",
//         async: false,
//         error: function (request) {
//             alert("Connection error");
//         },
//         success: function (jsondata) {
//             var arrlist=jsondata.list1;
//             for (i=0;i<arrlist.length;i++){
//                 //将json数据解析出来
//                 var class_id=arrlist[i].class_id;
//                 var class_name=arrlist[i].class_name;
//                 var grade_name=arrlist[i].grade_name;
//                 var class_teacher=arrlist[i].class_teacher;
//                 var class_slogan=arrlist[i].class_slogan;
//                 var class_num=arrlist[i].class_num;
//                 var a=i+1;
//                 var newtr = $("<tr class='info'><td><input name='box'  value="+class_id+ "  type=\"checkbox\"/>"+a+"<td>"+class_id+"</td><td>"+class_name+"</td><td>"+grade_name+"</td><td>"+class_teacher+"</td><td>"+class_slogan+"</td><td>"+class_num+"</td></tr>");
//                 $("tbody").append(newtr);
//             }
//         }
//     });
// });