$(function () {
    "use strict";
    var li_add_flag = false;
    var submit_blog = function () {
        $.ajax({
            type: "post",
            url: "/admin/postblog",
            async: false,
            data: JSON.stringify({
                postCode: $('input[name="post_code"]').val(),
                postTitle: $('input[name="rich_title"]').val(),
                postContent: $('textarea[name="content"]').val(),
                postDate: $('input[name="content_date"]').val(),
                postSmallTitle: $('input[name="small_title"]').val().replace(/ /g, '-')
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: function (data) {
                if (!data.status) {
                    alert(data.data);
                }
                else {
                    window.location.href = '/admin/list';
                }
            }
        });
    }
    var btn_wrtie = function () {
        $(".write-contet").show();
        $(".preview-content").hide();
    }
    var btn_preview = function () {
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
            success: function (data) {
                console.log(data);
                if (data.status) {
                    $(".preview-content").html(data.data);
                    $(".write-contet").hide();
                    $(".preview-content").show();
                }
            }
        });
    }
    var btn_delete_blog = function () {
        var postCode = $(this).attr("id");
        $.confirm({
            title: "确认",
            content: "确认是否删除编号为" + $(this).attr("id") + "的文章",
            type: "green",
            buttons: {
                ok: {
                    text: "确认",
                    btnClass: "btn-primary",
                    action: function () {
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
                            success: function (data) {
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
    var btn_update_password = function () {
        console.log("in btm update")
    //TODO:重构POST方法，进行封装；检查两次输入的密码是否一致。
        var newpass = $('input[name="new_pass"]').val();
        if (newpass != $('input[name="config_new_pass"]').val()) {
            $.alert({
                content: "请确保两次输入的密码一致"
            });
            return false;
        }

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
            success: function (data) {
                console.log(data)
                $.alert({
                    title: data.status,
                    content: data.data
                });
            }

        });
    }

    var class_type_change = function () {
        var classCode = $(this).find("option:selected").val();
        var postCode = $(this).find("option:selected").attr("post_code");
        $.ajax({
            type: "post",
            url: "/admin/blog/class",
            async: false,
            data: {
                classCode: classCode,
                postCode: postCode
            },
            cache: false,
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
                alert("更新异常！")
            }
        });
    }

    // 分类删除
    var btn_class_delete = function () {
        var classCode = $(this).attr("class_code");
        if (typeof(classCode) === "undefined") {
            $('#class_0').parent().remove();
            li_add_flag = false;
            return;
        }
        $.confirm({
            title: "确认",
            content: "确认是否删除该分类",
            type: "green",
            buttons: {
                ok: {
                    text: "确认",
                    btnClass: "btn-primary",
                    action: function () {
                        $.ajax({
                            type: "post",
                            url: "/admin/blog/class/del",
                            async: false,
                            data: {
                                classCode: classCode
                            },
                            cache: false,
                            success: function (data) {
                                console.log(data);
                                $('#input_class_' + classCode).parent().remove();
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

    // 分类编辑
    var btn_class_edit = function () {
        var editFlag = $(this).text();
        var classCode = $(this).attr("class_code");
        var that = this;
        if (typeof(classCode) === "undefined") {
            //分类添加
            var className = $('#class_0').val();
            $.ajax({
                type: "post",
                url: "/admin/blog/class/add",
                async: false,
                data: {
                    className: className
                },
                cache: false,
                success: function (data) {
                    console.log(data);
                    if(data.status===false){
                        $.alert(data.data);
                        return;
                    }
                    $('#class_0').attr('id','input_class_' + data.data);
                    $('#input_class_' + data.data).nextAll("span").children("a").attr("class_code",data.data);
                    li_add_flag = false;
                    $(that).text('edit');
                    $('#input_class_' + data.data).attr("readonly", "readonly");
                    $('#input_class_' + data.data).attr("disabled", "disabled");
                }
            });
            return;
        }

        if (editFlag === 'edit') {
            $(this).text('save');
            $('#input_class_' + classCode).removeAttr("readonly");
            $('#input_class_' + classCode).removeAttr("disabled");
        } else if (editFlag === 'save') {
            var className = $('#input_class_' + classCode).val();
            $.confirm({
                title: "确认",
                content: "确认是否更新该分类",
                type: "green",
                buttons: {
                    ok: {
                        text: "确认",
                        btnClass: "btn-primary",
                        action: function () {
                            $.ajax({
                                type: "post",
                                url: "/admin/blog/class/edit",
                                async: false,
                                data: {
                                    classCode: classCode,
                                    className: className
                                },
                                cache: false,
                                success: function (data) {
                                    if(data.status===true) {
                                        console.log(data);
                                        $(that).text('edit');
                                        $('#input_class_' + classCode).attr("readonly", "readonly");
                                        $('#input_class_' + classCode).attr("disabled", "disabled");
                                        return;
                                    }

                                    $.alert(data.data)
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
    }

    var btn_class_add_li = function () {
        if (li_add_flag === true) {
            $.alert('请完成当前分类编辑');
            return;
        }
        li_add_flag = true;
        $('.li-class-show').append('<li class="admin-li">\n' +
            '                        <input type="text" id="class_0"\n' +
            '                               style="width: 160px;height: 30px;border-radius:8px"/>\n' +
            '                        <span class="float-right">\n' +
            '                            <a class="li-btn li-class-btn-del">del</a>\n' +
            '                            <a class="li-btn li-class-btn-edit">save</a>\n' +
            '                        </span>\n' +
            '                    </li>');
        $('.li-class-btn-del').bind("click", btn_class_delete);
        $('.li-class-btn-edit').bind("click", btn_class_edit);
    }

    //定义通过方法，POST提交数据
    $.ajaxPost = function(url, data, callback) {
        $.ajax({
            type: "post",
            url: url,
            async: false,
            data: data,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false,
            success: callback
        });
    }
    //后台登录按钮
    var btn_login = function() {

        var username = $('input[name="username"]').val();
        if(username=="") {
            $.alert({
                title: "错误",
                content: "用户名不可为空"
            });
            return false;
        }
        var password = $('input[name="pass"]').val();
        if(""==password) {
            $.alert({
                content: "密码为空"
            });
            return false;
        }
        $.ajaxPost("/admin/login", JSON.stringify({
            userName: username,
            userPass: $('input[name="pass"]').val()
        }),function(data){
            console.log(data);
            if(data.status) {
                window.location.href='/admin/list';
            }else{
                $.alert({
                    title: data.status,
                    content: data.data
                });
            }
        });
    }

    $("#post-login").bind("click",btn_login);
    $("#update_pass").bind("click",btn_update_password);
    $(".preview-content").hide();
    $(".js-write-tab").bind("click",btn_wrtie);
    $(".js-preview-tab").bind("click",btn_preview);
    $("#post_blog").bind("click",submit_blog);
    $(".li-btn-del").bind("click", btn_delete_blog);
    jQuery(".datepicker").datetimepicker({format:'Y-m-d H:i'});

    $(".classify-type").bind("change", class_type_change);
    $('.li-class-btn-del').bind("click", btn_class_delete);
    $('.li-class-btn-edit').bind("click", btn_class_edit);
    $('.li-class-btn-add-li').bind("click", btn_class_add_li);
});