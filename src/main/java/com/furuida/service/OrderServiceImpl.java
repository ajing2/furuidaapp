package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.mapper.UserMapper;
import com.furuida.model.Order;
import com.furuida.model.OrderExample;
import com.furuida.model.ResultBean;
import com.furuida.model.User;
import com.furuida.utils.BlockQueueConsumer;
import com.furuida.utils.CacheQueueManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @program: furuidaapp
 * @description: OrderServiceImpl
 * @author: fuzhengquan
 * @create: 2018-10-27 20:16
 **/
@Component
public class OrderServiceImpl implements OrderService {
    Log log = LogFactory.getLog(OrderServiceImpl.class);
    @Resource
    OrderMapper orderMapper;

    @Resource
    UserService userService;

    @Override
    public void addOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List selectOrder(Order order) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        if (order.getId() != null) {
            criteria.andIdEqualTo(order.getId());
        }
        if (StringUtils.isNotEmpty(order.getUserId())) {
            criteria.andUserIdEqualTo(order.getUserId());
        }
        if (StringUtils.isNotEmpty(order.getOrderNum())) {
            criteria.andOrderNumEqualTo(order.getOrderNum());
        }
        if (order.getPayPrice() != null) {
            criteria.andPayPriceEqualTo(order.getPayPrice());
        }
        if (order.getIsPay() != null) {
            criteria.andIsPayEqualTo(order.getIsPay());
        }
        if (order.getIsShip() != null) {
            criteria.andIsShipEqualTo(order.getIsShip());
        }
        if (order.getIsReceipt() != null) {
            criteria.andIsReceiptEqualTo(order.getIsReceipt());
        }
        if (StringUtils.isNotEmpty(order.getShipNum())) {
            criteria.andShipNumEqualTo(order.getShipNum());
        }
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public List queryAndUser(Order order){
        return orderMapper.queryAndUser(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public ResultBean pay(String userId, String parentId) {
        //判断下级是否已经有3个用户，若满3人则不能添加
//        List<User> uList = userMapper.selectNode();
        try {
//            if (null != uList && uList.size() < 3) {
                //支付
                if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(parentId)) {
                    log.error("参数错误");
                    return ResultBean.fail("param error.");
                }
                String mess = userId + "|" + parentId;
                CacheQueueManager.blockingDeque.put(mess);
                return ResultBean.success();
//            } else {
//                log.error("---人数已达上限");
//                return ResultBean.fail("---人数已达上限");
//            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.fail("---异常了：" + e.getMessage());
        }
    }

    @Override
    public void addOrderByUser(String openId, String orderNum) {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//设置日期格式
        User user = new User();
        user.setWebchat(openId);
        List<User> users = userService.selectUser(user);
        if (users != null && users.size()>0){
            Order order = new Order();
            order.setOrderNum(orderNum);
            order.setUserId(users.get(0).getUserId());
            order.setPayPrice(158);
            order.setIsPay(true);
            order.setPayTime(nowTime);
            order.setIsShip(false);
            order.setCreateTime(nowTime);
            order.setUpdateTime(nowTime);
            addOrder(order);
            log.debug("增加订单成功");
        }else {
            log.error("支付成功, 没有新增订单成功");
        }
    }


    public static String getOrderIdByTime() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<6;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
