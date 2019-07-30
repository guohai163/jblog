<!DOCTYPE html>
<html lang="en">
<head>
    <#include "/inc/head.ftl"/>
    
</head>
<body>
<div class="admin-main">
    <div class="admin-side">
        <ul>
            <li>增加文章</li>
        </ul>
    </div>

    <div class="admin-body">
        <div class="admin-from">
            <ul>
                <li>标题:<input type="text" name="rich_title" ></li>
                <li>内容:<textarea name="content" id="content"></textarea></li>
                <li><a class="btn" onclick="post('/admin/preview',{content:$('#content').val()});">Preview</a></li>
            </ul>
            
        </div>
    </div>


</div>
<script>

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
<#include "/inc/foot.ftl"/>

</body>
</html>