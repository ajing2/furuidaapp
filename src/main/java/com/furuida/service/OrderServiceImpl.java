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
import java.util.List;

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
    UserMapper userMapper;
    @Resource
    BlockQueueConsumer consumer;

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
}
