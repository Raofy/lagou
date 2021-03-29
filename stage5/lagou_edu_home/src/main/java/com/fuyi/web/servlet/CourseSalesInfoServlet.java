package com.fuyi.web.servlet;

import com.fuyi.base.Constants;
import com.fuyi.dao.CourseDao;
import com.fuyi.dao.impl.CourseDaoImpl;
import com.fuyi.pojo.Course;
import com.fuyi.utils.DateUtils;
import com.fuyi.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "CourseSalesInfoServlet", urlPatterns = "/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 创建Course对象
            Course course = new Course();

            // 创建Map集合，用来收集数据
            HashMap<String, Object> paramsMap = new HashMap<>();

            // 1. 创建磁盘文件工厂对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 2. 创建文件上传核心类
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            // 2.1 设置上传文件的编码
            servletFileUpload.setHeaderEncoding("UTF-8");
            // 2.2 判断表单是否是文件上传表单
            if (servletFileUpload.isMultipartContent(request)) {           // 是多部文件上传表单
                // 3. 解析request -> 获取表单项的集合
                List<FileItem> list = servletFileUpload.parseRequest(request);
                if (null != list) {
                    // 4. 遍历集合获取表单项
                    for (FileItem fileItem : list) {
                        // 5. 判断当前表单项 是不是普通表单项
                        if (fileItem.isFormField()) {                      // 普通表单项
                            String fieldName = fileItem.getFieldName();
                            // 设置编码
                            String fieldValue = null;//设置编码
                            try {
                                fieldValue = fileItem.getString("utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            paramsMap.put(fieldName, fieldValue);
                            System.out.println(fieldName +" = " +fieldValue);
                        } else {
                            //文件上传项
                            //获取文件名
                            String fileName = fileItem.getName();

                            // 拼接新的文件名，使用UUID保证唯一性
                            String newFileName = String.format("%s_%s", UUIDUtils.getUUID(), fileName);

                            // 获取输入流
                            InputStream inputStream = fileItem.getInputStream();

                            //创建输出流
                            //1.获取项目的运行目录 J:\install\apache-tomcat-9.0.41\webapps\lagou_edu_home\
                            String realPath = this.getServletContext().getRealPath("/");

                            //2.截取到 webapps目录路径
                            String webAppsPath = realPath.substring(0, realPath.indexOf("lagou_edu_home"));

                            // 将路径保存到数据库
                            String format = String.format("%s/upload/%s", Constants.LOCAL_URL, newFileName);
                            paramsMap.put("course_img_url", format);

                            //3.拼接输出路径,将图片保存到 upload
                            FileOutputStream out = new FileOutputStream(webAppsPath+"/upload/" + newFileName);

                            //使用IOUtils完成 文件的copy
                            IOUtils.copy(inputStream,out);

                            //关闭流
                            out.close();
                            inputStream.close();
                        }
                    }

                }

                // 使用BeanUtil进行转成Course对象
                BeanUtils.populate(course, paramsMap);
                String dateFormat = DateUtils.getDateFormat();

                CourseDao courseDao = new CourseDaoImpl();

                if (paramsMap.get("id") != null) {                // 更新操作
                    // 修改消息
                    // 补全消息
                    course.setUpdate_time(dateFormat);
                    String result = courseDao.updateCourseSalesInfo(course);
                    response.getWriter().print(result);
                } else {                                          // 插入操作
                    // 新建操作
                    // 补全消息
                    course.setCreate_time(dateFormat);
                    course.setUpdate_time(dateFormat);
                    course.setStatus(1);                         // 上架
                    String result = courseDao.courseSalesInfo(course);
                    response.getWriter().print(result);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
