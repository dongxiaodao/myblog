<%--
  Create by ghost 2018/4/2 10:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="文章管理"/>
    <jsp:param name="pageActive" value="article"/>
</jsp:include>
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">文章管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-boeder">
            <thead>
            <tr>
                <th width="35%">文章标题</th>
                <th width="15%">发布时间</th>
                <th>浏览量</th>
                <th>所属分类</th>
                <th width="8%">发布状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${articles.list}" var="article" varStatus="status">
                <tr cid="${article.cid}">
                    <td>
                        <a href="${ctx}admin/article/${article.cid}">${article.title}</a>
                    </td>
                    <td>
                        <fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/>
                    </td>
                    <td>${article.hits}</td>
                    <td>${article.categories}</td>
                    <td>
                        <c:if test="${article.status=='publish'}"><span class="label label-success">已发布</span> </c:if>
                        <c:if test="${article.status=='draft'}"><span class="label label-success">草稿</span> </c:if>
                    </td>
                    <td>
                        <a href="${ctx}admin/article/${article.cid}" class="btn btn-primary btn-sm waves-effect waves-light m-b-5">
                            <i class="fa fa-edit"></i><span>编辑</span>
                        </a>
                        <a href="javascript:void(0)" onclick="delArticle(${article.cid})" class="btn btn-warning btn-sm waves-effect waves-light m-b-5">
                            <i class="fa fa-trash-o"></i><span>删除</span>
                        </a>
                        <c:choose>
                            <c:when test="${article.status=='publish'}">
                                <a href="${ctx}article/${article.cid}.html" class="btn btn-warning btn-sm waves-effect waves-light m-b-5">
                                    <i class="fa fa-rocket"></i><span>预览</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${ctx}admin/article/${article.cid}.html" class="btn btn-warning btn-sm waves-effect waves-light m-b-5">
                                    <i class="fa fa-rocket"></i><span>预览</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
    <jsp:include page="../common/pageAdminNav.jsp"/>
</div>

<jsp:include page="footer.jsp"/>
<script type="text/javascript">
    var tale = new $.tale();
    function delArticle(cid) {
        tale.alertConfirm({
            title:'确定要删除文章吗？',
            then:function(){
                tale.post({
                    url:'${ctx}admin/article/delete',
                    data:{cid:cid},//是data，不是date
                    success:function (result) {
                        if (result && result.success){
                            tale.alertOkAndReload('文章删除成功！');
                        }else{
                            tale.alertError(result.msg || '文章删除失败');
                        }
                    }
                })
            }
        })
    }
</script>
</body>
</html>