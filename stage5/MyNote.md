# 1. 常用的Maven命令

构建一个Maven工程项目，整个流程为：

- mvn clean   将target目录下的文件进行清除
- mvn compile 对工程进行编译，生成target目录
- mvn test    在target目录下生成surefire、surefire-reports（测试报告）、test-classes（测试的字节码文件）三个文件夹
- mvn package 打包操作，在target目录下生成jar或者war包(在pom文件中使用<packaging>war</packaging>进行打包成什么样的格式)
- mvn install 将打包好的jar包安装到本地Maven仓库，方便其他工程引用

# 2. Maven依赖的范围介绍

A依赖B，需要在 A 的 pom.xml 文件中添加 B 的坐标，添加坐标时需要指定依赖范围，依赖范围包括：

| 依赖范围 | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| compile  | 编译范围，指 A在编译时依赖 B，此范围为默认依赖范围。编译范围的依赖会用在 编译、测试、运行，由于运行时需要所以编译范围的依赖会被打包。 |
| provided | provided 依赖只有在当 JDK 或者一个容器已提供该依赖之后才使用， provided 依 赖在编译和测试时需要，在运行时不需要，比如：servlet api 被 tomcat 容器提供。 |
| runtime  | runtime 依赖在运行和测试系统的时候需要，但在编译的时候不需要。比如：jdbc 的驱动包。由于运行时需要所以 runtime 范围的依赖会被打包。 |
| test     | test 范围依赖 在编译和运行时都不需要，它们只有在测试编译和测试运行阶段可用， 比如：junit。由于运行时不需要所以test范围依赖不会被打包。 |
| system   | system 范围依赖与 provided 类似，但是你必须显式的提供一个对于本地系统中 JAR 文件的路径，需要指定 systemPath 磁盘路径，system依赖不推荐使用。 |

# 3. Post请求的方式

## 3.1 multipart/form-data

多部件传输方式，也就是说选择这种方式进行上传，可以上传文件和参数

使用Postman工具进行该方式提交数据，如下图所示

![](./img/form-data.png)

这里的file就对应了你需要上传的图片、或者其他文件；Text类型就是普通key-value参数和参数值，下面使用Java进行实现一下文件上传

### 3.1.1 实现文件上传

- 加入依赖包

```xml
<dependencies>
        <!--  文件上传 -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>1.4</version>
        </dependency>

        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.2.1</version>
        </dependency>
</dependencies>    
```

- 编写Java类

```java
public void getFileFromHttpRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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
                            String wabappsPath = realPath.substring(0, realPath.indexOf("lagou_edu_home"));

                            //3.拼接输出路径,将图片保存到 upload
                            FileOutputStream out = new FileOutputStream(wabappsPath+"/upload/" + newFileName);

                            //使用IOUtils完成 文件的copy
                            IOUtils.copy(inputStream,out);

                            //关闭流
                            out.close();
                            inputStream.close();
                        }
                    }

                }
            }
        } catch (FileUploadException  e) {
            e.printStackTrace();
        }
    }
```


## 3.2 application/x-www-form-urlencoded

提交form表单，只能是key-value值

- Java获取form表单里面的参数值

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取post请求中from表单的数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.entrySet().forEach(System.out::print);
    }
```

## 3.3 raw

提交Json数据格式，在Servlet中使用工具类进行解析获取参数值

- Java获取Json数据格式的参数值

```java
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
```

总结：读取请求里面的流，然后转为Map结构

# 4. JSON

Json（JavaScript Object Notation）一种轻量级别的数据交换格式，采用完全独立于语言的文本格式（就是说不用编程语言的JSON数据是一样的），同时易于人阅读和编写，同时也易于机器解析和生成。

## 4.1 XML与JSON

- XML是一种可扩展标记语言，用来标记电子文件使其具有结构性的标记语言

- JSON是一种轻量级的数据交换格式

- 共同点

```text
都作为一种数据交换格式
```

- 不同点

```text
1. XML是重量级的，JSON是轻量级的,XML在传输过程中比较占带宽，JSON占带宽少，易于压缩。
2. XML和json都用在项目交互下，XML多用于做配置文件，JSON用于数据交互
3. JSON独立于编程语言存在,任何编程语言都可以去解析json
```

## 4.2 常用Java对象和JSON格式转换工具类

### 4.2.1 FastJson

- 阿里开发的一个Java类库，实现Java与Json两者之间相互转化

- 特点

```text
1. 支持Java Bean序列化成JSON串，JSON串反序列化成Java Bean
2. 速度快
3. 无其他包依赖
```

- 常用注解

```java
public class Person {
	
    /**
     * @JSONField 注解
     * 指定name属性, 字段的名称
     * 使用 ordinal属性, 指定字段的顺序
     * 使用 serialize属性, 指定字段不序列化
     **/
    @JSONField(name="USERNAME",ordinal = 1)
    private String username;

    @JSONField(name="AGE",ordinal = 2)
    private int age;

    //排除不需要序列化的字段
    @JSONField(serialize = false)
    private String birthday;

    public Person() {
    }

    public Person(String username, int age, String birthday) {
        this.username = username;
        this.age = age;
        this.birthday = birthday;
    }
}
```

- 常用方法

```java
public class Test {
       
    /**
     * JSON.parseObject() 将 JSON 字符串转换为 Java 对象
     * JSON.parseArray() 将 JSON 字符串转换为 集合对象
     **/
      public void JSONToJavaBean(){

        String json = "{\"age\":15,\"birthday\":\"2020-07-03 19:54:33\",\"username\":\"码云\"}";
        Person person = JSON.parseObject(json, Person.class);
        System.out.println(person);

        //创建Person对象
        String json2 ="[{\"age\":15,\"birthday\":\"2020-07-03 19:59:05\",\"username\":\"码云\"},{\"age\":13,\"birthday\":\"2020-07-03 19:59:05\",\"username\":\"虎子\"},{\"age\":18,\"birthday\":\"2020-07-03 19:59:05\",\"username\":\"小斌\"}]";
        List<Person> list  = JSON.parseArray(json2,Person.class);
        System.out.println(list);
    }
}
    
```
# --------------------------------------------------Vue使用---------------------------------------------------------------------

# 2. Vue的入门程序

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello Vue</title>
</head>
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<!-- 引入方式1 引入vue.js的cdn地址-->
<!-- <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script> -->
<!-- 引入方式2 本地js文件引入-->
<script src="../common/js/vue.min.js"></script>
<body>
    <!-- 定义app div, 此区域作为vue的接管区域-->
    <div id = "app">
        <!-- {{}} 双括号是vue中的插值表达式，将表达式的值输出到HTML页面-->
        {{message}}
    </div>
</body>

<script>
    // 创建Vue实例
    var app = new Vue({
        // 定义vue实例挂载的元素节点，表示vue接管该div
        el: '#app',
        // 定义model模型数据对象
        data: {
            message: "Hello, Vue!!"
        }
    })
</script>
</html>
```

## 2.1 总结

编写一个vue程序的步骤

- 定义HTML，引入vue.js
- 定义app div，此区域作为vue接管区域
- 定义vue实例，接管app区域
- 定义model（数据对象）
- 在app中通过插值表达式进行展示数据

## 2.2 vue属性

### 2.2.1 {{}}插值表达式

```
1. 获取data对象中的数据
2. 属性节点中不能够使用插值表达式
```

### 2.2.2 el挂载点

```
1. 定义vue实例挂载额元素节点，表示vue接管该区域
2. 他的作用范围是管理el选项中的元素和他的内部元素
3. 使用el选择挂载点时候，可以使用其他的选择器，但是还是建议使用ID选择器
```

### 2.2.3 data数据对象

```
1. 数据定义data中
2. data可以编写复杂类型数据
3. 渲染复杂类型数据的时候，遵守js语法（看下面的例子）
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello Vue</title>
</head>
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<!-- 引入方式1 引入vue.js的cdn地址-->
<!-- <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script> -->
<!-- 引入方式2 本地js文件引入-->
<script src="../common/js/vue.min.js"></script>
<body>
    <!-- 定义app div, 此区域作为vue的接管区域-->
    <div id = "app">
        <!-- {{}} 双括号是vue中的差值表达式，将表达式的值输出到HTML页面-->
        {{message}}
    </div>



    <!-- 数组使用方法 -->
    <div id = "array">
        <!-- {{}} 双括号是vue中的差值表达式，将表达式的值输出到HTML页面-->
        <ul>
            <li>{{names[0]}}</li>
            <li>{{names[1]}}</li>
            <li>{{names[2]}}</li>
        </ul><br>
        <!-- 对象类型使用方法 -->
        {{role.name}}
        {{role.age}}
    </div>

    
</body>

<script>
    // 创建Vue实例
    var app = new Vue({
        // 定义vue实例挂载的元素节点，表示vue接管该div
        el: '#app',
        // 定义model模型数据对象
        data: {
            message: "Hello, Vue!!"
        }
    });

    var array = new Vue({
        el:'#array',
        data: {
            names:["赵云","黄忠","马超"],
            role: {
                name: "raofy",
                age: 20
            }
        }
    });
</script>
</html>
```

# 3. 声明式渲染

**简单理解就是我们声明数据，vue帮我们进行数据绑定到HTML**

# 4. Vue常用指令

## 4.1 v-text指令

### 4.1.1 作用

**进行获取data的数据，并设置标签的内容**

注意：默认写法会替换全部内容，使用插值表达式{{}}可以替换指定内容

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>V-text测试</title>
  </head>
  <script src="../common/js/vue.min.js"></script>
  <body>
    <div id="app">
      <!-- 使用插值表达式不会被覆盖 -->
      <h2 v-text="msg">百度</h2>

      <!--使用插值表达式不会被覆盖 -->
      <h2>{{msg}}使用插值表达式不会被覆盖</h2>

      <!-- 拼接字符串 -->
      <h2 v-text="msg+1"></h2>
      <h2 v-text="msg+'abc'"></h2>
    </div>

    <script>
      var VM = new Vue({
        el: "#app",
        data: {
          msg: "Java程序员",
        },
      });
    </script>
  </body>
</html>
```

## 4.2 v-html指令

### 4.2.1 作用

设置元素的innerHtml（可以向元素中写入新的标签）

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<body>
    <div id="app">
        <!-- 获取普通文本 -->
        {{msg}}
        <h1 v-text="msg"></h1>
        <h1 v-html="msg"></h1>

        <!-- 设置元素的innerhtml-->
        <h1 v-text="url"></h1>
        <h1 v-html="url"></h1>
    </div>
</body>

<script>
    var vm = new Vue({
        el:"#app",
        data: {
            msg: "Java程序员",
            url: "<a href='https://www.baidu.com'>百度一下</a>"
        }
    })
</script>
</html>
```

## 4.3 v-on指令

### 4.3.1 作用

绑定Vue实例中的method属性，进而绑定对应的事件。

### 4.3.2 语法格式

```
1. v-on:click
2. @click="方法"
```

### 4.3.3 代码实现
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<body>
    <div id="app">
        <input type="button" value="click button" v-on:click="test">
        <input type="button" value="click button" @click="test">
        <input type="button" value="double click button" @dblclick="test">
    </div>

    <!-- 传递参数 -->
    <div id="param">
        <input type="button" value="click button" v-on:click="testParam(1, 2)"/>
    </div>

    <!-- 事件修饰符 指定哪些方式可以触发事件 -->
    <div id="click">
        <input type="text" @keyup.enter="hi"/>
    </div>
    
</body>

<script>
    var vm = new Vue({
        el: "#app",
        methods: {
            test:function() {
                alert("123")
            }
        }    
    });

    var param = new Vue({
        el: "#param",
        methods: {
            testParam:function(p1, p2) {
                alert(p1 + p2)
            }
        }    
    });

    var click = new Vue({
        el: "#click",
        methods: {
            hi:function() {
                alert("Hello Vue!!")
            }
        }    
    })
</script>
</html>
```

### 4.3.4 总结

```
1. 事件绑定方法，可以传入参数
2. 事件后面跟上修饰符可以对事件触发进行限制
3. .enter可以限制触发的按键为回车
4. 事件修饰符有许多使用时可以查询文档
```

## 4.4 计数器案例

### 4.4.1 实现步骤

```
1. 定义data中起始num为1
2. method中定义两个方法：add&sub
3. 使用{{}}插值表达式将num进行绑定到span标签
4. 使用v-on进行绑定add&sub方法
5. 累加到10停止
6. 递减到0停止
```

### 4.4.2 代码实现
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<link rel="stylesheet" href="../common/css/counter/inputNum.css">
<body>
    <div id="app">
        <input type="button" class="btn btn_plus" @click="add">
        <span>{{num}}</span>
        <input type="button" class="btn btn_minus" @click="min">
    </div>
</body>
<script>
    var vm = new Vue({
        el:"#app",
        data:{
            num:1
        },
        methods: {
            add:function() {
                if(this.num < 10) {
                    this.num++;
                } else {
                    alert("点数超过10了")
                }
            },
            min:function() {
                if(this.num > 0) {
                    this.num--;
                } else {
                    alert("点数小于0了")
                }
            }
        }

    })
</script>
</html>
```
### 4.4.3 总结
```
1. 长江Vue实例：el挂载点；data数据对象；methods方法
2. v-on绑定事件
3. this关键字获取data中的数据
4. v-text与{{}}来设置元素的文本值
```
## 4.5 v-show指令

### 4.5.1 作用

根据真假值进行切换显示状态

### 4.5.2 代码实现
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<body>
    <div id="app">
        <input type="button" value="切换状态" @click="change">
        <img v-show="isShow" src="../common/img/gj12qd.jpg"/>
        <img v-show="age>18" src="../common/img/96y8y8.jpg"/>
    </div>
</body>

<script>
    var vm = new Vue({
        el:"#app",
        data:{
            isShow: true,
            age: 19
        },
        methods: {
            change: function() {
                this.isShow = !this.isShow
            }
        }

    })
</script>
</html>
```

### 4.5.3 总结

```
1. 本质是修改display属性值
2. 指令后面的内容最终被解析成布尔值
3. 值为true显示，为false则隐藏
4. 数据改变之后，显示的状态会同步更新
```

## 4.6 v-if指令

### 4.6.1 作用

根据表达式真假进行切换元素的显示和隐藏（操纵dom元素）

### 4.6.2 代码实现
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<body>
    <div id="app">
        <input type="button" value="切换显示状态" @click="change">
        <img v-if="isShow" src="../common/img/96y8y8.jpg"/>
    </div>
    
</body>
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            isShow: true
        },
        methods: {
            change: function() {
                this.isShow = !this.isShow
            }
        }
    })
</script>
</html>
```

### 4.6.3 总结

```
1. 根据表达式进行切换元素的显示状态
2. 本质是通过操作dom元素，为true，元素存在于dom树，为false从dom树中移除
3. 频繁切换使用v-show，反之使用v-if
```

## 4.7 v-bind指令

设置元素的属性（比如src,title,class）

### 4.7.1 作用

### 4.7.2 代码实现
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<script src="../common/js/vue.min.js"></script>
<body>
    <div id="app">
        <img v-bind:src="imgSrc"/>
        <img :src="imgSrc" :title="imgTitle"/>
        <div :style="{
            fontSize: size + 'px'
        }">v-bind指令</div>
    </div>
    
</body>
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            imgSrc:"../common/img/96y8y8.jpg",
            imgTitle:"图片标题",
            size: 100
        }
    })
</script>
</html>
```

### 4.7.3 总结
```
1. 为元素进行绑定属性
2. 语法格式：v-bind:属性名 or :属性名
```

## 4.8 v-for指令

### 4.8.1 作用

根据数据生成列表数据

### 4.8.2 代码实现
```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <script src="../common/js/vue.min.js"></script>
  <body>
    <div id="app">
      <input type="button" value="添加数据" @click="add" />
      <input type="button" value="移除数据" @click="remove" />
      <ul>
        <!-- 在li标签中获取数组元素 -->
        <li v-for="(item,index) in arr">{{index+1 }}城市: {{item}}</li>
      </ul>
      <!-- 使用h2标签显示v-for 结合 v-bind一起使用-->
      <h2 v-for="p in persons" v-bind:title="p.name">{{p.name}}</h2>
    </div>
  </body>
  <script src="./vue.min.js"></script>
  <script>
    var VM = new Vue({
      el: "#app",
      data: {
        //普通数组
        arr: ["上海", "北京", "天津", "杭州"],
        //对象数组
        persons: [{ name: "尼古拉斯·赵四" }, { name: "莱安纳多·小沈阳" }],
      },
      methods: {
        add: function () {
          //push 添加
          this.persons.push({ name: "多利安·刘能" });
        },
        remove: function () {
          this.persons.shift();
        },
      },
    });
  </script>
</html>
```

### 4.8.3 总结

```
1. 常用于生成列表数据
2. 数组和v-for配合使用，push方法向数组末尾添加一个或者多个元素，shift把数组的第一个元素删除
3. 数组的长度变化都会同步更新到页面上，都是响应式的
```

## 4.9 v-model指令

### 4.9.1 作用

作用：获取和设置表单元素的值（**实现双向数据绑定**）

### 4.9.2 代码实现

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>测试</title>
  </head>
  <script src="../common/js/vue.min.js"></script>
  <body>
    <div id="app">
      <!-- 单向数据绑定 -->
      <h2>{{msg}}</h2>

      <!-- 双向数据绑定 -->
      <input type="text" v-model="msg"/>
    </div>

    <script>
      var VM = new Vue({
        el: "#app",
        data: {
          msg: "Java程序员",
        }
      });
    </script>
  </body>
</html>

```

# 5 MVVM模式

Model-View-ViewModel的简写，一种基于前端开发的架构模式

- Model：负责数据存储（data）

- View：负责页面展示（{{}}）

- View Model：负责业务处理，对数据进行加工传给视图展示

代码演示

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MVVM模式</title>
  </head>
  <script src="../common/js/vue.min.js"></script>
  <body>
    <div id="app">
      <!-- View 视图部分-->
      <h2>{{msg}}使用插值表达式不会被覆盖</h2>

      <!-- View视图部分 -->
      <h2 v-text="msg+1"></h2>
      <h2 v-text="msg+'abc'"></h2>
    </div>

    <script>
      // 创建vue实例，就是VM 
      var VM = new Vue({
        el: "#app",
        // model部分
        data: {
          msg: "Java程序员",
        },
      });
    </script>
  </body>
</html>
```

## 5.1 单向数据绑定

**把model绑定到view，使用JS进行更新model时候，View就会自动更新**


## 5.2 双向数据绑定

**View得到了更新，Model的数据也会自动跟着更新**


## 5.3 v-model指令（实现双向数据绑定）

### 5.3.1 代码实现

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>测试</title>
  </head>
  <script src="../common/js/vue.min.js"></script>
  <body>
    <div id="app">
      <!-- 单向数据绑定 -->
      <h2>{{msg}}</h2>

      <!-- 双向数据绑定 -->
      <input type="text" v-model="msg"/>
    </div>

    <script>
      var VM = new Vue({
        el: "#app",
        data: {
          msg: "Java程序员",
        }
      });
    </script>
  </body>
</html>
```
### 5.3.2 总结

```
1. 使用v-model指令是便捷的设置和获取表单元素的值
2. 绑定的数据会和表单元素值相关联
3. 双向数据绑定
```

## 5.4 实现简单记事本

### 5.4.1 实现步骤

```
1. 使用v-for生成列表
2. 通过v-model进行数据双向绑定
3. 通过回车进行新增输入的数据
4. 删除数据
5. 清空所有数据
```

### 5.4.2 代码实现

```html
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>小黑记事本</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="robots" content="noindex, nofollow" />
    <meta name="googlebot" content="noindex, nofollow" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" type="text/css" href="../common/css/counter/index.css" />
  </head>

  <body>
    <!-- VUE示例接管区域 -->
    <section id="app">

      <!-- 输入框 -->
      <header class="header">
        <h1>VUE记事本</h1>
        <input
          autofocus="autofocus"
          autocomplete="off"
          placeholder="输入日程"
          class="new-todo"
          v-model="input"
          @keyup.enter="add"
        />
      </header>

      <!-- 列表区域 -->
      <section class="main">
        <ul class="listview">
          <li class="todo" v-for="(item,index) in arr">
            <div class="view">
              <span class="index">{{index + 1}}.</span> <label>{{item}}</label>
              <button class="destroy"@click="remove(index)"></button>
            </div>
          </li>
        </ul>
      </section>
       <!-- 统计和清空 -->
       <footer class="footer">
        <span class="todo-count"> <strong>{{arr.length}}</strong> items left </span>
        <button class="clear-completed"  @click="removeAll">
          Clear
        </button>
      </footer>
    </section>
    
    <!-- 开发环境版本，包含了有帮助的命令行警告 -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

    <script>
      var vm = new new Vue({
        el:"#app",
        data:{
          arr:["写代码","吃饭","睡觉"],
          input:"996还是997"
        },
        methods:{
          add:function() {
            this.arr.push(this.input)
          },
          remove:function(index) {
            // 使用splice(元素索引,删除几个) 根据索引删除
            this.arr.splice(index, 1);
          },
          removeAll:function() {
            this.arr = []
          }
        }
      })
    </script>

  </body>
</html>
```


# ------------------------------------------Vue高级使用-------------------------------------------

# Vue项目运行流程

## main.js

项目运行的时候首先加载入口文件main.js

```js
// 导入from文件路径文件;在HTML文件中通过script src = 'xx'进行导js文件
// vue中通过import 变量名 from 文件路径  导入文件
import { createApp } from 'vue'
// 主组件
import App from './App.vue'
// 路由
import router from './router'
  
// 创建Vue实例，并为整个项目添加路由，挂载的是App.vue组价中的id为app的区域
createApp(App).use(router).mount('#app')

```

## App.vue

### vue的组成结构

- <template>相当于HTML代码

- <style> 页面的样式编写

- <script> 编写的js代码

```vue
<template>
  <div id="nav">
    <!-- 路由导航连接，to="/"项目路径，跳转的是首页-->
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <!-- 根据访问的路径进行渲染对应的组件-->
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>

```

## router路由

```js
// 引入vue-router库
import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'

// 创建路由规则
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

// 创建路由管理器
const router = createRouter({
  history: createWebHashHistory(),
  routes
})

// export导出模块router就代表了整个路由文件
export default router

```

## Home.vue组件

从上面的执行流程可以看到，最先加载渲染的是Home.vue组件

```vue
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
// @ 相当于src文件夹路径的简写
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
```

这里导入了HelloWord.vue组件，看一下对应的代码

```vue
<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      For a guide and recipes on how to configure / customize this project,<br>
      check out the
      <a href="https://cli.vuejs.org" target="_blank" rel="noopener">vue-cli documentation</a>.
    </p>
    <h3>Installed CLI Plugins</h3>
    <ul>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-babel" target="_blank" rel="noopener">babel</a></li>
      <li><a href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-router" target="_blank" rel="noopener">router</a></li>
    </ul>
    <h3>Essential Links</h3>
    <ul>
      <li><a href="https://vuejs.org" target="_blank" rel="noopener">Core Docs</a></li>
      <li><a href="https://forum.vuejs.org" target="_blank" rel="noopener">Forum</a></li>
      <li><a href="https://chat.vuejs.org" target="_blank" rel="noopener">Community Chat</a></li>
      <li><a href="https://twitter.com/vuejs" target="_blank" rel="noopener">Twitter</a></li>
      <li><a href="https://news.vuejs.org" target="_blank" rel="noopener">News</a></li>
    </ul>
    <h3>Ecosystem</h3>
    <ul>
      <li><a href="https://router.vuejs.org" target="_blank" rel="noopener">vue-router</a></li>
      <li><a href="https://vuex.vuejs.org" target="_blank" rel="noopener">vuex</a></li>
      <li><a href="https://github.com/vuejs/vue-devtools#vue-devtools" target="_blank" rel="noopener">vue-devtools</a></li>
      <li><a href="https://vue-loader.vuejs.org" target="_blank" rel="noopener">vue-loader</a></li>
      <li><a href="https://github.com/vuejs/awesome-vue" target="_blank" rel="noopener">awesome-vue</a></li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>
```

# 自定义组件

## 编写Hearder.vue组件

```vue
<template>
   <h1 class="header">{{msg}}</h1>
</template>

<script>
export default {
    name:"Header", //组件的名称
    data() {
        return{
            "msg":"这是自定义的一个header组件"
        }
    }
}
</script>

<!--scoped表示当前的样式，只能作用于当前组件中的template视图-->
<style scoped>
.header {
    height: 100px;
    line-height: 100px;
    background-color: #eee;
    text-align: center;
    color: blue;
}
</style>
```

## 导入Header组件
```vue
<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <Header/>
  </div>
</template>

<script>
// @ is an alias to /src
import Header from '@/components/Header.vue'

export default {
  name: 'Home',
  components: {
    Header
  }
}
</script>
```

## 对自定义组件进行传参

```vue
<template>
   <h1 class="header">{{msg}}</h1>
</template>

<script>
export default {
    name:"Header", //组件的名称
    props: {
        msg: String
    }
}
</script>

<!--scoped表示当前的样式，只能作用于当前组件中的template视图-->
<style scoped>
.header {
    height: 100px;
    line-height: 100px;
    background-color: #eee;
    text-align: center;
    color: blue;
}
</style>
```

# Element-ui

是饿了么基于Vue进行封装的前端组件库

# 构建导入Element-ui组件库工程

```shell
# 创建Vue工程
vue create el-project

# 下载element-ui组件库
npm install element-ui -S
```

## 导入组件库到main.js入口文件中
```js
import Vue from 'vue'
import App from './App.vue'
import router from './router'
//导入组件库
import ElementUI from 'element-ui'

//导入组件相关样式
import 'element-ui/lib/theme-chalk/index.css'

//配置Vue插件将El安装到
Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
```

## 测试导入是否成功
```vue
<template>
  <div id="app">
    <div id="nav">
      <el-row>
        <el-button disabled>默认按钮</el-button>
        <el-button type="primary" disabled>主要按钮</el-button>
        <el-button type="success" disabled>成功按钮</el-button>
        <el-button type="info" disabled>信息按钮</el-button>
        <el-button type="warning" disabled>警告按钮</el-button>
        <el-button type="danger" disabled>危险按钮</el-button>
      </el-row>
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view/>
  </div>
</template>
```




