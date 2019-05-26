<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/3/31
  Time: 8:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>

<%--包含头部文件--%>
<jsp:include page="header.jsp">
    <jsp:param name="pageTitle" value="东小刀的个人博客"/>
</jsp:include>
<%--&lt;%&ndash;clearfix:清除浮动&ndash;%&gt;--%>
<div class="main-content index-page clearfix ">
    <%--说明型DIV，若无引用可去掉--%>
    <div class="post-lists">
        <%--发表的文章list--%>
        <div class="post-lists-body">
            <%--status名的对象作为varStatus的绑定值。该绑定值也就是status封装了当前遍历的状态，比如，可以从该对象上查看是遍历到了第几个元素：${status.count}，可实现例如隔项打印（斑马线效果）--%>
            <c:forEach items="${article_contents.list}" var="article" varStatus="status">
                <div class="post-list-item">
                    <div class="post-list-item-container">
                            <%--显示缩略图--%>
                        <div class="item-thumb bg-deepgrey" style="background-image:url(${dao:showThumbnail(article)})"></div>
                            <%--点击转到文章内部--%>
                    <%--slug为发表文章时的自定义访问路径，可能是为了避免暴露文章编号--%>
                        <a href="<c:choose>
                                           <c:when test="${article.slug != null}">${ctx}article/${article.slug}</c:when>
                                           <c:otherwise>${ctx}article/${article.cid}</c:otherwise>
                                </c:choose>">

                        <%--移到文章显示块上，会显示文章的摘要(内容，字数等)--%>
                            <div class="item-desc">
                                <p>${dao:showIntro(article, 70)}</p>
                            </div>
                        </a>
                        <%--slant:倾斜 以下可能是为 该文章显示模块 增加一些 画面表现--%>
                        <div class="item-slant reverse-slant bg-deepgrey"></div>
                        <div class="item-slant"></div>

                        <%--文章显示块右下角的标签图标和分类 模块--%>
                        <div class="item-label">
                            <%--文章的标题名--%>
                            <div class="item-title">
                                <a href="<c:choose>
                                           <c:when test="${article.slug != null}">${ctx}article/${article.slug}</c:when>
                                           <c:otherwise>${ctx}article/${article.cid}</c:otherwise>
                                </c:choose>">

                                ${article.title}</a>
                            </div>
                                <%--文章显示块右下角的标签图标和分类category 模块--%>
                            <div class="item-meta clearfix">
                                    <%--标签图标--%>
                                <div class="item-meta-ico ${dao:showIcon(article.cid)}"
                                     <%--右下角图标的的图片--%>
                                     style="background: url(${ctx}static/themes/img/bg-ico.png) no-repeat;background-size: 40px auto;"></div>
                                    <%--分类 如tech--%>
                                <div class="item-meta-cat">
                                    <a href="${ctx}category/${article.categories}">${article.categories}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
        <%--包含一下页脚文件--%>
    <div class="lists-navigator clearfix">
        <jsp:include page="../common/pageRef.jsp"/>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<%--</body> 和</html> 留给footer.jsp--%>



