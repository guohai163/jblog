$(function() {
    var submit_blog = function() {
        $.ajax({
            type: "post",
            url: "/admin/postblog",
            async: false,
            data: JSON.stringify({
                postCode: $('input[name="post_code"]').val(),
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
    jQuery(".datepicker").datetimepicker({format:'Y-m-d H:i'});

    $(".li-btn-del").click(function() {
        var postCode = $(this).attr("id");
        console.log(postCode)
        $.confirm({
            title: "确认",
            content: "确认是否删除编号为"+$(this).attr("id")+"的文章",
            type: "green",
            buttons: {
                ok: {
                    text: "确认",
                    btnClass: "btn-primary",
                    action: function() {
                        $.ajax({
                            type: "post",
                            url: "/admin/delblog",
                            async: false,
                            data: JSON.stringify({
                                postCode: postCode
                            }),
                            contentType: "application/json; charset=utf-8",
                            dataType: "json",
                            cache: false,
                            success: function(data) {
                                console.log(data);
                            }
                        });
                    }
                },
                cancel: {
                    text: "取消"
                }
            }
        });


    });
});