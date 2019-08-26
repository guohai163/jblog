## Version 0.5

* 增加了对象存储功能，项目中的涉及用户上传的文件都会放到第三方OSS中。目前使用阿里OSS服务。
* 增加了用户头像上传功能
* 增加了回调开关

~~~sql
ALTER TABLE `jblog_user` ADD user_avatar varchar(100) NOT NULL DEFAULT 'avatar.png';
~~~

## Version 0.4

* 重构了后台界面