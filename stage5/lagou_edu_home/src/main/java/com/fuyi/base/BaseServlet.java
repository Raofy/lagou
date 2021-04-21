package com.fuyi.base;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 获取methodName参数
        String methodName = null;

        // 1.1 获取POST请求的Content-Type类型
        String contentType = request.getHeader("Content-Type");

        // 1.2 判断传递格式是不是JSON格式
        if("application/json;charset=utf-8".equalsIgnoreCase(contentType) || "application/json".equals(contentType)){
            //是JOSN格式 调用getPostJSON
            String postJSON = getPostJSON(request);

            //将JSON格式的字符串转化为map
            Map<String,Object> map = JSON.parseObject(postJSON, Map.class);

            //从map集合中获取 methodName
            methodName =(String) map.get("methodName");

            // 将获取的对象保存到request域对象里面
            request.setAttribute("map",map);

        }else{
            methodName = request.getParameter("methodName");
        }


        // 2. 通过反射机制寻找对应servlet类的方法(优化代码，提高维护性)
        if (null != methodName) {

            // 这里this指的是继承BaseServlet的具体子类Servlet
            Class c = this.getClass();

            try {
                // 获取对应的方法
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                // 执行对应的方法
                method.invoke(this, request, response);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("没有对用的方法");
            }
        }
    }

    /**
     * POST请求格式为:  application/json;charset=utf-8，使用该方法进行读取
     *
     * */
    public String getPostJSON(HttpServletRequest request){

        try {
            //1.从request中 获取缓冲输入流对象
            BufferedReader reader = request.getReader();

            //2.创建StringBuffer 保存读取出的数据
            StringBuffer sb = new StringBuffer();

            //3.循环读取
            String line = null;
            while((line = reader.readLine()) != null){
                //将每次读取的数据 追加到StringBuffer
                sb.append(line);
            }

            //4.返回结果
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
