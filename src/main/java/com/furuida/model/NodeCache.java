package com.furuida.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: furuidaapp
 * @description: cache
 * @author: fuzhengquan
 * @create: 2018-10-29 13:33
 **/
public class NodeCache {
    public static final Map<String, Node> nMap = new HashMap<>();

    /**
     * print.
     */
    public void print() {
        System.out.println("==================================");
        Node root = getRootNode();
        System.out.println("root=" + root.toString());
        System.out.println("===========开始遍历=======================");
        printAllNodes(root);
        System.out.println("===============printNodes===================");
//        printNodes(root.getAllDownLeafs(root, 3));
    }

    private void printAllNodes(Node root) {
        printNode(root);
    }
    private void printNodes(List<Node> nList) {
        if (nList==null) return;
        nList.forEach(n-> System.out.println("==" + n.toString()));
    }

    void printNode(Node n) {
        System.out.println("node===" + n.toString());
        for (String s : n.getChildList()) {
            printNode(nMap.get(s));
        }
    }
    public static Node getRootNode() {
        Node n = null;
        for (Map.Entry<String, Node> entry : nMap.entrySet()) {
            if (entry.getValue().getParent() == null || entry.getValue().getParent().equals("") || Integer.parseInt(entry.getValue().getParent())==0) {
                n = entry.getValue();
            }
        }
        return n;
    }
}
