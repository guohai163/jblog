var expire = 0;
var now;
var signatureData;


function get_signature() {
    // 可以判断当前expire是否超过了当前时间， 如果超过了当前时间， 就重新取一下，3s 作为缓冲。
    now = timestamp = Date.parse(new Date()) / 1000;
    if (expire < now + 3) {
        $.ajaxPost("/admin/alioss", null, function (data) {
            signatureData = data;
            ret = true;
        }, false);
        expire = signatureData.expire;
        return true;
    }
    return false;
};

// 获得文件扩展名 
function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function set_upload_param(up, filename, ret) {

    if (ret == false) {
        //当传参为false时向服务器请求签名
        ret = get_signature();
    }
    console.log(filename);
    g_object_name = signatureData.dir;
    if (filename != '') {
        g_object_name = signatureData.dir + signatureData.user + get_suffix(filename);
        console.log(g_object_name);
    }
    new_multipart_params = {
        'key': g_object_name,
        'policy': signatureData.policy,
        'OSSAccessKeyId': signatureData.accessid,
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'callback': signatureData.callback,
        'signature': signatureData.signature
    };

    up.setOption({
        'url': signatureData.host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

var uploader = new plupload.Uploader({
    runtimes: 'html5,html4',
    browse_button: 'browse',
    multi_selection: false,
    filters: {
        mime_types: [ //只允许上传图片和zip文件
            { title: "Image files", extensions: "jpg,jpeg,gif,png" }
        ],
        max_file_size: '1mb', //最大只能上传10mb的文件
        prevent_duplicates: true //不允许选取重复文件
    },
    url: 'http://oss.aliyuncs.com',
    init: {
        PostInit: function () {
            $('#filelist').html('');
            $('#start-upload').bind("click", function () { set_upload_param(uploader, '', false); return false; });
        },

        FilesAdded: function (up, files) {
            plupload.each(files, function (file) {
                previewImage(file, (function (imgsrc) {
                    $("#filelist").html('<li><img src="' + imgsrc + '" /></li>');
                }));
            });
        },
        BeforeUpload: function (up, file) {
            set_upload_param(up, file.name, true);
        },
        FileUploaded: function (up, file, info) {
            if (info.status == 200) {
                alert('upload to oss success, object name:' + file.name + ' 回调服务器返回的内容是:' + info.response);
            }
            else if (info.status == 203) {
                alert('上传到OSS成功，但是oss访问用户设置的上传回调服务器失败，失败原因是:' + info.response);
            }
            else {
                alert(info.response);
            }
        }
    }
});
uploader.init();

function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数

    if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
        var fr = new moxie.image.FileReader();
        fr.onload = function () {
            callback(fr.result);
            fr.destroy();
            fr = null;
        }
        fr.readAsDataURL(file.getSource());
    } else {
        var preloader = new moxie.image.Image();
        preloader.onload = function () {
            preloader.downsize(300, 300);//先压缩一下要预览的图片,宽300，高300
            var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
            callback && callback(imgsrc); //callback传入的参数为预览图片的url
            preloader.destroy();
            preloader = null;
        };
        preloader.load(file.getSource());
    }
}