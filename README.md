# Tlias 智能辅助学习系统
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Java](https://img.shields.io/badge/Java-blue.svg) Java 17
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green.svg) Spring Boot 2.7.x
![Vue 3](https://img.shields.io/badge/Vue-brightgreen.svg) Vue 3.x
![MySQL](https://img.shields.io/badge/MySQL-orange.svg) MySQL 8.0

Tlias（Intelligent Assisted Learning System）是一款面向职业教育场景的智能辅助学
习管理系统，聚焦「教师、系统管理员」两类核心用户，实现班级管理、课程学习、违纪扣分、数据统计、权限管控等全流程教学辅助功能，解决传统教学中资源分散、管理效率低、数据统计难等痛点。
🌟 核心特性

教师端
班级与学员管理：支持批量导入 / 导出学员信息，维护考勤、违纪记录
数据统计分析：生成班级考勤率、作业完成率、成绩分布等可视化报表

管理员端
精细化权限管控：基于 RBAC 模型配置角色权限，确保最小权限原则
系统运维管理：操作日志审计、核心数据备份 / 恢复、服务器状态监控
批量数据维护：支持部门、员工、班级等基础数据的批量管理

🛠 技术栈

前端
技术 / 框架	版本	用途
Vue	3.x	核心前端框架
Vite	最新版	前端构建工具
Element Plus	最新版	UI 组件库
Pinia	最新版	状态管理
ECharts	5.x	数据可视化图表

后端
技术 / 框架	版本	用途
Java	17	核心开发语言
Spring Boot	2.7.x	后端核心框架
MyBatis Plus	3.5.x	ORM 框架，简化数据库操作
JWT	最新版	无状态登录认证

数据层
技术	版本	用途
MySQL	8.0	核心业务数据存储
MySQL 读写分离	-	提升高并发查询性能

🚀 快速开始
环境要求
JDK 17+
MySQL 8.0+
Node.js 16+ (前端构建)
Maven 3.8+ (后端构建)
部署步骤
1. 克隆项目
bash
运行
git clone https://github.com/ouvou/tlias.git
cd tlias
2. 数据库配置
创建数据库：CREATE DATABASE tlias DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
执行初始化脚本：sql/tlias_init.sql (包含表结构、初始数据、索引)
修改后端配置：backend/src/main/resources/application.yml
yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tlias?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: [你的数据库用户名]
    password: [你的数据库密码]

3. 后端启动
bash
运行
cd backend
mvn clean package
java -jar target/tlias-web-management.jar
访问地址：http://localhost:8080

4. 前端启动
bash
运行
cd frontend
npm install
npm run dev
访问地址：http://localhost:80
5. 初始账号
角色	用户名	密码
管理员	Mark	123456789


🎨 界面预览
模块	预览说明
管理员权限配置	角色 - 权限关联配置界面
注：可在 docs/screenshots 目录查看完整界面截图

📄 许可证
本项目采用 MIT 开源许可证 - 详见 LICENSE 文件。

🤝 贡献指南
Fork 本仓库
创建特性分支 (git checkout -b feature/AmazingFeature)
提交修改 (git commit -m 'Add some AmazingFeature')
推送到分支 (git push origin feature/AmazingFeature)
打开 Pull Request

📞 联系方式
作者：Leven
邮箱：zwl2921003148@gmail.com
项目地址：https://github.com/ouvou/tlias 
✨ 致谢
感谢 Element Plus 提供的优质 UI 组件
感谢 MyBatis Plus 简化数据库操作
感谢 Redisson 解决分布式并发问题
总结
这份 README 包含开源项目核心要素：
核心特性：按角色拆分功能，突出系统核心价值；
技术栈：清晰罗列前后端 / 数据层技术，补充 Redisson 并说明用途；
快速开始：提供完整的环境要求、部署步骤和初始账号，新手可快速上手；
架构与预览：用 Mermaid 展示架构，补充界面预览说明；
开源规范：包含许可证、贡献指南、联系方式，符合 GitHub 开源项目规范。
