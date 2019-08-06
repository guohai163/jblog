$(function() {
    "use strict";
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
                if(!data.status) {
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
                if(data.status) {
                    $(".preview-content").html(data.data);
                    $(".write-contet").hide();
                    $(".preview-content").show();
                }
            }
        });
    }
    var btn_delete_blog = function() {
        var postCode = $(this).attr("id");
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
    }
    var btn_update_password = function() {
        console.log("in btm update")
    //TODO:重构POST方法，进行封装；检查两次输入的密码是否一致。
        $.ajax({
            type: "post",
            url: "/admin/updatepass",
            async: false,
            data: JSON.stringify({
                userPass: $('input[name="new_pass"]').val()
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: function(data) {
                console.log(data)
                $.alert({
                    title: data.status,
                    content: data.data,
                });
            }

        });
    }
    $("#update_pass").bind("click",btn_update_password);
    $(".preview-content").hide();
    $(".js-write-tab").bind("click",btn_wrtie);
    $(".js-preview-tab").bind("click",btn_preview);
    $("#post_blog").bind("click",submit_blog);
    $(".li-btn-del").bind("click", btn_delete_blog);
    jQuery(".datepicker").datetimepicker({format:'Y-m-d H:i'});

    $(".li-btn-del").click(function() {



    });
});