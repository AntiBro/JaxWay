# JaxWay - 基于springcloud-gateway的动态网关

项目使用前后端分离部署,本项目都是后端代码，前端代码地址，移步到 [这里](https://github.com/AntiBro/jaxway-admin-vue-ui)

## 如果本项目对您有所帮助欢迎 star

Jaxway 目前有2种定义的运行模式
1. 通用模式
2. 严格的权限校验模式(doing...)

## 通用模式
通用模式下和一般的SpringCloud gateway网关一样，在JaxAdmin 后台中配置对应网关的路由信息即可



## 权限校验模式
权限校验模式下的JaxAdmin 后台模块，正在开发中...


## 部署与运行
1. 部署前端代码，[这里](https://github.com/AntiBro/jaxway-admin-vue-ui) 构建前端代码后 需要通过nginx部署静态文件地址
2. 部署Jaxway Admin后台(jax-admin模块),用于管理网关的权限以及动态路由,需要配置数据库地址以及初始化Jaxway需要的数据库信息,数据库DB文件[这里](https://github.com/AntiBro/jax-way/tree/master/jax-admin-dao/src/main/resources/sql)
3. 部署Jaxway Server(jax-server模块),使用通用模式下 需要 将 spring.jaxway.filter.enable 设置为false,同时需要使用 jaxway.host 来指定Jaxway Admin的后端地址,以及jaxway.server.id即当前Jaxway Server在Jaxway Admin中的id信息
4. 在Jaxway Admin后台中添加动态路由信息
 
### 架构图
![image](https://raw.githubusercontent.com/AntiBro/JaxWay/master/doc/1.jpg)



邮箱:1916657120@qq.com