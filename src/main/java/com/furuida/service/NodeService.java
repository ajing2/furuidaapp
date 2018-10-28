package com.furuida.service;

import com.furuida.model.Node;

import java.util.Map;

public interface NodeService {
    Map<String, Node> getALLNode();
    void payAndUpgrade(String parentId);
}
