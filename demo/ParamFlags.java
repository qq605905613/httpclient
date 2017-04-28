package com.unionpay.demo;


public class ParamFlags {
//	Verify返回值
	public static final String RET_SAME = "00";			// 返回一致(通道返回)
	public static final String RET_UNSAME = "01";	// 返回不一致(通道返回)
	public static final String RET_FAIL = "99";			// 返回失败(通道返回)
	public static final String RET_FAIL_WITH_MSG = "98";	// 返回失败 但返回报文内容(返回至前台时修改为99)(通道返回)
	// 以下参数为新增接口添加的返回码 version= 1.1
	public static final String RET_DECODE_ERR = "97";		// 解密失败, 需重新getKey后再发	
	public static final String RET_RETRY = "96";					// 请重新发送请求, 针对redis某次出现的读取失败问题,重新发送请求解决	nn
	public static final String RET_NET_CON_FAIL = "95";		// 网络连接出错, 全渠道接口超时情况返回该信息,对应非200情况 (通道返回)	
	public static final String RET_CHL_NET_FAIL = "94";		// 通道连接银行网络不通	
	public static final String RET_RECHARGE = "93";			// 余额不足, 未找到新的充值记录		ok
	public static final String RET_IP_ILLEGAL = "92";				// IP地址非法									ok
	public static final String RET_SRV_BMP = "91";				// 不具备该功能位图, 接口调用权限	ok
	public static final String RET_PERMISSION_DENIED = "90";	// 商户不存在			ok				
	public static final String RET_WRONG_ACCESS_PSW = "89";	// 接入密码错误		ok
	public static final String RET_PARA_ERR = "88";				// 传入参数错误				
	public static final String RET_RECHARGING = "87";		// 正在查找新的套餐, 请稍后再试
	public static final String RET_ORG_RECHARGE = "86";	// 所属机构余额不足, 请联系拓展分公司
	public static final String RET_PHOTO_TOO_BIG = "85";	// 身份证照片查询传入照片过大
	public static final String RET_RATE_OUT_RANGE = "84";	// 限流器超限错误		ok
	public static final String RET_ENCRIPT_ERR = "83";		// 加密机错误
	public static final String CIPHER_CONNECTION_ERR = "82";		// 加密机连接错误
	
	// version
	public static final String VER_1_0 = "1.0";				// 默认的接口version版本
	public static final String VER_1_1 = "1.1";				// 接口进行扩展之后的版本 
	
	// DataReform
	public static final String ZH_BANK_THREE_ITEM = "BK0301";	// 银行卡3要素
	public static final String ZH_BANK_FOUR_ITEM = "BK0401";	// 银行卡4要素
	public static final String BANK_THREE_ITEM_CREDIT = "BK0302";	// 银行卡贷记卡3要素
	public static final String BANK_FOUR_ITEM_CREDIT = "BK0402";		// 银行卡贷记卡4要素
	public static final String BANK_THREE_ITEM_DEBIT = "BK0303";		// 银行卡借记卡3要素
	public static final String BANK_FOUR_ITEM_DEBIT = "BK0403";		// 银行卡借记卡4要素
	public static final String BANK_NUM_NAME = "0201";	// 银行卡2要素 卡号 姓名
	public static final String BANK_NUM_ID = "0202";			// 银行卡2要素 卡号 身份证号
	public static final String BANK_THREE_ITEM = "0301";	// 银行卡3要素
	public static final String BANK_FOUR_ITEM = "0401";	// 银行卡4要素
	public static final String BANK_FIVE_ITEM = "0501";		// 银行卡5要素
	public static final String BANK_SIX_ITEM = "0601";		// 银行卡6要素
	public static final String BANK_CARD_PROPERTY = "BK0001";
	public static final String ID_NAME = "ID0201";				// 身份证2要素
	public static final String ID_NAME_SEARCH = "ID03";	// 身份证查询
	public static final String ID_NAME_PHOTO_CHECK = "ID04";	// 身份证照片比对
	public static final String CARD_TRANS_QUERY = "TQ0001";
	
	public static final String	CUSTOMER_NM="customerNm";//用户姓名
	public static final String	CVN2="cvn2";//cvn
	public static final String	EXPIRED="expired";//有效期
	
	//UCChannel
	public static final String CARD_ATTR = "cardAttr";			// 版本号
	public static final String BANK_NAME = "bankNm";			// 版本号
	
	
	public static final String	REAL_NAME="realNm";
	public static final String	ID_CARD_NUM="idCardNum";
	public static final String	APP_ID="appId";
	public static final String	APP_PWD="appPwd";
	public static final String	DATA="data";

	//public
	public static final String	XM = "xm";
	public static final String SIGN = "sign";
	public static final String	CERTIF_ID="certifId";//证件号
	public static final String CARD_NO= "cardNo";
	public static final String	PHOTO="photo";
	public static final String	PHONE_NO="phoneNo";//电话号码
	public static final String	ACC_NO="accNo";					//卡号
	public static final String	CVN_2 = "CVN2";
	public static final String ORDER_ID="orderId";			//订单号
	public static final String	TRADE_TYPE = "tradeType";
	public static final String ADDRESS = "address";
}
