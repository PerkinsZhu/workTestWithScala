package utiltool.encode.demo03;

import org.junit.Test;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by PerkinsZhu on 2018/9/27 11:52
 **/
public class Base64Test {

    public String getEncodedBase64(String plainText) {
        String encoded = null;
        try {
            byte[] bytes = plainText.getBytes("UTF-8");
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return encoded;
    }

    public byte[] getDecodedBase64(String plainText) {
        byte[] decoded = null;
        try {
            byte[] bytes = plainText.getBytes("UTF-8");
            decoded = Base64.getDecoder().decode(bytes);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return decoded;
    }

    @Test
    public void testBase64() throws UnsupportedEncodingException {
        String content = "hello=";
        String data = getEncodedBase64(content);
        System.out.println("加密结果：" + data);
        byte[] result = getDecodedBase64(data);
        System.out.println("解密结果：" + new String(result, "UTF-8"));
    }

    String src = "hello ";

    @Test
    // 用jdk实现
    public void jdkBase64() {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            String encode = encoder.encodeToString(src.getBytes());
            System.out.println("encode:" + encode);
            Base64.Decoder decoder = Base64.getDecoder();
            System.out.println("decode:" + new String(decoder.decode(encode)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    // 用Apache的common codes实现
    public  void commonsCodesBase64() {
        byte[] encodeBytes = org.apache.commons.codec.binary.Base64.encodeBase64(src.getBytes());
        System.out.println("common codes encode:" + new String(encodeBytes));
        byte[] dencodeBytes = org.apache.commons.codec.binary.Base64.decodeBase64(encodeBytes);
        System.out.println("common codes decode:" + new String(dencodeBytes));
    }

/*    @Test
    // 用bouncy castle实现
    public  void bouncyCastleBase64(){
        byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
        System.out.println("bouncy castle encode:" + new String(encodeBytes));
        byte[] dencodeBytes = org.bouncycastle.util.encoders.Base64.decode(encodeBytes);
        System.out.println("bouncy castle decode:" + new String(dencodeBytes));
    }*/

}
