package com.furuida.model;

/**
 * @program: furuidaapp
 * @description: 树
 * @author: fuzhengquan
 * @create: 2018-11-08 17:51
 **/
public class TreeNode {
    private String name;
    private String id;
    private String pId;
    private String isParent;
    private boolean open = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
