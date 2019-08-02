$(function() {
    var submit_blog = function() {
        $.ajax({
            type: "post",
            url: "/admin/postblog",
            async: false,
            data: JSON.stringify({
                postTitle: $('input[name="rich_title"]').val(),
                postContent: $('textarea[name="content"]').val(),
                postDate: $('input[name="content_date"]').val(),
                postSmallTitle: $('input[name="small_title"]').val().replace(/ /g,'-')
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: function(data) {
                if(!data.state) {
                    alert(data.data);
                }
                else {
                    window.location.href='/admin/list';
                }
            }
        });
    }
    var btn_wrtie = function() {
        $(".write-contet").show();
        $(".preview-content").hide();
    }
    var btn_preview = function() {
        $.ajax({
            type: "post",
            url: "/admin/preview",
            async: false,
            data: JSON.stringify({
                postContent: $('textarea[name="content"]').val()
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: function(data) {
                console.log(data);
                if(data.state) {
                    $(".preview-content").html(data.data);
                    $(".write-contet").hide();
                    $(".preview-content").show();
                }
            }
        });
    }
    $(".preview-content").hide();
    $(".js-write-tab").bind("click",btn_wrtie);
    $(".js-preview-tab").bind("click",btn_preview);
    $("#post_blog").bind("click",submit_blog);
    $(".datepicker").datepicker({dateFormat: "yy-mm-dd" } );
    $(".datepicker").focus(function(){document.activeElement.blur();});
});