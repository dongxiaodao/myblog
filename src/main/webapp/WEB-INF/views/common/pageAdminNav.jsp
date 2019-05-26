<%--
  Create by ghost 2018/4/5 12:02
  管理者模式中文章的分页条
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="clearfix"></div>
    <ul class="pagination m-b-5 pull-right">
        <c:if test="${articles.pageNum>1}">
            <li>
                <a href="?page=${articles.prevPage}" aria-label="Previous"><i class="fa fa-arrow-left"></i>&nbsp;上一页 </a>
            </li>
        </c:if>
        <c:forEach items="${articles.navigatepageNums}" var="nav">
            <li <c:if test="${nav == articles.pageNum}"> class="active" </c:if>><a href="?page=${nav}">${nav}</a></li>
        </c:forEach>
        <c:if test="${articles.pageNum<articles.total}">
            <li>
                <a href="?page=${articles.nextPage}" class="next"> <i class="fa fa-arrow-right fa"></i>&nbsp;下一页 </a>
            </li>
        </c:if>
    </ul>


