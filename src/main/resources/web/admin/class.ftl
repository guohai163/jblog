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
                <header class="panel-heading">
                    <p><strong>分类管理</strong></p>
                </header>
                <!--表格主体-->
                <section class="scrollable padder">
                    <div class="m-b-md">
                    </div>
                    <section class="panel panel-default panel-succes">
                        <div class="table-responseive">
                            <table class="table table-striped">
                                <thead><tr><th>分类</th><th>操作</th></tr></thead>
                                <tbody class="table-class">
                                    <#list classTypeList as classItem>
                                    <tr><td><input type="text" id="input_class_${classItem.classCode}" value="${classItem.className}"
                               readonly="readonly" disabled="disabled"
                               style="width: 160px;height: 30px;border-radius:8px"/></td>
                                    <td><a class="li-btn li-class-btn-del" class_code="${classItem.classCode}">del</a>
                            <a class="li-btn li-class-btn-edit" class_code="${classItem.classCode}">edit</a></td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </section>
                <div>
                    <span><a class="btn btn-info li-class-btn-add-li">add</a></span>
                </div>
            </section>        
        </section>
    </section>
    </section>
<#include "/inc/foot.ftl"/>
</body>
</html>