package com.furuida.test;

import com.furuida.model.User;
import com.furuida.service.NodeService;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @program: furuidaapp
 * @description: junit
 * @author: fuzhengquan
 * @create: 2018-10-29 17:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-test.xml"}) //加载配置文件
public class Test {
    @Resource
    NodeService nodeService;
    @Resource
    UserService userService;

    @org.junit.Test
    public void testStartMonitor() {
        try {
            System.out.println("====================");
            User u = new User();
//            u.setId(3L);
            u.setParentId("6");
            u.setUserId("13");
            u.setPhone("1234424323");
            u.setLevel(0);
            u.setReceiveAddr("s");
            u.setReceiveName("quan");
            u.setWebchat("ssada");
            userService.addUser(u);
            nodeService.initALLNode();
            nodeService.payAndUpgrade(u.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void testTrans() {
        try {
            System.out.println("====================");
            userService.testTrans();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
