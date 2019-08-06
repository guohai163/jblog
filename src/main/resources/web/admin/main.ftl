<!DOCTYPE html>
<html lang="en">
<head>
	<title>${blog_name}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css" />
    <link rel="stylesheet" type="text/css" href="/css/jquery.datetimepicker.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/markdown.css" />
</head>
<body>
<div class="admin-main">
    <#include "/inc/admin-left.ftl"/>

    <div class="admin-body">
        <div class="ul-head">
        <#if blog??>
        <h2>编辑文章 ${blog.postCode}<input type="hidden" name="post_code" value="${blog.postCode}" /></h2>
        <#else>
        <h2>创建新文章<input type="hidden" name="post_code" value="0" /></h2>
        </#if>
        </div>
        <div class="editor-title"><input class="title input-block form-control" type="text" name="rich_title" <#if blog??>value="${blog.postTitle}"</#if> /></div>
        <div class="js-previewable-form">
            <div class="comment-form-head tabnav"><nav class="tabnav-tabs">
            <button type="button" class="js-write-tab selected">Write</button>
            <button type="button" class="js-preview-tab">Preview</button>
            </nav></div>

            <div class="write-contet">
                <textarea class="from-content" name="content" id="content"><#if blog??>${blog.postContent}</#if></textarea>
            </div>
            <div class="preview-content"></div>
            <div class="admin-div">
                <span><input class="datepicker" type="text" name="content_date" value="<#if blog??>${blog.postDate?string('yyyy-MM-dd HH:mm')}<#else>${.now?string('yyyy-MM-dd HH:mm')}</#if>"/></span>
                <span><input type="text" name="small_title" <#if blog??>value="${blog.postSmallTitle?replace('-',' ')}"</#if> /></span>
            </div>
            <div class="admin-div"><a class="jblog-a-btn" id="post_blog" >保存</a></div>
        </div>


    </div>


</div>
<#include "/inc/foot.ftl"/>



</body>
</html>