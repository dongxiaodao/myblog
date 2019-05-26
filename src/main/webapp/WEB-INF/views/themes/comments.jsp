<%--
  Created by IntelliJ IDEA.
  User: dongxiaodao
  Date: 2019/4/9
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="dao" uri="http://www.daoblog.cn/jsp/mytag/core" %>

<c:if test="${article != null}">
    <div id="${article.cid}" class="comment-container">
        <div id="comments" class="clearfix">
            <%--先判断是否允许评论--%>
            <c:choose>
                <c:when test="${article.allowComment == 1}">
                    <%--有用户登录的情况下多加以下内容--%>
                    <span class="response"><c:if test="${login_user != null}"> Hi，<a href="${login_user.homeUrl}" data-no-instant>${login_user.username}</a>
                        如果你想 <a href="${ctx}logout" title="注销" data-no-instant>注销</a> ? </c:if>
                    </span>
                    <%--评论框--%>
                    <form method="post" id="comment-form" class="comment-form" role="form"
                            <%--点击提交后的后台ajax--%>
                          onsubmit="return TaleComment.subComment();">

                        <input type="hidden" name="coid" id="coid" value=""/>
                        <input type="hidden" name="cid" id="cid" value="${article.cid}"/>

                        <%--_csrf_token: 什么意思？--%>
                        <%--Cross Site Request Forgery, 跨站域请求伪造--%>
                        <%--猜测是post请求防御scrf 而设立的token相关的东西，未完成。注释掉--%>

                        <%--<input type="hidden" name="_csrf_token" value="${_csrf_token}"/>--%>

                        <input type="text" name="author" maxlength="12" id="author"
                               class="form-control input-control clearfix"
                               <%--保留登录--%>
                               placeholder="怎么称呼？(*)" value="${login_user.username}" required/>
                        <input type="email" name="mail" id="mail" class="form-control input-control clearfix"
                               placeholder="留个邮箱？回复通过邮件提醒"
                               value="${login_user.email}"/>
                        <textarea name="content" id="textarea" class="form-control" placeholder="说点什么吧..." required
                                  minlength="5" maxlength="2000"></textarea>
                        <button type="submit" class="submit" id="misubmit">提交</button>
                    </form>
                </c:when>
                <%--若文章不允许评论--%>
                <c:otherwise>
                    <span class="response">该文章禁止评论</span>
                </c:otherwise>
            </c:choose>
                <c:if test="${article.allowComment==1}">
                    <%--有评论--%>
                    <c:if test="${comments != null}">
                        <%----%>
                        <ol class="comment-list">
                            <c:forEach items="${comments.list}" var="comment">
                                <%--最早的评论，（非子评论）--%>
                                <c:if test="${comment.parent==0}">
                                    <li id="li-comment-${comment.coid}" class="comment-body comment-parent comment-odd">
                                            <%-- 这里的id ：对应footer.jsp 的人们评论 <a标签后缀 #comment-${comment.coid}> 方便定位 --%>
                                        <div id="comment-${comment.coid}">

                                            <div class="comment-view" onclick="">
                                                <%--评论头部信息--%>
                                                <div class="comment-header">
                                                    <%--头像图标--%>
                                                        <img class="avatar"
                                                             src="<c:choose>
                                                                    <c:when test="${comment.author == '东小刀'}">
                                                                        ${ctx}static/themes/img/avatar/2.png
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        ${ctx}static/themes/img/avatar/10.png
                                                                    </c:otherwise>
                                                                  </c:choose>"
                                                             title="${comment.author}" width="80" height="80">
                                                        <%--评论者名字--%>
                                                    <span class="comment-author">
                                                        ${comment.author}
                                                <%--<a href="${comment.url}" target="_blank" rel="external nofollow">${comment.author}</a>--%>
                                                    </span>
                                                </div>
                                                <%--评论内容--%>
                                                <div class="comment-content">
                                                    <span class="comment-author-at"></span>
                                                    <p>${comment.content}</p>
                                                </div>
                                                <%--评论时间--%>
                                                <div class="comment-meta">
                                                    <time class="comment-time"><fmt:formatDate value="${comment.created}"
                                                                                               pattern="yyyy-MM-dd HH:mm:ss"/>
                                                    </time>
                                                    <%--评论中的回复模块：reply：点击回复后的ajax--%>
                                                    <span class="comment-reply">
                                                    <a rel="nofollow" onclick="TaleComment.reply('${comment.coid}');">回复</a>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                                <%--显示子评论--%>
                                        <c:if test="${comment.level>0}">
                                            <div class="comment-children">
                                                <%-- <ol> :代表有序列表--%>
                                                <ol class="comment-list">
                                                    <c:forEach items="${comment.children}" var="child">

                                                        <li id="li-comment-${child.coid}"
                                                            class="comment-body comment-child comment-level-odd comment-odd">
                                                                <%--加id 方便定位--%>
                                                            <div id="comment-${child.coid}">
                                                                <%--评论模块开始--%>
                                                                <div class="comment-view">
                                                                    <div class="comment-header">
                                                                        <%--若为管理者，头像不一样--%>
                                                                        <img class="avatar"
                                                                             src="<c:choose>
                                                                                    <c:when test="${child.author == '东小刀'}">
                                                                                        ${ctx}static/themes/img/avatar/2.png
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        ${ctx}static/themes/img/avatar/10.png
                                                                                    </c:otherwise>
                                                                                  </c:choose>"
                                                                             title="${child.author}" width="80" height="80">
                                                                        <span class="comment-author comment-by-author">
                                                                            <a href="#comment-${comment.coid}">${child.author}</a>
                                                                        </span>
                                                                    </div>
                                                                    <div class="comment-content">
                                                                        <%--评论者名字--%>
                                                                        <span class="comment-author-at">
                                                                                ${dao:comment_at(child.parent)}
                                                                        </span>
                                                                            <%--评论的内容--%>
                                                                        <p>${child.content}</p>
                                                                    </div>
                                                                    <div class="comment-meta">
                                                                        <time class="comment-time"><fmt:formatDate
                                                                                value="${child.created}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/>
                                                                        </time>
                                                                        <span class="comment-reply">
                                                                            <a rel="nofollow" onclick="TaleComment.reply('${child.coid}');">回复</a>
                                                                        </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                                </ol>

                                            </div>
                                        </c:if>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ol>
                        <%--include 评论页码条--%>
                        <jsp:include page="../common/commentPageRef.jsp"/>
                    </c:if>
                </c:if>

        </div>
    </div>
</c:if>
<jsp:include page="../common/tale_comment.jsp"/>