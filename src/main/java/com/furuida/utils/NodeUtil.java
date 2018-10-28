package com.furuida.utils;

import com.furuida.model.Node;

/**
 * @program: furuidaapp
 * @description: 工具类
 * @author: fuzhengquan
 * @create: 2018-10-28 14:03
 **/
public class NodeUtil {
    /**
     * @param node node
     * @param dep 深度
     * @param level 级别
     * @return Node count
     */
    // level1:组长，level2:，主管，level3:副经理，level4:经理，level5:总经理
    int getLevelCount(Node node, int dep, int level) {
        if (node==null) return 0;
        for (int i = 0; i < dep; i++) {
            node = node.getParent();
            if (null == node) {
                return 0;
            }
        }

        return 0;
    }
}
