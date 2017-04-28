package com.unionpay.demo;

import java.security.MessageDigest;
import java.security.Security;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.unionpay.gzap.client.MD5Util;
import com.unionpay.gzap.client.RSA;

public class Demo {
	// 外网地址
		public static String getKeyUrl_HTTPS = "https://auth.95516.com/authonl/gzap-core-access-web/rest/verify/getkey";
		public static final String BANK_URL_HTTPS = "https://auth.95516.com/authonl/gzap-core-access-web/rest/verify/bankcard";
		public static final String ID_NAME_URL_HTTPS = "https://auth.95516.com/authonl/gzap-core-access-web/rest/verify/idcardNumName";
		public static final String ID_SEARCH_URL_HTTPS = "https://auth.95516.com/authonl/gzap-core-access-web/rest/verify/idCardInfSearch";

		

		// 生产测试账号
		private static final String USERNAME_HTTPS = "123456789012345";
		private static final String PWD_HTTPS = "1234567890123456";

		private HashMap<String, Object> params = new HashMap<String, Object>();

		// 银行卡传入参数
		private static final String cardNo = "6225888888888888";
		private static final String CVN2 = "111";
		private static final String expired = "1212";
		private static final String customerNm = "测试姓名";
		private static final String phoneNo = "18888888888";
		private static final String certifId = "420100000000000000";

		// 身份证传入参数
		private static final String XM = "测试姓名";
		
		private static Set<String> retMsgSet = new HashSet<String>(2);


		// https 调用客户端
		@Test
		public void httpsTest() throws Exception {
			httpsDH();
			Client client = Client.create();
			params.put(ParamFlags.APP_ID, USERNAME_HTTPS);
			params.put(ParamFlags.APP_PWD, MD5Util.encodeByMD5(PWD_HTTPS));
			try {
				String rsaKey = getKey(client);
				if (StringUtil.isBlank(rsaKey)) {
					System.out.println("RSAKey 没拿到 key=" + rsaKey);
					return;
				}

				// 银行卡验证
				httpPost(client, BANK_URL_HTTPS, ParamFlags.BANK_NUM_ID, rsaKey);
				httpPost(client, BANK_URL_HTTPS, ParamFlags.BANK_NUM_NAME, rsaKey);
				httpPost(client, BANK_URL_HTTPS, ParamFlags.BANK_THREE_ITEM, rsaKey);
				httpPost(client, BANK_URL_HTTPS, ParamFlags.BANK_FOUR_ITEM, rsaKey);
				httpPost(client, BANK_URL_HTTPS, ParamFlags.BANK_SIX_ITEM, rsaKey);
				// 身份证验证
				httpPost(client, ID_NAME_URL_HTTPS, ParamFlags.ID_NAME, rsaKey);
				httpPost(client, ID_SEARCH_URL_HTTPS, ParamFlags.ID_NAME_SEARCH + "01", rsaKey);
				httpPost(client, ID_SEARCH_URL_HTTPS, ParamFlags.ID_NAME_SEARCH + "02", rsaKey);
				httpPost(client, ID_SEARCH_URL_HTTPS, ParamFlags.ID_NAME_SEARCH + "03", rsaKey);
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				client.destroy();
			}
		}

		private void httpsDH() throws Exception {
			Security.addProvider(new BouncyCastleProvider());
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			trustAllHttpsCertificates();
		}

		private String getKey(Client client) throws Exception {
			HashMap<String, String> resultMap = new HashMap<String, String>();
			try {
				Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
				// 生成datamap
				HashMap<String, String> data = new HashMap<String, String>();
				data.put(ParamFlags.TRADE_TYPE, "02");

				// 将data并入发送报文中
				WebResource webResource = client.resource(getKeyUrl_HTTPS);
				params.put(ParamFlags.DATA, data);
//				System.out.println(params);
				// post提交并返回结果
				resultMap = gson.fromJson(webResource.type("application/json").post(String.class, gson.toJson(params)), HashMap.class);
//				System.out.println("返回:" + resultMap);
			} catch (Exception e) {
				System.out.println(e);
			}
			String secret = resultMap.get("secret");
			params.remove(ParamFlags.DATA);
			return secret;
		}

		private Map httpPost(Client client, String url, String tradeType, String rsaKey) throws  Exception {
			if (client == null || StringUtil.isBlank(tradeType)) {
				return null;
			}
	 
			// 构造报文
			params.put(ParamFlags.DATA, getDataByTradeType(tradeType, rsaKey));
//			System.out.println(params);

			// 构造请求链接
			HashMap<String, String> resultMap = new HashMap<String, String>();
			WebResource webResource = client.resource(url);
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			resultMap = gson.fromJson(webResource.type("application/json").post(String.class, gson.toJson(params)), HashMap.class);
			retMsgSet.add(""+resultMap);
			System.out.println(""+retMsgSet);
//			System.out.println(tradeType + " result = " + resultMap + "\r\n");
			params.remove(ParamFlags.DATA);
			return resultMap;
		}

		private HashMap<String, String> getDataByTradeType(String tradeType, String rsaKey) {
//			return null;/*
			HashMap<String, String> data = new HashMap<String, String>(6);
			if (tradeType.equals(ParamFlags.BANK_NUM_NAME)) {
				data.put(ParamFlags.CUSTOMER_NM, customerNm);
				data.put(ParamFlags.CARD_NO, RSA.encrypt(cardNo, rsaKey));
			} else if (tradeType.equals(ParamFlags.BANK_NUM_ID)) {
				data.put(ParamFlags.CARD_NO, RSA.encrypt(cardNo, rsaKey));
				data.put(ParamFlags.CERTIF_ID, RSA.encrypt(certifId, rsaKey));
			} else if (tradeType.equals(ParamFlags.BANK_THREE_ITEM) || tradeType.equals(ParamFlags.ZH_BANK_THREE_ITEM)) {
				data.put(ParamFlags.CARD_NO, RSA.encrypt(cardNo, rsaKey));
				data.put(ParamFlags.CERTIF_ID, RSA.encrypt(certifId, rsaKey));
				data.put(ParamFlags.CUSTOMER_NM, customerNm);
			} else if (tradeType.equals(ParamFlags.BANK_FOUR_ITEM) || tradeType.equals(ParamFlags.ZH_BANK_FOUR_ITEM)) {
				data.put(ParamFlags.CARD_NO, RSA.encrypt(cardNo, rsaKey));
				data.put(ParamFlags.CERTIF_ID, RSA.encrypt(certifId, rsaKey));
				data.put(ParamFlags.CUSTOMER_NM, customerNm);
				data.put(ParamFlags.PHONE_NO, phoneNo);
			} else if (tradeType.equals(ParamFlags.BANK_SIX_ITEM)) {
				data.put(ParamFlags.CARD_NO, RSA.encrypt(cardNo, rsaKey));
				data.put(ParamFlags.CERTIF_ID, RSA.encrypt(certifId, rsaKey));
				data.put(ParamFlags.CUSTOMER_NM, customerNm);
				data.put(ParamFlags.PHONE_NO, phoneNo);
				data.put(ParamFlags.EXPIRED, RSA.encrypt(expired, rsaKey));
				data.put(ParamFlags.CVN_2, RSA.encrypt(CVN2, rsaKey));
			} else if (tradeType.equals(ParamFlags.ID_NAME) || tradeType.contains(ParamFlags.ID_NAME_SEARCH)) {
				data.put(ParamFlags.XM, XM);
				data.put(ParamFlags.CERTIF_ID, RSA.encrypt(certifId, rsaKey));
			}
			if (tradeType.contains(ParamFlags.ID_NAME_SEARCH)) {
				tradeType = tradeType.substring(4);
			}
			data.put(ParamFlags.TRADE_TYPE, tradeType);
			return data;
		}

		// 绕过Https证书方法，也可以自己实现
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return true;
			}
		};

		private static void trustAllHttpsCertificates() throws Exception {
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
			javax.net.ssl.TrustManager tm = new miTM();
			trustAllCerts[0] = tm;
			javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, null);
			javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}

		public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
				return true;
			}

			public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
				return true;
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
				return;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
				return;
			}
		}

		private static final String ALGORITHM = "MD5";

		private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		public static String encodeByMD5(String str) {
			if (str == null) {
				return null;
			}
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
				messageDigest.update(str.getBytes());
				return getFormattedText(messageDigest.digest());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		private static String getFormattedText(byte[] bytes) {
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);
			// 把密文转换成十六进制的字符串形式
			for (int j = 0; j < len; j++) {
				buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
				buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
			}
			return buf.toString();
		}
}
