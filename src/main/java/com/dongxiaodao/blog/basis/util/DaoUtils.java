package com.dongxiaodao.blog.basis.util;

import com.dongxiaodao.blog.basis.bean.SysUsers;
import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dongxiaodao
 * @date 2019/4/4 - 21:33
 */
public class DaoUtils {

    /**
     * markdown转换为html(因为在发表文章的时候如果使用Markdown文档格式写入，要读出显示在html上面需要用Java格式化Markdown文档的工具库commonmark-java 来吧md转化为html格式)
     *
     * @param old_content
     * @return
     */
    public static String mdToHtml(String old_content) {
        if (StringUtils.isBlank(old_content)) {
            return "";
        }

        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(old_content);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String new_content = renderer.render(document);
//      content = Commons.emoji(content);

/*        // 支持网易云音乐输出
        if (TaleConst.BCONF.getBoolean("app.support_163_music", true) && content.contains("[mp3:")) {
            content = content.replaceAll("\\[mp3:(\\d+)\\]", "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=350 height=106 src=\"//music.163.com/outchain/player?type=2&id=$1&auto=0&height=88\"></iframe>");
        }
        // 支持gist代码输出
        if (TaleConst.BCONF.getBoolean("app.support_gist", true) && content.contains("https://gist.github.com/")) {
            content = content.replaceAll("&lt;script src=\"https://gist.github.com/(\\w+)/(\\w+)\\.js\">&lt;/script>", "<script src=\"https://gist.github.com/$1/$2\\.js\"></script>");
        }*/
        return new_content;
    }

    private static final Pattern SLUG_REGEX = Pattern.compile("^[A-Za-z0-9_-]{5,100}$",Pattern.CASE_INSENSITIVE);

    public static boolean isPath(String slug) {
        if (StringUtils.isNotBlank(slug)){
            if (slug.contains("/") || slug.contains(" ") || slug.contains("."))
                return false;
        }
        Matcher matcher = SLUG_REGEX.matcher(slug);
        return matcher.find();
    }

    public static String htmlToText(String html) {
        if(StringUtils.isNotBlank(html)){
            return html.replaceAll("(?s)(<[^>]*>)*", "");
        }
        //防止返回后，出现空指针异常
        return "无文本内容，点击进入获取其他内容";
    }

    public static String cleanXSS(String value) {
        value = value.replaceAll("<","&lt").replaceAll(">","&gt");
//        value = value.replaceAll("\\(", "&g#40;").replaceAll("\\)", "g#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)","");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

//    获取访问者的ip地址
    public static String getIp(HttpServletRequest request) {
        //代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
//如果没有代理，则获取真实ip
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
//获取登录的user

    public static SysUsers getLoginUser() {
        HttpSession session = getRequest().getSession();
        if (null == session) {
            return null;
        }
        SysUsers user = (SysUsers) session.getAttribute(Type_C.LOGIN_SESSION_KEY);
        return user;
    }

    private static HttpServletRequest getRequest() {
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch(Exception e){
            return null;
        }
    }

    public static void sendMail(String receiveMailAccount, String title, String user, String url) {

    }

    public static String getDevice(String requestHead) {
        Pattern pattern = Pattern.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
        Matcher matcher = pattern.matcher(requestHead);
        String model = null;
        if (matcher.find()){
            model = matcher.group(1).trim();
        }
        return model;
    }
}
