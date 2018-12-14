package com.furuida.controller;

import com.furuida.model.Order;
import com.furuida.model.ResultBean;
import com.furuida.model.User;
import com.furuida.model.UserInfo;
import com.furuida.service.NodeService;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: furuidaapp
 * @description: OrderController
 * @author: fuzhengquan
 * @create: 2018-10-27 20:18
 **/
@RestController
@RequestMapping("user")
public class UserController {
    Log log = LogFactory.getLog(UserController.class);
    @Resource
    UserService userService;
    @Resource
    NodeService nodeService;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private String addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    private String deleteUser(@RequestParam Long id) {
        try {
            userService.deleteUser(id);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private ResultBean updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResultBean.success("ok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("error.");
        }
    }

    @RequestMapping(value = "/update/default", method = RequestMethod.POST)
    @ResponseBody
    private ResultBean updateDefaultUser(@RequestBody User user) {
        try {
            userService.updateDefaultUser(user);
            return ResultBean.success("ok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("error.");
        }
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    private List selectUser(User user) {
        try {
            return userService.selectUser(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    private ResultBean getToken(HttpServletRequest request, @RequestParam String code) {
        try {
            HttpSession session = request.getSession();
            if (StringUtils.isEmpty(code)) {
                return ResultBean.fail("param can not be null.");
            }
            return ResultBean.success(userService.getToken(session, code));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("get token failed." + e.getMessage());
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    private ResultBean getUserInfo(HttpServletRequest request, @RequestParam String code, @RequestParam String parentId) {
        try {
            HttpSession session = request.getSession();
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(parentId)) {
                return ResultBean.fail("param can not be null.");
            }
            return ResultBean.success(userService.getUserInfo(session, code, parentId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("get user info failed." + e.getMessage());
        }
    }


    @RequestMapping(value = "/children", method = RequestMethod.GET)
    @ResponseBody
    private Boolean getChildrenInfo(@RequestParam String parentId){
        return userService.getChildrenInfo(parentId);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    private ResultBean getUserTree(HttpServletRequest request) {
        try {
            return nodeService.getTree();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("get user tree failed." + e.getMessage());
        }
    }



    @RequestMapping(value = "/qr", method = RequestMethod.GET)
    @ResponseBody
    private ResultBean getQr(@RequestParam String userId){
        return userService.getQr(userId);
    }


}
