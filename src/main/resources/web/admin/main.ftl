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
                            <p><strong>
                            <#if blog??>
                            编辑文章 ${blog.postCode}<input type="hidden" name="post_code" value="${blog.postCode}" />
                            <#else>
                            创建新文章<input type="hidden" name="post_code" value="0" />
                            </#if>
                            </strong></p>       
                        </header>
                        <p></p>
                        <div class="form-group">
                            <label class="control-label col-sm-2">标题</label>
                            <div class="col-sm-7"><input type="text" name="rich_title" class="form-control" <#if blog??>value="${blog.postTitle}"</#if>/></div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2">内容</label>
                            <div class="col-sm-7">
                                <nav class="tabnav-tabs">
                                <button type="button" class="js-write-tab selected">Write</button>
                                <button type="button" class="js-preview-tab">Preview</button>
                                </nav>
                                <div class="write-contet">
                                <textarea class="form-control" name="content" id="content"><#if blog??>${blog.postContent}</#if></textarea>
                                </div>
                                <div class="preview-content"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2">时间</label>
                            <div class="col-sm-7">
                                <input class="datepicker" type="text" name="content_date" value="<#if blog??>${blog.postDate?string('yyyy-MM-dd HH:mm')}<#else>${.now?string('yyyy-MM-dd HH:mm')}</#if>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2">关键字</label>
                            <div class="col-sm-7">
                                <input type="text" name="small_title" <#if blog??>value="${blog.postSmallTitle?replace('-',' ')}"</#if> />
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-info js_start" id="post_blog">保存</button>
                        </div>
                    </section>
                </section>
            </section>        
        </section>
    </section>
    </section>

<#include "/inc/foot.ftl"/>
</body>
</html>