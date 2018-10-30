package com.furuida.service;

import com.furuida.mapper.UserMapper;
import com.furuida.model.Node;
import com.furuida.model.NodeCache;
import com.furuida.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    Log log = LogFactory.getLog(NodeServiceImpl.class);
    @Resource
    UserMapper userMapper;

    /**
     * 加载数据库数据，初始化NODE
     * @return
     */
    @Override
    public void initALLNode() {
        List<User> uList = userMapper.selectAll();
        if (null == uList)
            return;
        Map<String, Boolean> countedMap = new HashMap<>(); //已经计算过的
        uList.forEach(user->{
            countedMap.put(user.getUserId(), true);
            Node n = new Node();
            n.setParent(user.getParentId());
            n.setData(user);
            List<User> child = uList.stream().filter(user1->user1.getParentId().equals(user.getUserId())).collect(Collectors.toList());
            List<String> childNodeList = new ArrayList<>();
            if (null != child) {
                for (User u : child) {
                    childNodeList.add(u.getUserId());
                }
            }
            n.setChildList(childNodeList);
            NodeCache.nMap.put(user.getUserId(), n);
        });
        log.debug("=============");
        new NodeCache().print();
    }

    @Override
    public void payAndUpgrade(String parentId) {
        try {
            Map<String, Node> allNodes = NodeCache.nMap;
            if (null == allNodes || allNodes.size() == 0) {
                return;
            }
            Node current = allNodes.get(parentId);
            log.info("=============当前节点=" + current.toString());
            if (null == current) {
                return;
            }
            if (current.getChildList().size() == 3) {
                //current 升组长
                log.info("==="+current.getData().getUserId()+"升组长");
                changeLevel(current.getData(), 1);
                List<String> list1 = current.getAllLeafs(current, 1);
                if (null != list1 && (levelCount(list1, 1) >= 2)) {
                    //升主管
                    current = current.getParentNode();
                    log.info("==="+current.getData().getUserId()+"升主管");
                    changeLevel(current.getData(), 2);
                    List<String> list2 = current.getAllLeafs(current, 2);
                    if (null != list2 && (levelCount(list2, 2) >= 4)) {
                        //升副经理
                        current = current.getParentNode();
                        log.info("==="+current.getData().getUserId()+"升副经理");
                        changeLevel(current.getData(), 3);
                        List<String> list3 = current.getAllLeafs(current, 3);
                        if (null != list3 && (levelCount(list3, 3) >= 4)) {
                            //升经理
                            current = current.getParentNode();
                            log.info("==="+current.getData().getUserId()+"升经理");
                            changeLevel(current.getData(), 4);
                            List<String> list4 = current.getAllLeafs(current, 4);
                            if (null != list4 && (levelCount(list4, 4) >= 4)) {
                                //升总经理
                                current = current.getParentNode();
                                log.info("==="+current.getData().getUserId()+"升总经理");
                                changeLevel(current.getData(), 5);
                                current = current.getParentNode();

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void changeLevel(User u, int i) throws Exception {
        User umap = NodeCache.nMap.get(u.getUserId()).getData();
        umap.setLevel(i);
        u.setLevel(i);
        userMapper.updateByPrimaryKey(u);
        //以下是打钱逻辑。。。
        if (true) { //需要打钱
            //打多少钱

        }
    }
    /**
     * 该层的level人员数量
     * @param list
     * @param i
     * @return int
     */
    private int levelCount(List<String> list, int i) {
        int res = 0;
        if (null == list || list.size() == 0) {
            return 0;
        }
        for (String n : list) {
            User u = NodeCache.nMap.get(n).getData();
            if (null != u && u.getLevel() == i) {
                res++;
            }
        }
        return res;
    }
}
