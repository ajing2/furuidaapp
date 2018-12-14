package com.furuida.service;

import com.furuida.mapper.CashHistoryMapper;
import com.furuida.mapper.UserMapper;
import com.furuida.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    boolean hasZong = false;
    /**
     * 自动添加节点测试.
     */
    @Override
    public void autoAddNodes() {
        findNode();
    }

    private void findNode() {
        initALLNode();
        Node root = NodeCache.getRootNode();
        List<String> sL = new ArrayList<>();
        for (int i = 1;; i++) {
            /*if (i==1) {
                payAndUpgrade((i+1) + "-1", root.getData().getUserId());
                payAndUpgrade((i+1) + "-2", root.getData().getUserId());
                payAndUpgrade((i+1) + "-3", root.getData().getUserId());
                continue;
            }
            initALLNode();
            List<String> sL = root.getAllDownLeafs2(NodeCache.getRootNode(), i-1);
            if (null!= sL&&sL.size()>0) {
                int ii = 0;
                for (String s:sL) {
                    payAndUpgrade((i+1) + "-" + (ii++), s);
                    payAndUpgrade((i+1) + "-" + (ii++), s);
                    payAndUpgrade((i+1) + "-" + (ii++), s);
                }
            }*/
            if (i==1) {
                payAndUpgrade((i+1) + "-1", root.getData().getUserId());
                payAndUpgrade((i+1) + "-2", root.getData().getUserId());
                payAndUpgrade((i+1) + "-3", root.getData().getUserId());
                sL = new ArrayList<>();
                sL.add((i+1) + "-1");
                sL.add((i+1) + "-2");
                sL.add((i+1) + "-3");
                continue;
            }
            initALLNode();
//            List<String> sL = root.getAllDownLeafs2(NodeCache.getRootNode(), i-1);
            if (null!= sL&&sL.size()>0) {
                int ii = 0;
                List<String> sL2 = new ArrayList<>();
                for (String s:sL) {
                    String s1 = (i+1) + "-" + (ii++);
                    String s2 = (i+1) + "-" + (ii++);
                    String s3 = (i+1) + "-" + (ii++);
                    payAndUpgrade(s1, s);
                    payAndUpgrade(s2, s);
                    payAndUpgrade(s3, s);
                    sL2.add(s1);
                    sL2.add(s2);
                    sL2.add(s3);
                }
                sL = new ArrayList<>();
                sL.addAll(sL2);
            }
        }
    }

    /**
     * 加载数据库数据，初始化NODE
     * @return
     */
    @Override
    public void initALLNode() {
        List<User> uList = userMapper.selectNode();
        if (null == uList) {
            return;
        }
//        Map<String, Boolean> countedMap = new HashMap<>(); //已经计算过的
        uList.forEach(user->{
//            countedMap.put(user.getUserId(), true);
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
//        new NodeCache().print();
    }

    @Transactional
    @Override
    public void payAndUpgrade(String id, String parentId) {
        try {
            //for test
//            User u = new User();
//            u.setParentId(parentId);
//            u.setUserId(id);
//            u.setPhone("1234424323");
//            u.setLevel(0);
//            u.setReceiveAddr("s");
//            u.setReceiveName("quan");
//            u.setWebchat("ssada");
//            u.setWebchatUrl("");
//            u.setWebchatName(id);
//            u.setIspayed(1);
//            userMapper.insertSelective(u);
            initALLNode();
            Map<String, Node> allNodes = NodeCache.nMap;
            if (null == allNodes || allNodes.size() == 0) {
                return;
            }
            Node current = allNodes.get(parentId);
            if (null == current) {
                log.error("=============parentId=" + parentId);
            }
//            current.init(id, parentId);
            if (null == current) {
                return;
            }
            log.info("=============当前节点=" + current.toString());
            if (current.getChildList().size() == 3) {
                //current 升组长
                log.info("===【"+current.getData().getWebchatName()+"】升组长");
                changeLevel(current.getData(), 1, 1, current.getData());
                List<String> list1 = current.getAllLeafs(current, 1);
                int level1Count = levelCount(list1, 1);
                if (null != list1 && (level1Count >= 2)) {
                    current = current.getParentNode();
                    if (current.getData().getLevel()==2) { //已经是主管
                        return;
                    }
                    //升主管
                    log.info("===【"+current.getData().getWebchatName()+"】升主管");
                    current.getData().setLevel(2); //node 升级
                    User l2 = current.getData();

                    //先升级
                    List<String> list2 = current.getAllLeafs(current, 2);
                    int level2Count = levelCount(list2, 2);
                    changeLevel(current.getData(), 2, level2Count, l2);
                    //再
                    int level2LCount = levelCount(current.getAllLeafs(current, 1), 2);

                    if (level2LCount >= 2) {
                        //升副经理
                        current = current.getParentNode();
                        if (current.getData().getLevel()==3) { //已经是副经理
                            return;
                        }
                        log.info("===【"+current.getData().getWebchatName()+"】升副经理"); //收下二级主管的钱
                        current.getData().setLevel(3); //node 升级
                        User l3 = current.getData();
                        List<String> list3 = current.getAllLeafs(current, 3);
                        int level3Count = levelCount(list3, 3);
                        changeLevel(current.getData(), 3, level3Count, l2);
                        //再升级
                        int level3LCount = levelCount(current.getAllLeafs(current, 1), 3);
                        if (null != list3 && (level3LCount >= 2)) {
                            //升经理
                            current = current.getParentNode();
                            if (current.getData().getLevel()==4) { //已经是经理
                                return;
                            }
                            log.info("===【"+current.getData().getWebchatName()+"】升经理");
                            current.getData().setLevel(4); //node 升级
                            User l4 = current.getData();
                            List<String> list4 = current.getAllLeafs(current, 4);
                            int level4Count = levelCount(list4, 4);
                            changeLevel(current.getData(), 4, level4Count, l3);
                            //再升级
                            int level4LCount = levelCount(current.getAllLeafs(current, 1), 4);
                            if (null != list4 && (level4LCount >= 2)) {
                                //升总经理
                                current = current.getParentNode();
                                if (current.getData().getLevel()==5) { //已经是总经理
                                    return;
                                }
                                log.info("===【"+current.getData().getWebchatName()+"】升总经理");
                                current.getData().setLevel(5); //node 升级
//                                User l5 = current.getData();
                                changeLevel(current.getData(), 5, level4Count, l4);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("打钱升级异常了：" + e.getMessage(), e);
        }
    }

//    private void payParent(Node current, int i) {
//        User u = find(current.getData(), 1);
//        if (u != null) {
//            pay(u, i);
//        }
//    }

    @Override
    public ResultBean getTree() {
        List<TreeNode> tree = new ArrayList<>();
        TreeNode root = new TreeNode();
        root.setpId("-1");
        root.setId("0");
        root.setName("系统");
        tree.add(root);
        List<User> uList = userMapper.selectNode();
        if (null == uList) {
            return null;
        }
        Map<String, String> pmap = new HashMap<>();
        uList.forEach(user->{
            pmap.put(user.getParentId(), "parent");
        });
        uList.forEach(user->{
            TreeNode n = new TreeNode();
            n.setId(user.getUserId());
            String le = "";
            int level = user.getLevel();
            switch (level) {
                case 0:
                    le = "会员";
                    break;
                case 1:
                    le = "组长";
                    break;
                case 2:
                    le = "主管";
                    break;
                case 3:
                    le = "副经理";
                    break;
                case 4:
                    le = "经理";
                    break;
                case 5:
                    le = "总经理";
                    break;
                case -1:
                    le = "游客";
                    break;
            }
            int show_level = user.getShowLevel();
            String showLe = "";
            switch (show_level) {
                case 0:
                    showLe = "会员";
                    break;
                case 1:
                    showLe = "组长";
                    break;
                case 2:
                    showLe = "主管";
                    break;
                case 3:
                    showLe = "副经理";
                    break;
                case 4:
                    showLe = "经理";
                    break;
                case 5:
                    showLe = "总经理";
                    break;
                case -1:
                    showLe = "游客";
                    break;
            }
            n.setName(user.getWebchatName() + "-" + user.getUserId() + "("+ le + ")-[" + showLe + "]");
            n.setpId(user.getParentId());
            if (pmap.containsKey(user.getUserId())) {
                n.setIsParent("parent");
            }
            tree.add(n);
        });
        log.debug("=============");
        return ResultBean.success(tree);
    }

    /**
     * @param u
     * @param i
     * @param levelCount 该级别人员数量
     * @throws Exception
     */
    private void changeLevel(User u, int i, int levelCount, User shengjiUser) throws Exception {
        User umap = NodeCache.nMap.get(u.getUserId()).getData();
        umap.setLevel(i);
        u.setLevel(i);
        switch (i) {
            case 1:
                u.setShowLevel(1);
                break;
            default:
                break;
        }
        userMapper.updateByPrimaryKeySelective(u);
        //以下是打钱逻辑。。。
        if (i == 1) {
            //组长,给自己80元
            pay(u, i,80);
            return;
        }
        if (i == 5) {
            //总经理
            pay(find(u, 5), i, 800);
            hasZong = true;
            return;
        }
        if ((i==2 && levelCount==1) || (i==2 && levelCount>9)) { //一个主管不打钱
            return;
        }
        if (i==2 && levelCount==2) { //两个主管就打钱
            User user = find(u, 2); //给谁打
            if (null != user) {
                pay(user, i, 0);
            }
            return;
        }
        if (i==2 && levelCount>=3) { //三个主管就打钱
            User user = find(u, 2); //给谁打
            if (null != user) {
                pay(user, i, 130);
            }
            return;
        }
        if ((i==3 && levelCount==1) || (i==3 && levelCount>27)) { //1个副经理不打钱
            return;
        }
        if (i==3 && levelCount==2) { //两个副经理就打钱
            User user = find(u, 3); //给谁打
            if (null != user) {
                pay(user, i, 20);
            }
            return;
        }
        if (i==3 && levelCount>=3) { //三个副经理就打钱
            User user = find(u, 3); //给谁打
            if (null != user) {
                pay(user, i, 260);
            }
            return;
        }
        if ((i==4 && levelCount==1) || (i==4 && levelCount>81)) { //1个副经理不打钱
            return;
        }
        if (i==4 && levelCount==2) { //三个副经理就打钱
            User user = find(u, 4); //给谁打
            if (null != user) {
                pay(user, i, 200);
            }
            return;
        }
        if (i==4 && levelCount>=3) { //三个副经理就打钱
            User user = find(u, 4); //给谁打
            if (null != user) {
                pay(user, i, 500);
            }
            return;
        }

        if (i==5 &&  levelCount<=243) { //1个副经理不打钱
            User user = find(u, 5); //给谁打
            if (null != user) {
                pay(user, i, 800);
            }
            return;
        }

//        if (levelCount < 4 || levelCount > 4) { //不到4个不打钱
//            return;
//        }
        /*if (ifChangeLevelPay(u, i, levelCount)) { //需要打钱,满足升级打钱条件
            //打多少钱
            pay(u, i, 1, levelCount);
        }else {
            if (i>=2) {
                User user = find(u, i); //给谁打
                if (null!=user) {
                    Node n = NodeCache.nMap.get(user.getUserId());
                    if(null!= n&&n.getData()!=null&&n.getData().getIspayed()==1){
                        pay(u, i, 2, levelCount);
                    }
                }
            }
        }*/
    }

    /**
     * 打钱
     * @param u
     * @param i
     */
    private void pay(User u, int i, int money) {
        if (null == u) {
            return;
        }
        u.setShowLevel(i);
        userMapper.updateShowLevelByPrimaryKey(u);
        CashHistory cash = new CashHistory();
        cash.setUserId(u.getUserId());
        cash.setMoney(money);
        cash.setTime(new Date().toLocaleString());
        cash.setAccNum(u.getWebchat());
        cash.setOpenid(u.getUserId());
        cashHistoryMapper.insert(cash);
        log.info("**pay*****给：" + NodeCache.nMap.get(u.getUserId()).toString() + " 打钱：" + money + "元");
    }

    /*private void pay(User u, int i, int type, int levelCount) {
        if (type == 1) {
            User user = find(u, i); //给谁打
            if (null == user) {
                return;
            }
            user.setIspayed(1);
            NodeCache.nMap.get(user.getUserId()).getData().setIspayed(1);
            userMapper.updateByPrimaryKeySelective(user);
            int price = changeLevelPay(i, levelCount); //打多少
            //打钱
            pay(user, price);
        }
        if (type == 2) {
            User user = find(u, i); //给谁打
            if (null == user) {
                return;
            }
            int price = newChangeLevelPay(u, i);
            //打钱
//            log.info("*******给：" + NodeCache.nMap.get(user.getUserId()).toString() + " 打钱：" + price + "元");
            pay(user, price);
        }
    }*/

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
//        if (user.getLevel() != i) {
//            log.info("======目标人员不满足打钱等级，不打钱");
//            return null;
//        }
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
//                if(levelCount>4) {
                    price = 130;
//                }
//                if(levelCount==4) {
//                    price = 130 * levelCount - 260;
//                }
//                price = levelCount>4?130:(130 * levelCount - 260);
                break;
            case 3:
//                if(levelCount>4) {
                    price = 260;
//                }
//                if(levelCount==4) {
//                    price = 260 * levelCount - 500;
//                }
//                price = levelCount>4?260:(260 * levelCount - 500);
                break;
            case 4:
//                if(levelCount>4) {
                    price = 500;
//                }
//                if(levelCount==4) {
//                    price = 500 * levelCount - 800;
//                }
//                price = levelCount>4?500:(500 * levelCount - 800);
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
    private boolean ifChangeLevelPay(User u, int i, int level2Count) {
        //两种情况需要打钱：
        // 1、满足升级打钱条件
        Node current = NodeCache.nMap.get(u.getUserId());
//        List<String> list = current.getAllLeafs(current, i);
//
//        int level2Count = levelCount(list, i);
        // 2、升级本身导致给上级打钱
        return level2Count>=4;
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
            if (null != u && u.getLevel() >= i) { // >=
                res++;
            }
        }
        return res;
    }
}
