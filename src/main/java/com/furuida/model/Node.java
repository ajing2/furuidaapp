package com.furuida.model;

import java.util.List;

/**
 * @program: furuidaapp
 * @description: TreeNode
 * @author: fuzhengquan
 * @create: 2018-10-28 13:27
 **/
public class Node {
    private User data;
    private Node parent;
    private List<Node> childList;

    List<Node> allDownLeafs;
    /**
     * 获取向上深度为dep的所有节点.
     * 即同级节点
     * @param dep
     * @return
     */
    public List<Node> getAllLeafs(Node node, int dep) {
        if (null == node) return null;
        for (int i = 0; i < dep; i++) {
            node = node.parent;
            if (null == node) {
                return null;
            }
        }
        //get add childs
        return getAllDownLeafs(node, dep);
    }

    /**
     * 获取向下深度为dep的所有叶子节点.
     *
     * @param dep
     * @return
     */
    List<Node> getAllDownLeafs(Node node, int dep) {
        if (null == node) return null;
        List<Node> nodeList = node.childList;
        if (dep == 1) {
            allDownLeafs.addAll(nodeList);
        }
        dep--;
        if (null == nodeList) {
            return null;
        }
        for (Node n : nodeList) {
            return getAllDownLeafs(n, dep);
        }
        return allDownLeafs;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildList() {
        return childList;
    }

    public void setChildList(List<Node> childList) {
        this.childList = childList;
    }
}
