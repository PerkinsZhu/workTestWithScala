package qidian.demo;


import qidian.com.qq.weixin.mp.aes.SHA1;
import qidian.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import qidian.com.qq.weixin.mp.aes.XMLParse;

public class Program {

	public static void main(String[] args) throws Exception {

		String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
		String token = "pamtest";
		String timestamp = "1409304348";
		String nonce = "xxxxxx";
		String appId = "wxb11529c136998cb6";
		//明文
		String replyMsg = "<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
		//加密明文
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String miwen = pc.encryptMsg(replyMsg);
		//生成签名
		String signature = SHA1.getSHA1(token, timestamp, nonce, miwen);
		//拼装报文
		String corpAppId = "123456789";
		String xmltext = XMLParse.generate(corpAppId, miwen);
		System.out.println("xml报文: \n" + xmltext + "\n签名:" + signature);

		
		
		//解析报文
		Object[] result = XMLParse.extract(xmltext);
		//拿到消息所属appid，可以区分出消息所属企业
		corpAppId = result[0].toString();
		//拿到密文
		String encrypt = result[1].toString();
		//解密
		String result1 = pc.decryptMsg(signature, timestamp, nonce, encrypt);
		System.out.println("解密后明文: " + result1);
	}
}
