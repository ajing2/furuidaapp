package com.furuida.service;

import com.furuida.model.ResultBean;
import com.furuida.model.TreeNode;

import java.util.List;

public interface NodeService {
    void autoAddNodes();
    void initALLNode();
    void payAndUpgrade(String id, String parentId);
    ResultBean getTree();
}
