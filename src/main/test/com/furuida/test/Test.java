package com.furuida.test;

import com.furuida.model.User;
import com.furuida.service.NodeService;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;

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
            u.setParentId("127");
            u.setUserId("133");
            u.setPhone("1234424323");
            u.setLevel(0);
            u.setReceiveAddr("s");
            u.setReceiveName("quan");
            u.setWebchat("ssada");
            userService.addUser(u);
            nodeService.initALLNode();
//            nodeService.payAndUpgrade(u.getParentId());
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
    @org.junit.Test
    public void testautoAddNodes() {
        nodeService.autoAddNodes();
    }
    @org.junit.Test
    public void testGetToken() {
        HttpSession session = new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int i) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public HttpSessionContext getSessionContext() {
                return null;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String[] getValueNames() {
                return new String[0];
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public void removeValue(String s) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        };
        userService.getToken(session, "081Eql3x1xWBs90nit4x1NWf3x1Eql33");
    }
}
