package com.furuida.model;
/**
 * 支付回调的参数实体类
 *
 * @author zhoutingting
 */
public class GLpayApi {

	/**
	 * GLpay生成的订单ID号
	 */
	private String platform_trade_no;

	/**
	 * 您的自定义订单号
	 */
	private String orderid;

	/**
	 * 订单定价
	 */
	private String price;

	/**
	 * 实际支付金额
	 */
	private String realprice;

	/**
	 * 您的自定义用户ID
	 */
	private String orderuid;

	/**
	 * 秘钥
	 */
	private String key;

	public String getTrade_no() {
		return platform_trade_no;
	}

	public void setTrade_no(String platform_trade_no) {
		this.platform_trade_no = platform_trade_no;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRealprice() {
		return realprice;
	}

	public void setRealprice(String realprice) {
		this.realprice = realprice;
	}

	public String getOrderuid() {
		return orderuid;
	}

	public void setOrderuid(String orderuid) {
		this.orderuid = orderuid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
