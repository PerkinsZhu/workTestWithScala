package sign;

import com.alibaba.fastjson.JSONObject;
import util.MD5;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class KefuUtils {
    private final static String SECRET_KEY = "test";
    public static String getSign(SortedMap<String, Object> paramMap) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照ascii排序(升序)
        Set<Map.Entry<String, Object>> paramKeys = paramMap.entrySet();
        Iterator<Map.Entry<String, Object>> it = paramKeys.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            sb.append(key + "=" + value + "&");
        }
        sb.append("secretkey=" + SECRET_KEY);
        String md1 = MD5.crypt(sb.toString());
        String md2 = MD5.crypt(md1);
        return md2;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        SortedMap map = new TreeMap();
//        map.put("thirdId","26");
//        map.put("robotId","5b9c81b81400008400cd0257");
//        map.put("appId","wxf8ef8d6283d60f53");
        JSONObject temp = JSONObject.parseObject("{\"touser\":\"olJQ3t_jAQzZYayoVmwlGbBUWLq4\",\"template_id\":\"nlTN6Vuj7Y_aXEwT1P0ixp2__sHEFSMv3kKl723bEIA\",\"data\":{\"first\":{\"value\":\"您的反馈已经由管理人员处理！\",\"color\":\"#4e5051\"},\"keyword1\":{\"value\":\"2018-09-17 15:38:20\",\"color\":\"#4e5051\"},\"keyword2\":{\"value\":\"这是一条反馈11111\",\"color\":\"#003366\"},\"keyword3\":{\"value\":\"这是一条回复23222222\",\"color\":\"#003366\"},\"remark\":{\"value\":\"感谢您的反馈，希望我们的回复能解决您的问题!\",\"color\":\"#4e5051\"}}}");

//        map.put("templateMessage","{\"touser\":\"olJQ3t_jAQzZYayoVmwlGbBUWLq4\",\"template_id\":\"nlTN6Vuj7Y_aXEwT1P0ixp2__sHEFSMv3kKl723bEIA\",\"data\":{\"first\":{\"value\":\"您的反馈已经由管理人员处理！\",\"color\":\"#4e5051\"},\"keyword1\":{\"value\":\"2018-09-17 15:38:20\",\"color\":\"#4e5051\"},\"keyword2\":{\"value\":\"这是一条反馈11111\",\"color\":\"#003366\"},\"keyword3\":{\"value\":\"这是一条回复23222222\",\"color\":\"#003366\"},\"remark\":{\"value\":\"感谢您的反馈，希望我们的回复能解决您的问题!\",\"color\":\"#4e5051\"}}}");
//        map.put("openId","olJQ3t_jAQzZYayoVmwlGbBUWLq4");
//        map.put("kfChannelId","9c449f6d-319e-4919-bf20-0e0ecf6daba2");
//        map.put("content","未读消息同孩子");
        map.put("thirdId","26");
        String sing = getSign(map);
        System.out.println(sing);
    }
}
