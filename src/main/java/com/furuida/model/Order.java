package com.furuida.model;

public class Order {
    private Long id;

    private String orderNum;

    private String userId;

    private Integer payPrice;

    private Boolean isPay;

    private String payTime;

    private Boolean isShip;

    private String shipTime;

    private Boolean isReceipt;

    private String receiptTime;

    private String shipNum;

    private String createTime;

    private String updateTime;


    private String webchat;

    private String phone;

    private String receiveName;

    private String receiveAddr;

    private String parentId;

    private int level;

    private String webchatUrl;

    private String webchatName;

    private int ispayed;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWebchatUrl() {
        return webchatUrl;
    }

    public void setWebchatUrl(String webchatUrl) {
        this.webchatUrl = webchatUrl;
    }

    public String getWebchatName() {
        return webchatName;
    }

    public void setWebchatName(String webchatName) {
        this.webchatName = webchatName;
    }

    public int getIspayed() {
        return ispayed;
    }

    public void setIspayed(int ispayed) {
        this.ispayed = ispayed;
    }

    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddr() {
        return receiveAddr;
    }

    public void setReceiveAddr(String receiveAddr) {
        this.receiveAddr = receiveAddr;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }



    public Boolean getIsShip() {
        return isShip;
    }

    public void setIsShip(Boolean isShip) {
        this.isShip = isShip;
    }


    public Boolean getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(Boolean isReceipt) {
        this.isReceipt = isReceipt;
    }


    public String getShipNum() {
        return shipNum;
    }

    public void setShipNum(String shipNum) {
        this.shipNum = shipNum == null ? null : shipNum.trim();
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Boolean getShip() {
        return isShip;
    }

    public void setShip(Boolean ship) {
        isShip = ship;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }

    public Boolean getReceipt() {
        return isReceipt;
    }

    public void setReceipt(Boolean receipt) {
        isReceipt = receipt;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}