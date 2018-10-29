package com.furuida.service;

import com.furuida.mapper.UserMapper;
import com.furuida.model.Node;
import com.furuida.model.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: furuidaapp
 * @description: NodeService
 * @author: fuzhengquan
 * @create: 2018-10-28 13:45
 **/
@Component("modeService")
public class NodeServiceImpl implements NodeService {
    @Resource
    UserMapper userMapper;

    /**
     * 加载数据库数据，初始化NODE
     * @return
     */
    @Override
    public Map<String, Node> getALLNode() {
        List<User> uList = userMapper.selectAll();
        if (null == uList)
            return null;
        final Map<String, Node> nMap = new HashMap<>();
        uList.forEach(user->{
            Node n = new Node();
//            n.setParent;
            n.setData(user);
            List<User> child = uList.stream().filter(user1->user.getLevel()==1).collect(Collectors.toList());
            List<Node> childNodeList = new ArrayList<>();
            if (null != child) {
                for (User u : child) {
                    Node nc = new Node();
                    nc.setParent(n);
                    nc.setData(u);
//                    nc.setChildList();
                    childNodeList.add(nc);
                }
            }
            n.setChildList(childNodeList);
            nMap.put(user.getUserId(), n);
        });
        return nMap;
    }

    @Override
    public void payAndUpgrade(String parentId) {
        Map<String, Node> allNodes = getALLNode();
        if (null == allNodes || allNodes.size() == 0) {
            return;
        }
        Node current = getALLNode().get(parentId);
        if (null == current) {
            return;
        }
        if (current.getChildList().size() == 3) {
            //current 升组长
            List<Node> list1 = current.getAllLeafs(current, 1);

            if (null != list1 && (levelCount(list1, 2) >= 2)) {
                //升主管
                current = current.getParent();
                List<Node> list2 = current.getAllLeafs(current, 2);
                if (null != list2 && (levelCount(list2, 3) >= 4)) {
                    //升副经理
                    current = current.getParent();
                    List<Node> list3 = current.getAllLeafs(current, 3);
                    if (null != list3 && (levelCount(list3, 4) >= 4)) {
                        //升经理
                        current = current.getParent();
                        List<Node> list4 = current.getAllLeafs(current, 4);
                        if (null != list4 && (levelCount(list4, 5) >= 4)) {
                            //升总经理
                            current = current.getParent();

                        }
                    }
                }
            }
        }
    }

    /**
     * 该层的level人员数量
     * @param list
     * @param i
     * @return int
     */
    private int levelCount(List<Node> list, int i) {
        int res = 0;
        if (null == list || list.size() == 0) {
            return 0;
        }
        for (Node n : list) {
            User u = n.getData();
            if (null != u && u.getLevel() == i) {
                res++;
            }
        }
        return res;
    }
}
