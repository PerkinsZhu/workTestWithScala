package utiltool.encode.demo01;

import org.junit.Test;

import java.io.File;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String filepath = System.getProperty("user.dir")+ File.separator+"temp\\encode";


        System.out.println("--------------公钥加密私钥解密过程-------------------");
        //        String plainText="ihep_公钥加密私钥解密";
        String json = "{" + "\"bgyprogressuuid\": \"aabc891fc62f41728497da2904294e16\"," + "\"bgysupplier\": \"供应商名称3\"," + " \"bgyitemname\": \"项目公司名称3\"," + "\"bgyitemcode\": \"0003\"," + "\"bgyarea\": \"佛山区域3\"," + "\"bgycontractname\": \"合同名称\"," + "\"bgycontractno\": \"合同编号003\"," + "\"bgypayamount\": 888.989," + "\"bgysupplierbank\": \"供应商开户行：中国银行\"," + "\"bgysupplieraccount\": \"供应商账号：88888989989\"," + "\"bgysupplieraccountname\": \"收方户名：佛山碧桂园\"," + "\"bgypaystatus\": false," + "\"dr\": 0}" + "{" + "\"bgyprogressuuid\": \"aabc891fc62f41728497da2904294e15\"," + "\"bgysupplier\": \"供应商名称3\"," + " \"bgyitemname\": \"项目公司名称3\"," + "\"bgyitemcode\": \"0003\"," + "\"bgyarea\": \"佛山区域3\"," + "\"bgycontractname\": \"合同名称\"," + "\"bgycontractno\": \"合同编号003\"," + "\"bgypayamount\": 888.989," + "\"bgysupplierbank\": \"供应商开户行：中国银行\"," + "\"bgysupplieraccount\": \"供应商账号：88888989989\"," + "\"bgysupplieraccountname\": \"收方户名：佛山碧桂园\"," + "\"bgypaystatus\": false," + "\"dr\": 0}";
        //公钥加密过程
        byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), json.getBytes());
        String cipher = Base64.encode(cipherData);
        //私钥解密过程
        byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        String restr = new String(res);
        System.out.println("原文：" + json);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

    }

    @Test
    public void testCreateRAS() {
        //创建随机的公钥和私钥
        String path = System.getProperty("user.dir")+ File.separator+"temp\\encode";
        System.out.println(path);
        RSAEncrypt.genKeyPair(path);
    }


}
