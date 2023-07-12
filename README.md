<div align="center">

<h1 style="border-bottom: none">
    <b>SFileHub</b><br />
</h1>

</div>

<div align="center">

[![](https://github.com/KeyonYan/SFileHub/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/KeyonYan/SFileHub/actions/workflows/maven.yml)
[![](https://github.com/KeyonYan/SFileHub/workflows/Node.js%20CI/badge.svg)](https://github.com/KeyonYan/SFileHub/actions/workflows/node.yml)

</div>

## Tech Stack

前端技术：

`React` `Vite` `TypeScript`

UI：`Tailwind` `AndDesign`

路由管理：`React-router-dom`

状态管理：`zustand`

后端技术：

`Spring` `SpringBoot` `SpringSecurity` `SpringDataJPA`

数据库：

`MySQL` `Redis`

## Features

* [X] **SpringSecurity**配置**前后端分离**，将JsonLoginFilter加入FilterChain重写授权策略
* [X] @ControllerAdvice配置**全局异常处理**GlobalExceptionHandler
* [X] vite.config.js**配置Node代理**解决前后端分离跨域问题
* [X] 自定义AntDesign Upload组件的请求方法，实现**分片上传**
* [X] **践行Github开源工作流**，dev分支开发完成一个功能点后，提PR给main分支，main分支进行分支合并
* [X] 编写**集成测试**代码，借助后端CI实现自动化测试
