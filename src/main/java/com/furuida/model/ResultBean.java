package com.furuida.model;

import java.io.Serializable;

/**
 * 返回对象包装类(带泛型)
 * 
 * @author zhangjinpeng9
 */
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int NO_LOGIN = -1;

	public static final int SUCCESS = 0;

	public static final int FAIL = 1;

	public static final int NO_PERMISSION = 2;

	public static final int UNKNOWN_EXCEPTION = -99;
	

	/**
	 * 返回的信息(主要出错的时候使用)
	 */
	private String msg = "success";

	/**
	 * 接口返回码, 0表示成功, 其他看对应的定义
	 * 晓风轻推荐的做法是: 
	 * 0   : 成功
	 * >0 : 表示已知的异常(例如提示错误等, 需要调用地方单独处理) 
	 * <0 : 表示未知的异常(不需要单独处理, 调用方统一处理)
	 */
	private int code = SUCCESS;
	/**
     * 默认方法.
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> success() {
        return successMsg(null);
    }

    /**
     * 默认方法.
     * @param msg 错误的消息提示
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> successMsg(String msg) {
        ResultBean<K> apiBean = new ResultBean<K>(msg, SUCCESS, null);
        return apiBean;
    }

    /**
     * 默认方法.
     * @param data 接口返回的数据
     * @param msg 错误的消息提示
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> success(K data, String msg) {
        ResultBean<K> apiBean = new ResultBean<K>(msg, SUCCESS, data);
        return apiBean;
    }

    /**
     * 默认方法.
     * @param data 接口返回的数据
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> success(K data) {
        ResultBean<K> apiBean = new ResultBean<K>("", SUCCESS, data);
        return apiBean;
    }

    /**
     * 失败默认方法.
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> fail() {
        return fail("请求失败");
    }

    /**
     * 失败默认方法.
     * @param msg 错误的消息提示
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> fail(String msg) {
        ResultBean<K> apiBean = new ResultBean<K>(msg, FAIL);
        return apiBean;
    }

    /**
     * 失败默认方法.
     * @param code 错误码
     * @param msg 错误的消息提示
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> fail(int code, String msg) {
        ResultBean<K> apiBean = new ResultBean<K>(msg, code);
        return apiBean;
    }

    /**
     * 失败默认方法.
     * @param code 错误码
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> failWithNoMsg(int code) {
        return fail(code, null);
    }

    /**
     * session失效.
     * @return ResultBean
     * @param <K> Data的类型
     */
    public static <K> ResultBean<K> sessionError() {
        ResultBean<K> apiBean = new ResultBean<K>("登陆超时", FAIL);
        return apiBean;
    }

    /**
     * 判断ResultBean是否成功.
     * @param result ResultBean
     * @return 是否成功
     */
    public static boolean isSuccess(ResultBean<?> result) {
        return result != null && SUCCESS == result.getCode();
    }

	/**
	 * 返回的数据
	 */
	private T data;

	public ResultBean() {
		super();
	}

	public ResultBean(T data) {
		super();
		this.data = data;
	}
	public ResultBean(String msg, int code){
		this.msg = msg;
		this.code = code;
	}

	public ResultBean(String msg, int code, T data){
		this(msg,code);
		this.data = data;
	}

	public ResultBean(Throwable e) {
		super();
		this.msg = e.toString();
		this.code = UNKNOWN_EXCEPTION;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
