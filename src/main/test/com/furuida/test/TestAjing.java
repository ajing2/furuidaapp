/**
 * @ClassName TestAjing
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 12:35 PM
 * @Version 1.0
 **/
public class TestAjing {
    public static void main(String[] args) {
        WeChatUtils weChatUtils = new WeChatUtils();
        WeChatAccessToken data = weChatUtils.getAccessToken("aaa");
        System.out.println("+++++++++++++++++++++++");
        System.out.println(data.toString());
        System.out.println("+++++++++++++++++++++++");

    }
}
