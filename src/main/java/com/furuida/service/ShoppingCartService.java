package com.furuida.service;

import com.furuida.model.ShoppingCart;

/**
 * @ClassName ShoppingCartService
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 6:19 PM
 * @Version 1.0
 **/
public interface ShoppingCartService {

    public void addShoppingCart(ShoppingCart shoppingCart);
    public void deleteShoppingCart(Long id);

}
