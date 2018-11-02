package com.furuida.service;

import com.furuida.mapper.CashHistoryMapper;
import com.furuida.mapper.UserMapper;
import com.furuida.model.CashHistory;
import com.furuida.model.Node;
import com.furuida.model.NodeCache;
import com.furuida.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 **/
@Component("nodeService")
public class NodeServiceImpl implements NodeService {
    Log log = LogFactory.getLog(NodeServiceImpl.class);
    @Resource
    UserMapper userMapper;
    @Resource
    CashHistoryMapper cashHistoryMapper;

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
                changeLevel(current.getData(), 1, 1);
                List<String> list1 = current.getAllLeafs(current, 1);
                int level1Count = levelCount(list1, 1);
                if (null != list1 && (level1Count >= 2)) {
                    //升主管
                    current = current.getParentNode();
                    log.info("==="+current.getData().getUserId()+"升主管");
                    changeLevel(current.getData(), 2, level1Count);
                    List<String> list2 = current.getAllLeafs(current, 2);
                    int level2Count = levelCount(list2, 2);
                    if (null != list2 && (level2Count >= 4)) {
                        //升副经理
                        current = current.getParentNode();
                        log.info("==="+current.getData().getUserId()+"升副经理");
                        changeLevel(current.getData(), 3, level2Count);
                        List<String> list3 = current.getAllLeafs(current, 3);
                        int level3Count = levelCount(list3, 3);
                        if (null != list3 && (level3Count >= 4)) {
                            //升经理
                            current = current.getParentNode();
                            log.info("==="+current.getData().getUserId()+"升经理");
                            changeLevel(current.getData(), 4, level3Count);
                            List<String> list4 = current.getAllLeafs(current, 4);
                            int level4Count = levelCount(list4, 4);
                            if (null != list4 && (level4Count >= 4)) {
                                //升总经理
                                current = current.getParentNode();
                                log.info("==="+current.getData().getUserId()+"升总经理");
                                changeLevel(current.getData(), 5, level4Count);
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

    /**
     * @param u
     * @param i
     * @param levelCount 该级别人员数量
     * @throws Exception
     */
    private void changeLevel(User u, int i, int levelCount) throws Exception {
        User umap = NodeCache.nMap.get(u.getUserId()).getData();
        umap.setLevel(i);
        u.setLevel(i);
        userMapper.updateByPrimaryKey(u);
        //以下是打钱逻辑。。。
        if (i == 1) {
            //组长,给自己80元
            log.info("*******给：" + NodeCache.nMap.get(u.getUserId()).toString() + " 打钱：80元");
            pay(u, 80);
            return;
        }
        if (i == 5) {
            //总经理
            log.info("*******给：" + NodeCache.nMap.get(u.getUserId()).toString() + " 打钱：3200元");
            pay(u, 3200);
            return;
        }
        if (ifChangeLevelPay(u, i)) { //需要打钱,满足升级打钱条件
            //打多少钱
            pay(u, i, 1, levelCount);
        }
        if (ifNewChangeLevelPay(u, i)) { //需要打钱,升级本身导致给上级打钱
            //打多少钱
            pay(u, i, 2, levelCount);
        }
    }

    /**
     * 打钱
     * @param u
     * @param i
     */
    private void pay(User u, int i) {
        CashHistory cash = new CashHistory();
        cash.setUserId(u.getUserId());
        cash.setMoney(i);
        cash.setTime((int) new Date().getTime());
        cash.setAccNum(u.getWebchat());
        cashHistoryMapper.insert(cash);
    }

    private void pay(User u, int i, int type, int levelCount) {
        if (type == 1) {
            User user = find(u, i); //给谁打
            if (null == user) {
                return;
            }
            int price = changeLevelPay(i, levelCount); //打多少
            //打钱
            log.info("*******给：" + NodeCache.nMap.get(user.getUserId()).toString() + " 打钱：" + price + "元");
            pay(u, price);
        }
        if (type == 2) {
            User user = find(u, i); //给谁打
            if (null == user) {
                return;
            }
            int price = newChangeLevelPay(u, i);
            //打钱
            log.info("*******给：" + NodeCache.nMap.get(user.getUserId()).toString() + " 打钱：" + price + "元");
            pay(u, price);
        }
    }

    /**
     * 给谁打钱
     * @param u
     * @param i
     * @return
     */
    private User find(User u, int i) {
        if (i == 1) {
            return u;
        }
        Node node = null;
        int j = 0;
        for (; j < i; j++) {
            node = NodeCache.nMap.get(u.getParentId());
            if (null == node) {
                log.info("======层级不够，不打钱");
                return null;
            }
            if (null == NodeCache.nMap.get(node.getData().getUserId())) {
                log.info("======层级不够，不打钱");
                return null;
            }
            u = NodeCache.nMap.get(node.getData().getUserId()).getData();
        }
        if (j < i - 1) {
            log.info("======层级不够，不打钱");
            return null;
        }
        User user = node.getData();
        if (user.getLevel() != i) {
            log.info("======目标人员不满足打钱等级，不打钱");
            return null;
        }
        return user;
    }

    private int newChangeLevelPay(User u, int i) {
        //打钱
        int price = 0;
        switch (i) {
            case 2:
                price = 130;
                break;
            case 3:
                price = 260;
                break;
            case 4:
                price = 500;
                break;
        }
        return price;
    }

    private int changeLevelPay(int i, int levelCount) {
        int price = 0;
        switch (i) {
            case 2:
                price = 130 * levelCount - 260;
                break;
            case 3:
                price = 260 * levelCount - 500;
                break;
            case 4:
                price = 500 * levelCount - 800;
                break;
            default:
                break;
        }
        return price;
    }

    /**
     * 判断是否升级打钱
     * @param u
     * @param i
     * @return
     */
    private boolean ifChangeLevelPay(User u, int i) {
        //两种情况需要打钱：
        // 1、满足升级打钱条件
        // 2、升级本身导致给上级打钱
        return true;
    }
    /**
     * 判断是否升级本身导致给上级打钱
     * @param u
     * @param i
     * @return
     */
    private boolean ifNewChangeLevelPay(User u, int i) {
        //两种情况需要打钱：
        // 1、满足升级打钱条件
        // 2、升级本身导致给上级打钱
        return false;
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
