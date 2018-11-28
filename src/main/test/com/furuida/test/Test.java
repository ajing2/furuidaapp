package com.furuida.test;

import com.furuida.model.User;
import com.furuida.service.NodeService;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import com.furuida.utils.ExecCommand;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestHeader;

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
    @Resource
    OrderService orderService;

//    @org.junit.Test
    public void testStartMonitor() {
        try {
            String orderId = "1630316288";
            if (StringUtils.isNotEmpty(orderId)) {
                User u = new User();
                u.setUserId(orderId);
                // TODO
                User uList = userService.selectByUserId(orderId);
                if (null!=uList) {
                    String uid = uList.getUserId();
                    String parentId = uList.getParentId();
                    System.out.println("===============uid=" + uid + ",pid=" + parentId);
                    orderService.pay(uid, parentId);
                    uList.setLevel(0);
                    uList.setIspayed(1);
                    userService.updateUser(uList);
//                    ExecCommand run = new ExecCommand();
//                    String cmd = "/usr/local/tomcat8/postMaker.sh " + uid;
//                    run.runLocal(cmd);
                }
            } else {
                System.out.println("================key验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void testTrans() {
        try {
            System.out.println("====================");
//            userService.testTrans();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @org.junit.Test
    public void testautoAddNodes() {
//        nodeService.autoAddNodes();
    }
//    @org.junit.Test
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

//    @org.junit.Test
    public void testUpdateUser() {
        User u = new User();
        u.setIspayed(0);
        u.setUserId("589778865");
        u.setReceiveAddr("北京大兴区金地格林小镇106号楼46单元");
        userService.updateDefaultUser(u);
    }
}
