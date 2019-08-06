<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css" />
	<link rel="stylesheet" href="/css/jquery-confirm.min.css">
</head>
<body>
<div class="admin-main">

    <#include "/inc/admin-left.ftl"/>
    <div class="admin-body">
        <div class="ul-head"><h2>BLOG列表</h2></div>
        <ul class="admin-ul">
            <#list listContent as content>
                <li class="admin-li">
                <#if content.postStatus == "publish">🔵<#else>🔴</#if>
                <#if content.postTitle?length gt 21>${content.postTitle?substring(0,20)}...
                <#else>${content.postTitle}</#if>
                <span class="float-right">
                <a class="li-btn li-btn-del" id="${content.postCode}">del</a>
                <a class="li-btn" href="/admin/main?postCode=${content.postCode}">edit</a></span>
                <time class="float-right" datetime="${content.postDate?string('yyyy-MM-dd')}">${content.postDate?string('yyyy-MM-dd')}</time>
                
                </li>
            </#list>
        </ul>

        <div>
        <span><#if (maxPageNum > pageNum)><a href="/admin/list?page=${pageNum+1}">上一页</a></#if></span>
        <span class="float-right"><#if (pageNum > 1)><a href="/admin/list?page=${pageNum-1}">下一页</a></#if></span>
        </div>

    </div>
</div>
<#include "/inc/foot.ftl"/>

</body>
</html>