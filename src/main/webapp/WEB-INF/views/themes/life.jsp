<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/17
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="生活随笔"/>
</jsp:include>
<link rel="stylesheet" href="${ctx}static/themes/css/app.css"/>
<link rel="stylesheet" href="${ctx}static/themes/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${ctx}static/themes/css/mysize.css"/>
<div class="x-pc-width clearfix x-margin-top-20 x-margin-bottom-20" style="padding-top: 50px">
    <%--左半边和右半边没有什么联系--%>
    <%--左半边为tech分类的文章--%>
    <div class="x-pull-left col-md-9 col-sm-12 blog-sm-left">
        <c:forEach items="${articles.list}" var="at">
            <div class="x-white-box clearfix">
                <div class="x-layout-30 x-pull-left">
                    <p>
                        <a href="<c:choose><c:when test="${at.slug != ''}">${ctx}article/${at.slug}</c:when>
                                <c:otherwise>${ctx}article/${at.cid}</c:otherwise></c:choose>">
                            <img class="x-image-size-11" src="${dao:showThumbnail(at)}" />
                        </a>
                    </p>
                </div>
                <div class="x-layout-68 x-pull-right">
                    <h3 class="x-box-title ">
                        <a class="x-a-active" href="<c:choose><c:when test="${at.slug != null}">${ctx}article/${at.slug}</c:when>
                                <c:otherwise>${ctx}article/${at.cid}</c:otherwise></c:choose>">${at.title}</a>
                    </h3>
                    <p class="x-box-summary x-item-space-25 clearfix">${dao:showIntro(at,66 )}</p>
                    <div class="x-box-tags">
                        <p >
                            <img class="x-small-avatar x-image-radus x-vertical-middle" src="${ctx}static/admin/images/user.jpg" />
                            <a href="/about">Dao</a>
                        </p>
                        <p >
                            <i class="fa fa-bars" aria-hidden="true"></i>
                            <a href="${ctx}category/${at.categories}">${at.categories}</a>
                        </p>
                        <p >
                            <i class="fa fa-clock-o" aria-hidden="true"></i>
                            <time datetime="<fmt:formatDate value="${post.created}" pattern="yyyy-MM-dd"/>"> <fmt:formatDate value="${at.created}" pattern="yyyy-MM-dd"/> </time>
                        </p>
                        <p >
                            <i class="fa fa-eye" aria-hidden="true"></i>
                                ${at.hits}
                        </p>
                        <p >
                            <i class="fa fa-commenting-o" aria-hidden="true"></i>
                                ${at.commentsNum}
                        </p>
                    </div>
                </div>
            </div>

        </c:forEach>
        <div class="lists-navigator clearfix">
            <jsp:include page="../common/pageRef.jsp"/>
        </div>
    </div>

<%--右半边为文章分类， 标签分类， 推荐文章--%>
    <%--<div class="x-pull-right x-layout-25 blog-sm-right">--%>
    <div class="x-pull-right col-md-3 blog-sm-right">
        <div class="x-white-box">
            <h3 class="x-box-title">
                文章分类 <span class="x-title-border"></span>
            </h3>
            <ul class="x-category">
                <c:forEach items="${categoriesf}" var="category">

                    <li>
                        <a href="${ctx}category/${category.name}"><span class="fa fa-bookmark-o"></span>&nbsp;&nbsp;&nbsp;&nbsp;${category.name}&nbsp;(${category.count})</a>
                    </li>
                </c:forEach>
            </ul>
        </div>


        <div class="x-white-box">
            <h3 class="x-box-title">
                推荐文章 <span class="x-title-border"></span>
            </h3>
<%--图片会被拉长，这里直接去掉图片--%>
            <%----%>
            <%--<div class="x-layout-100 person-relative image-box">--%>
                <%--<div class="yfh-banner-image">--%>
                    <%--<img src="${dao:showThumbnail(realTop)}">--%>
                <%--</div>--%>
                <%--<div class="x-box-border yfh-banner-transfer">--%>
                    <%--<a href="<c:choose><c:when test="${realTop.slug != null}">${ctx}article/${reaTop.slug}</c:when>--%>
                                <%--<c:otherwise>${ctx}article/${realTop.cid}</c:otherwise></c:choose>">--%>
                        <%--<p>${realTop.title}</p> </a>--%>
                <%--</div>--%>
            <%--</div>--%>

            <c:forEach items="${recommendArticle}" var="rea">
                <div class="x-margin-bottom-5 x-margin-top-5 clearfix" style="border-bottom: 2px dotted #CCCCCC;">
                    <div class="x-layout-100">
                        <p class="right-box-title"><a href="<c:choose><c:when test="${rea.slug != null}">${ctx}article/${rea.slug}</c:when>
                                <c:otherwise>${ctx}article/${rea.cid}</c:otherwise></c:choose>">${rea.title}</a></p>
                        <div class="x-layout-100 x-font-small x-font-grey">
                            <i class="fa fa-clock-o" aria-hidden="true"></i>
                            <time datetime="<fmt:formatDate value="${rea.created}" pattern="yyyy-MM-dd HH:mm:ss"/>"> <fmt:formatDate value="${rea.created}" pattern="yyyy-MM-dd HH:mm:ss"/> </time>
                        </div>
                    </div>
                </div>
                <%--            <div class="x-margin-bottom-10 x-margin-top-10 clearfix">
                                <div class="x-layout-30 x-pull-left">
                                    <p>
                                        <a href="<c:choose><c:when test="${rea.slug != ''}">${ctx}article/${rea.slug}.html</c:when>
                                                <c:otherwise>${ctx}article/${rea.cid}.html</c:otherwise></c:choose>">
                                            <img class="x-item-space-5" src="${fnc:show_thumb(rea)}" />
                                        </a>
                                    </p>
                                </div>

                                <div class="x-layout-68 x-pull-right">
                                    <p class="right-box-title"><a href="<c:choose><c:when test="${rea.slug != ''}">${ctx}article/${rea.slug}.html</c:when>
                                                <c:otherwise>${ctx}article/${rea.cid}.html</c:otherwise></c:choose>">${rea.title}</a></p>
                                    <div class="x-layout-100 x-font-small x-font-grey">
                                        <i class="fa fa-clock-o" aria-hidden="true"></i>
                                        <time datetime="<fmt:formatDate value="${rea.created}" pattern="yyyy-MM-dd HH:mm:ss"/>"> <fmt:formatDate value="${rea.created}" pattern="yyyy-MM-dd HH:mm:ss"/> </time>
                                    </div>
                                </div>
                            </div>--%>
            </c:forEach>
        </div>


        <%--        <div class="x-white-box">
                    <h3 class="x-box-title">
                        热评文章 <span class="x-title-border"></span>
                    </h3>

                    <div class="x-layout-100 person-relative x-margin-bottom-10 image-box">
                        <div class="yfh-banner-image">
                            <img src="${(content.thumbnail)}" />
                        </div>
                        <div class="x-box-border yfh-banner-transfer">
                            <a href="${(content.url)}">
                                <p>${(content.title)}</p>
                            </a>
                        </div>
                    </div>
                </div>--%>

        <div class="x-white-box">
            <h3 class="x-box-title">
                热门标签 <span class="x-title-border"></span>
            </h3>
            <ul class="x-tag">
                <c:forEach items="${tagsf}" var="tag">
                    <li>
                        <a href="${ctx}tag/${tag.name}">${tag.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

    </div>
</div>

<jsp:include page="footer.jsp"/>