package com.furuida.controller;



import com.furuida.model.ShoppingCart;
import com.furuida.service.ShoppingCartService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ShoppingCartController
 * @Description TODO
 * @Author lingxiangxiang
 * @Date 6:18 PM
 * @Version 1.0
 **/

@RestController
@RequestMapping("shopping")
public class ShoppingCartController {
    Log log = LogFactory.getLog(OrderController.class);
    @Resource
    ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private String addShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        try {
            shoppingCartService.addShoppingCart(shoppingCart);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    private String deleteShoppingCart(@RequestParam Long id) {
        try {
            shoppingCartService.deleteShoppingCart(id);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private String updateShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        try {
            shoppingCartService.updateShoppingCart(shoppingCart);
            return "ok";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "failed";
        }
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    private List selectShoppingCart(ShoppingCart shoppingCart) {
        try {
            ArrayList<ShoppingCart> data = (ArrayList<ShoppingCart>) shoppingCartService.selectShoppingCart(shoppingCart);
            return data;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

}
