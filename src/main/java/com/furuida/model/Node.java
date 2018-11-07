package com.furuida.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: furuidaapp
 * @description: TreeNode
 * @author: fuzhengquan
 * @create: 2018-10-28 13:27
 **/
public class Node {
    private User data;
    private String parent;
    private List<String> childList;

    List<String> allDownLeafs = new ArrayList<>();
    /**
     * 获取向上深度为dep的所有节点.
     * 即同级节点
     * @param dep
     * @return
     */
    public List<String> getAllLeafs(Node node, int dep) {
        if (null == node) return null;
        for (int i = 0; i < dep; i++) {
            node = NodeCache.nMap.get(node.parent);
            if (null == node) {
                return null;
            }
        }
        //get add childs
        allDownLeafs.clear();
        return getAllDownLeafs(node, dep);
    }

    /**
     * 获取向下深度为dep的所有叶子节点.
     *
     * @param dep
     * @return
     */
    public List<String> getAllDownLeafs(Node node, int dep) {
        if (null == node) return null;
        List<String> userList = node.childList;
        List<Node> nodeList = getNodeListByIdList(userList);
        if (dep == 1) {
            List<String> sl = new ArrayList<>();
            nodeList.forEach(n->{
                sl.add(n.getData().getUserId());
            });
            allDownLeafs.addAll(sl);
            return allDownLeafs;
        }
        if (null == nodeList || nodeList.size() == 0) {
            return null;
        }
        dep--;
        int i = dep;
        for (Node n : nodeList) {
            getAllDownLeafs(n, i);
        }
        return allDownLeafs;
    }
    List<Node> getNodeListByIdList(List<String> userList) {
        if (null == userList) {
            return null;
        }
        List<Node> nodeList = new ArrayList<>();
        userList.forEach(userId->{
            nodeList.add(NodeCache.nMap.get(userId));
        });
        return nodeList;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getParent() {
        return parent;
    }
    public Node getParentNode() {
        return NodeCache.nMap.get(parent);
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getChildList() {
        return childList;
    }

    public void setChildList(List<String> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + data.getUserId() +
                ", parent='" + parent + '\'' +
                ", childList=" + childList +
                ", allDownLeafs=" + allDownLeafs +
                ", level=" + data.getLevel() +
                '}';
    }
    public void init(String id, String parentid) {
        User data = new User();
        data.setLevel(0);
        data.setWebchat("web");
        data.setReceiveName("name");
        data.setPhone("23234434");
        data.setUserId(id);
        data.setReceiveAddr("dizhi");
        data.setIspayed(0);
        data.setParentId(parentid);
    }
}
