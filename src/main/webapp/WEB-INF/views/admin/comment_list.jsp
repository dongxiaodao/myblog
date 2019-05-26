<%--
  Create by ghost 2018/4/9 15:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="评论管理"/>
    <jsp:param name="pageActive" value="comments"/>
    <jsp:param name="has_sub" value="other"/>
</jsp:include>

<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">评论管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th width="35%">评论内容</th>
                <th>评论人</th>
                <th>评论时间</th>
                <th>评论人邮箱</th>
                <th>评论人网址</th>
                <th>评论状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${articles.list}" var="comment">
                <tr cid="${comment.coid}">
                    <td>
                        <a href="${ctx}article/${comment.cid}#comments" target="_blank">${comment.content}</a>
                    </td>
                    <td>${comment.author}</td>
                    <td><fmt:formatDate value="${comment.created}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${comment.mail}</td>
                    <td><a href="${comment.url}" target="_blank">${comment.url}</a></td>
                    <td>
                        <c:choose>
                            <c:when test="${comment.status=='shield'}"><span class="label label-default">未显示</span></c:when>
                            <c:otherwise><span class="label label-success">已显示</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="javascript:void(0)" onclick="reply('${comment.coid}');" class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i class="fa fa-edit"></i> <span>回复</span></a>
                        <c:if test="${comment.status!='shield'}">
                            <a href="javascript:void(0)" onclick="updateStatus('${comment.coid}', 'shield');" class="btn btn-default btn-sm waves-effect waves-light m-b-5"><i
                                    class="fa fa-cog"></i> <span>隐藏</span></a>
                        </c:if>
                        <a href="javascript:void(0)" onclick="delComment(${comment.coid});" class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i class="fa fa-trash-o"></i> <span>删除</span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="../common/pageAdminNav.jsp"/>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script type="text/javascript">

    var tale = new $.tale();
    function reply(coid) {
        swal({
            title: "回复评论",
            text: "请输入你要回复的内容:",
            input: 'text',
            showCancelButton: true,
            confirmButtonText: '回复',
            cancelButtonText: '取消',
            showLoaderOnConfirm: true,
            preConfirm: function (comment) {
                return new Promise(function (resolve, reject) {
                    tale.post({
                        url : '${ctx}admin/comments',
                        data: {coid: coid, content: comment},
                        success: function (result) {
                            if(result && result.success){
                                tale.alertOk('已回复');
                            } else {
                                tale.alertError(result.msg || '回复失败');
                            }
                        }
                    });
                })
            },
            allowOutsideClick: false
        });
    }

    function delComment(coid) {
        tale.alertConfirm({
            title:'确定删除该评论吗?',
            then: function () {
                tale.post({
                    url : '${ctx}admin/comments/delete',
                    data: {coid: coid},
                    success: function (result) {
                        if(result && result.success){
                            tale.alertOkAndReload('评论删除成功');
                        } else {
                            tale.alertError(result.msg || '评论删除失败');
                        }
                    }
                });
            }
        });
    }

    function updateStatus(coid, status) {
        tale.post({
            url : '${ctx}admin/comments/status',
            data: {coid: coid, status: status},
            success: function (result) {
                if(result && result.success){
                    tale.alertOkAndReload('评论状态设置成功');
                } else {
                    tale.alertError(result.msg || '评论设置失败');
                }
            }
        });
    }
</script>

</body>
</html>