# 西川在线视频课程系统

## 源码下载
使用下面的命令将源码从远程仓库拉取到本地，需要本地提前安装好git

```
git clone https://
或
git clone ssh://
```

##项目说明
* **admin**<br>
控台管理，vue cli项目
* **business**<br>
核心业务模块，spring boot项目
* **doc**<br>
项目相关的文档，包含项目数据库初始化脚本
* **eureka**<br>
注册中心，spring boot项目
* **file**<br>
文件模块，spring boot项目
* **gateway**<br>
路由模块，spring boot项目
* **generator**<br>
代码生成器
* **server**<br>
公共jar模块，被business，file，system依赖
* **system**<br>
系统模块，spring boot项目
* **web**<br>
网站模块，vue cli项目

##项目初始化
* **需要本地安装好idea nodejs jdk1.8 mysql5.7 navicat(数据库可视化工具) redis**<br>
* **将下载好的源码，用idea打开**<br>
* **刷新maven依赖**<br>
* **安装vue cli，npm install -g@vue/cli**<br>
* **下载node模块，初始化web模块cd web npm install**<br>
* **初始化admin模块 cd admin npm install**<br>
* **新建数据库coursedev，并运行初始脚本/doc/db/all.sql**<br>
##项目启动
* **启动注册中心：EurekaApplication**<br>
* **自动路由模块：GatewayApplication**<br>
* **启动系统模块：SystemApplication**<br>
* **启动业务模块：BusinessApplication**<br>
* **启动文件模块：FileApplication**<br>
* **启动控台模块：admin\package.json**<br>
* **启动前台模块：web\package.json**<br>
##项目启动
* **控台地址：http://localhost:8080/login**<br>
  初始化用户名密码：admin/admin
* **前台地址admin：http://localhost:8081**<br>
  可以自己注册用户，短信验证码从后台日志看 或者看sms表
##资源配置
**所有资源都在/doc/db/resource.json**<br>
需要在控台上将所有的资源配置进去，并在角色管理中配置权限 权限配置好后，需要重新登录



