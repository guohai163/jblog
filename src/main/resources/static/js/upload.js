var uploader = new plupload.Uploader({
    browse_button: 'browse',
    multi_selection: false,
    url: 'http://oss.aliyuncs.com'
});
uploader.init();
uploader.bind('FilesAdded', function(up, files) {
    var html = '';
      plupload.each(files, function(file) {

    !function(file) {
        previewImage(file, function(imgsrc){
            html = '<li><img src="'+imgsrc+'" /></li>'
         $("#filelist").append(html);
         })
    }
//        html += '<li id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></li>';
      });

});

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

                    console.log("img is jpeg/png");
                    var preloader = new moxie.image.Image();
                    preloader.onload = function () {
                        //preloader.downsize(550, 400);//先压缩一下要预览的图片,宽300，高300
                        var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
                        callback && callback(imgsrc); //callback传入的参数为预览图片的url
                        preloader.destroy();
                        preloader = null;
                    };
                    preloader.load(file.getSource());
                }
            }