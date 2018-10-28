package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.mapper.ShoppingCartMapper;
import com.furuida.model.Order;
import com.furuida.model.ShoppingCart;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 6:24 PM
 * @Version 1.0
 **/

@Component
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public void addShoppingCart(ShoppingCart shoppingCart){
        shoppingCartMapper.insertSelective(shoppingCart);
    }

    @Override
    public void deleteShoppingCart(Long id){
        shoppingCartMapper.deleteByPrimaryKey(id);
    }
}
