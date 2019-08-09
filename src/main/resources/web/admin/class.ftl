<!DOCTYPE html>
<html lang="en">
<head>
    <title>${blog_name}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="/css/jblog.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery-confirm.min.css">
</head>
<body>
<div class="admin-main">

<#include "/inc/admin-left.ftl"/>
    <div class="admin-body">
        <div class="ul-head"><h2>分类管理</h2></div>
        <ul class="admin-ul">
            <li>
                <ul class="li-class-show">
                <#list classTypeList as classItem >
                    <li class="admin-li">
                        <input type="text" id="input_class_${classItem.classCode}" value="${classItem.className}"
                               readonly="readonly" disabled="disabled"
                               style="width: 160px;height: 30px;border-radius:8px"/>
                        <span class="float-right">
                            <a class="li-btn li-class-btn-del" class_code="${classItem.classCode}">del</a>
                            <a class="li-btn li-class-btn-edit" class_code="${classItem.classCode}">edit</a>
                        </span>
                    </li>
                </#list>
                </ul>
            </li>
            <li class="li-class-add">
                <span class="float-right">
                    <a class="li-btn li-class-btn-add-li">add</a>
                </span>
            </li>
        </ul>
    </div>
</div>
<#include "/inc/foot.ftl"/>
</body>
</html>