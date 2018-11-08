package com.furuida.model;

/**
 * @program: furuidaapp
 * @description:
 * @author: fuzhengquan
 * @create: 2018-11-08 13:50
 **/
public class WechatAccessTokenCheck {
    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
