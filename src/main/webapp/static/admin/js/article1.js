var mditor, ueditor;
var tale = new $.tale();
var attach_url = $('#attach_url').val();
var refreshIntervalId = setInterval("autoSave()", 60 * 1000);
$(document).ready(function () {
    mditor = window.mditor = Mditor.fromTextarea(document.getElementById('md-editor'));
    ueditor = document.getElementById('ue_container');

    var fmt_type = $('#fmtType').val();
    // 富文本编辑器
    if (fmt_type != 'markdown') {
        var this_ = $('#switch-btn');
        mditor.value = '';
        $('#md-container').hide();
        $('#html-container').show();
        $('#fmtType').val('html');
        this_.text('切换为Markdown编辑器');
        this_.attr('type', 'texteditor');
    } else {
        var this_ = $('#switch-btn');
        $('#html-container').hide();
        $('#md-container').show();
        ueditor.value = '';//------------------------------
        $('#fmtType').val('markdown');
        this_.attr('type', 'markdown');
        this_.text('切换为富文本编辑器');
        //htmlEditor.summernote("code", "");
    }

    /*
     * 切换编辑器
     * */
    $('#switch-btn').click(function () {
        var type = $('#fmtType').val();
        var this_ = $(this);
        if (type == 'markdown') {
            // 切换为富文本编辑器
            mditor.value = '';
            $('#md-container').hide();
            $('#html-container').show();
            this_.text('切换为Markdown编辑器');
            $('#fmtType').val('html');
        } else {
            // 切换为markdown编辑器
            $('#html-container').hide();
            $('#md-container').show();
            $('#fmtType').val('markdown');
            this_.text('切换为富文本编辑器');
            //htmlEditor.summernote("code", "");
        }
    });

    $('#tags').tagsInput({

        width: '100%',
        height: '35px',
        defaultText: '请输入文章标签'
    });

    $('.toggle').toggles({
        on: true,
        text: {
            on: '开启',
            off: '关闭'
        }
    });

    $('.select2').select2({
        width: '100%'
    });

    $('div.allow-false').toggles({
        off:true,
        text:{
            on:'开启',
            off:'关闭'
        }

    });
    //
    // if($('#thumb-toggle').attr('thumb_url') != ''){
    //     $('#thumb-toggle').toggles({
    //         on: true,
    //         text: {
    //             on: '开启',
    //             off: '关闭'
    //         }
    //     });
    //     $('#thumb-toggle').attr('on', 'true');
    //     $('#dropzone').css('background-image', 'url('+ $('#thumb-toggle').attr('thumb_url') +')');
    //     $('#dropzone').css('background-size', 'cover');
    //     $('#dropzone-container').show();
    // } else {
    //     $('#thumb-toggle').toggles({
    //         off: true,
    //         text: {
    //             on: '开启',
    //             off: '关闭'
    //         }
    //     });
    //     $('#thumb-toggle').attr('on', 'false');
    //     $('#dropzone-container').hide();
    // }

    if ($('#thumb-toggle').attr('thumb_url') != ''){
        $('#thumb-toggle').togles({
            on:true,
            text:{
                on:'开启',
                off:'关闭'
            }
        });
        $('#thumb-toggle').attr('on','true');
        $('#dropzone').css('background-image','url('+ $('#thumb-toggle').attr('thumb_url')+')');
        $('#dropzone').css('background-size','cover');
        $('#dropzone-container').show();
    }else{
        $('#thumb-toggle').toggles({
            off:true,
            text:{
                on:'开启',
                off:'关闭'
            }
        });
        $('#thumb-toggle').attr('on','false');
        $('#dropzone-container').hide();
    }

    Dropzone.autoDiscover = false;

    var thumbdropzone = $('.dropzone');
    $('#dropzone').dropzone({
        url:'../attach/upload',
        filesizeBase:1024,//定义字节算法
        maxFilesize:'10',
        fallback:function () {
            tale.alertError('暂不支持您的浏览器上传');
        },
        acceptedFiles:'image/*',
        dictFileTooBig:'您的文件超过10MB',
        dictInvalidInputType:'不支持您上传的类型',
        init:function () {
            this.on('success',function (files,result) {
                console.log('upload success..');
                console.log('result => '+result);
                if (result && result.success){
                    var url = attach_url+result.fkey;
                    console.log('url => '+url);
                    thumbdropzone.css('background-image', 'url('+ url +')');
                    thumbdropzone.css('background-size', 'cover');
                    $('.dz-image').hide();
                    $('#thumb_img').val(url);
                    $('#thumbImg').val(result.fkey);
                }
            })
            this.on('error',function(a,errorMessage,result){
                if (!result.success && result.msg){
                    tale.alertError(result.msg || '缩略图上传失败')
                }
            });
        }
    });
});

/**
 * 自动保存文章
 */
function autoSave(){
    var content = $('#fmtType').val() == 'markdown' ? mditor.value:ueditor.value;
    var title = $('#articleForm input[title]').val();
    if (title!='' && content!='') {
        $('#content-editor').val(content);
        $('#articleForm #status').val(status);
        $('#articleForm #categories').val($('#multiple-sel').val());
        var params = $('#articleForm').serialize();
        var url = $('#articleForm #cid').val() != '' ? 'modify' : 'publish';
        tale.post({
            url: url,
            date: params,
            success: function (result) {
                if (result && result.success) {
                    $('#articleForm #cid').val(result.payload);
                }else{
                    tale.alertError(result.msg || '保存文章失败')
                }
            }
        })
    }

}

// /**
//  * 保存
//  */
// function subArticle(status) {
//     var content = $('#fmtType').val() == 'markdown' ? mditor.value:ueditor.value;
//     var title = $('#articleForm input[title]').val();
//     if (title==''){
//         tale.alertWarn('请输入文章标题');
//         return;
//     }
//     if (content==null){
//         tale.alertWarn('请输入文章内容');
//         return;
//     }
//
//     clearInterval(refreshIntervalId);
//     $('#content-editor').val(content);
//     $('#articleForm #status').val(status);
//     $('#articleForm #categories').val($('#multiple-sel').val());
//     var params = $('#articleForm').serialize();
//     var url = $('#articleForm #cid').val()!='' ? 'modify': 'publish/article';
//     tale.post({
//         url:url,
//         date:params,
//         success:function (result) {
//             if (result && result.success){
//                 tale.alertOk({
//                     text:'文章成功保存',
//                     then:function () {
//                         setTimeout(function () {
//                             window.location.href = '../article';
//                         },500);
//                     }
//                 });
//
//             }else{
//                 tale.alertError(result.msg || '文章保存失败');
//             }
//         }
//     });
// }

/**
* 保存文章
* @param status
*/
function subArticle(status) {
    var content = $('#fmtType').val() == 'markdown' ? mditor.value : 'ueditor.value';
    var title = $('#articleForm input[name=title]').val();
    if (title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }
    if (content == '') {
        tale.alertWarn('请输入文章内容');
        return;
    }
    clearInterval(refreshIntervalId);
    $('#content-editor').val(content);
    $('#articleForm #status').val(status);
    $('#articleForm #categories').val($('#multiple-sel').val());
    var params = $('#articleForm').serialize();
    var url = $('#articleForm #cid').val() != '' ? 'modify' : 'publish/article';
    tale.post({
        url: url,
        data: params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text: '文章保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '../article';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存文章失败');
            }
        }
    });
}

function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on==1){
        this_.attr('on',0);
        $('#allowComment').val(0);
    }else{
        this_.attr('on',1);
        $('#allowComment').val(1);
    }
}

function allow_ping(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on==1){
        this_.attr('on',0);
        $('#allowPing').val(0);
    }else {
        this_.attr('on',1);
        $('#allowPing').val(1);
    }
}

function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    if (on==1){
        this_.attr('on',0);
        $('#allowFeed').val(0);
    }else{
        this_.attr('on',1);
        $('#allowFeed').val(1);
    }
}

function add_thumbimg(obj) {
    var this_ = $(obj);
    var on = this_.attr('on');
    console.log(on);
    if (on == 'true'){
        this_.attr('on','false');
        $('#dropzone-container').addClass('hide');
        $('#thumbImg').val('');
    }else{
        this_.attr('on','true');
        $('#dropzone-container').removeClass('hide');
        $('#dropzone-container').show();
    }
}




