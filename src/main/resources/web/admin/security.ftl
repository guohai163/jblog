<!DOCTYPE html>
<html>
<head>
	<#include "/inc/admin-head.ftl" />
</head>
<body>
    <section class="main-section">
    <header>
        <div class="navbar-header">jBlog</div>
    </header>
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
                </section>
            </section>        
        </section>
    </section>
    </section>
<#include "/inc/foot.ftl"/>
</body>
</html