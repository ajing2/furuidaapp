package com.furuida.service;

import com.furuida.mapper.OrderMapper;
import com.furuida.mapper.ShoppingCartMapper;
import com.furuida.model.Order;
import com.furuida.model.ShoppingCart;
import com.furuida.model.ShoppingCartExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
    public void deleteShoppingCart(String id){
        shoppingCartMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateShoppingCart(ShoppingCart shoppingCart){
        shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
    }

    @Override
    public List selectShoppingCart(ShoppingCart shoppingCart){
        ShoppingCartExample shoppingCartExample = new ShoppingCartExample();
        ShoppingCartExample.Criteria criteria = shoppingCartExample.createCriteria();
        if (StringUtils.isNotEmpty(shoppingCart.getUserId())){
            criteria.andUserIdEqualTo(shoppingCart.getUserId());
        }
        if (shoppingCart.getGoodsId() != null){
            criteria.andGoodsIdEqualTo(shoppingCart.getGoodsId());
        }
        if (shoppingCart.getNum() != null){
            criteria.andNumEqualTo(shoppingCart.getNum());
        }
        return shoppingCartMapper.selectByExample(shoppingCartExample);
    }
}
