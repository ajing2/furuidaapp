package com.furuida.service;

import com.furuida.model.ShoppingCart;

import java.util.List;

/**
 * @ClassName ShoppingCartService
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 6:19 PM
 * @Version 1.0
 **/
public interface ShoppingCartService {

    public void addShoppingCart(ShoppingCart shoppingCart);

    public void deleteShoppingCart(String id);

    public void updateShoppingCart(ShoppingCart shoppingCart);

    public List selectShoppingCart(ShoppingCart shoppingCart);

}
