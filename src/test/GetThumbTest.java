import com.dongxiaodao.blog.basis.funcs.Contents;

/**
 * @author dongxiaodao
 * @date 2019/4/5 - 10:45
 */
public class GetThumbTest{
    public static void main(String[] args) {
        String str = "<p><span style=\"border: 1px solid rgb(0, 0, 0);\"> 阿萨德</span><br/></p><p><span style=\"border: 1px solid rgb(0, 0, 0);\"></span></p><p><span style=\"border: 1px solid rgb(0, 0, 0);\">\n" +
                "\n" +
                "<img src=\"http://img.baidu.com/hi/jx2/j_0003.gif\"/>\n" +
                "\n" +
                "</span></p><p><span style=\"border: 1px solid rgb(0, 0, 0);\">\n" +
                "\n" +
                "<img src=\"/ueditor/jsp/upload/image/2019/04/1554376305809091189.jpg\" title=\"1554376305809091189.jpg\"/>\n" +
                "\n" +
                "<img width=\"530\" height=\"340\" src=\"http://api.map.baidu.com/staticimage?center=116.404,39.915&zoom=10&width=530&height=340&markers=116.404,39.915\"/>\n" +
                "</span></p><pre class=\"brush:java;toolbar:false\">println(&quot;helloWorld&quot;)</pre><p><span style=\"border: 1px solid rgb(0, 0, 0);\"></span><br/></p>";

        String str1 = Contents.get_thumb(str);
//        测试成功，可以使用该方法来截取文章中第一个照片
    }


}
