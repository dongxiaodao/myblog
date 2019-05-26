<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/12
  Time: 15:33
  To change this template use File | Settings | File Templates.
  
  评论模块中用到的ajax的function
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    (function () {
        //提交之后调用的方法
        window.TaleComment = {
            //  子函数 subComment
            subComment: function () {
                $.ajax({
                    type:'post',
                    url:'${ctx}comment',
                //    传入的数据
                    data:$("#comment-form").serialize(),
                    dataType:"json",
                    success:function(result){
                    //    请求成功时,先清空coid，保证不是编辑评论时不是在回复某条评论
                        $('#comment-form input[name=coid]').val('');
                        //滚动回comment-container
                        if(result.success){
                            window.location.reload();
                        }else{
                            if(result.msg){
                                alert(result.msg);
                            }
                        }
                    }

                });
                return false;
            },
            //上面函数结束要以逗号，结尾
            //点击评论底下的回复调用的方法
            reply : function (coid) {
                $('#comment-form input[name=coid]').val(coid);
                //滚动回comment-container,并聚焦到textarea
                $("html,body").animate({scrollTop: $('div.comment-container').offset().top}, 500);
                $('#comment-form #textarea').focus();

            }


        };

    })();
    //获取指定cookie值（下列方法可能出现乱码）
    // function getCommentCookie(name) {
    //     var cname = name + "=";
    //     var ca = document.cookie.split(';');
    //     for(var i =0; i< ca.length;i++){
    //         var c = ca[i].trim();
    //         if(c.indexOf(cname)==0){
    //             return c.substring(cname.length,c.length);
    //
    //         }
    //     }
    //     return "";
    // }
    function getCommentCookie(name){
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg))
            //使用decodeURI可以避免乱码问题
            return unescape(decodeURI(arr[2]));
        else
            return null;
    }
    //为评论表单中的名字和邮箱附上cookie存有的值
    function addCommentInputValue() {
        document.getElementById('author').value = getCommentCookie('remember_author');
        document.getElementById('mail').value=getCommentCookie('remember_mail');
    }
    //加载页面时自动加载该函数
    addCommentInputValue();
    
    
    
    
</script>
