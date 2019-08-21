<!DOCTYPE html>
<html>
<head>
	<#include "/inc/admin-head.ftl" />
</head>
<body>
    <section class="main-section">
    <#include "/inc/admin-header.ftl" />
    <section class="main-down">
        <#assign ftlname = .current_template_name>
        <#include "/inc/admin-left.ftl" />
        <!--右侧区域-->
        <section class="right-content">
            <section class="vbox">
                
                <!--表格主体-->
                <section class="scrollable padder">
                    <div class="m-b-md">
                    </div>
                    <section class="panel panel-default panel-succes">
                        <header class="panel-heading">
                            <p><strong>管理账户
                            </strong></p>       
                        </header>
                        <p></p>
                        <div class="form-group">
                            <label class="control-label col-sm-2">密码</label>
                            <div class="col-sm-7"><input type="text" name="new_pass" class="form-control"/></div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2">确认密码</label>
                            <div class="col-sm-7"><input type="text" name="config_new_pass" class="form-control"/></div>
                        </div>

                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info js_start" id="update_pass">更新密码</button>
                        </div>
                    </section>
                    <div class="m-b-md">
                    </div>
                    <section class="panel panel-default panel-succes">
                        <header class="panel-heading">
                                                <p><strong>修改头像
                                                </strong></p>
                        </header>
                        <p></p>
                        <div class="form-group">
                            <ul id="filelist"></ul>
                        </div>
                        <div class="modal-footer">
                            <a id="browse" class="btn btn-info js_start" href="javascript:;">浏览文件</a>
                            <button type="submit" class="btn btn-info js_start" id="start-upload">上传头像</button>
                        </div>
                    </section>
                </section>

                <p></P>
                <section class="scrollable padder">

                </section>
            </section>        
        </section>
    </section>
    </section>
<#include "/inc/foot.ftl"/>
<script type="text/javascript" src="/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/upload.js"></script>
</body>
</html