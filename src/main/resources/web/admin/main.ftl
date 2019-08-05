<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css" />
    <link rel="stylesheet" type="text/css" href="/css/jquery-ui.css" />
    <link rel="stylesheet" type="text/css" href="/css/markdown.css" />
</head>
<body>
<div class="admin-main">
    <#include "/inc/admin-left.ftl"/>

    <div class="admin-body">
        <div class="ul-head"><h2>创建新文章</h2></div>
        <div class="editor-title"><input class="title input-block form-control" type="text" name="rich_title" /></div>
        <div class="js-previewable-form">
            <div class="comment-form-head tabnav"><nav class="tabnav-tabs">
            <button type="button" class="js-write-tab selected">Write</button>
            <button type="button" class="js-preview-tab">Preview</button>
            </nav></div>

            <div class="write-contet">
                <textarea class="from-content" name="content" id="content"></textarea>
            </div>
            <div class="preview-content"></div>
            <div class="admin-div">
                <span><input class="datepicker" type="text" name="content_date" value="${.now}"/></span>
                <span><input type="text" name="small_title" /></span>
            </div>
            <div class="admin-div"><a class="btn" id="post_blog" >保存</a></div>
        </div>


    </div>


</div>
<#include "/inc/foot.ftl"/>
<script src="/js/jblog-admin.js"></script>



</body>
</html>