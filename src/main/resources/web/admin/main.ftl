<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login V14</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/css/jblog.css">
    
</head>
<body>
<div class="admin-main">
    <#include "/inc/admin-left.ftl"/>

    <div class="admin-body">
        <div class="admin-from">
        <form id="blogform" class="post_blog">
            <ul>
                <li>标题:<input class="title" type="text" name="rich_title" /></li>
                <li>SEO标题:<input class="title" type="text" name="small_title" /></li>
                <li>内容:<textarea class="content" name="content" id="content"></textarea></li>
                <li><a class="btn" onclick="post('/admin/preview',{content:$('#content').val()});">Preview</a></li>
                <li>日期:<input class="title" type="text" name="content_date" id="datepicker"/></li>
                <li><a class="btn" id="post_blog" >提交</a>

                </li>

            </ul>
            </form>
        </div>
    </div>


</div>
<#include "/inc/foot.ftl"/>
<script>

$(function() {
    var submit_blog = function() {
        $.ajax({
            type: "post",
            url: "/admin/postblog",
            async: false,
            data: JSON.stringify({
                title: $('input[name="rich_title"]').val(),
                intro: $('textarea[name="content"]').val(),
                date: $('input[name="content_date"]').val(),
                smallTitle: $('input[name="small_title"]').val()
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: function(data) {
                console.log(data);
                if(!data.state) {
                    alert(data.data);
                }
                else {
                    window.location.href='/admin/list';
                }
            }
        });
    }
    $("#post_blog").bind("click",submit_blog);
    $("#datepicker").datepicker({dateFormat: "yy-mm-dd" } );
    $("#datepicker").focus(function(){document.activeElement.blur();});
});

function post(URL, PARAMS) { var temp_form = document.createElement("form");     
    temp_form .action = URL;     
    temp_form .target = "_blank";
    temp_form .method = "post";     
    temp_form .style.display = "none"; for (var x in PARAMS) { var opt = document.createElement("textarea");     
                opt.name = x;     
                opt.value = PARAMS[x];     
                temp_form .appendChild(opt);     
    }     
    document.body.appendChild(temp_form);     
    temp_form .submit();    
}
      
</script>


</body>
</html>