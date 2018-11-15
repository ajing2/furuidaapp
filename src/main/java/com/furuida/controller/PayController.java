package com.furuida.controller;

import com.furuida.model.GLpayApi;
import com.furuida.model.User;
import com.furuida.service.OrderService;
import com.furuida.service.UserService;
import com.furuida.utils.PayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pays")
public class PayController {
	Log log = LogFactory.getLog(PayController.class);
	@Resource
	UserService userService;
	@Resource
	OrderService orderService;
	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request, float price, int istype, String uid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("code", 1);
		remoteMap.put("price", price);
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", uid);
		remoteMap.put("orderuid", uid);
		remoteMap.put("goodsname", "心生爱目洗眼液");
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		return resultMap;
	}

	@RequestMapping("/notifyPay")
	public String notifyPay(HttpServletRequest request, HttpServletResponse response, GLpayApi payAPI) {
		try {
			log.error("======================通知了。");
			// 保证密钥一致性
			if (PayUtil.checkPayKey(payAPI)) {
				User u = new User();
				u.setUserId(payAPI.getOrderuid());
				u.setLevel(0);
				u.setIspayed(1);
				userService.updateUser(u);
				// TODO
//			Order o = new Order();
//			o.setUpdateTime(new Date().toLocaleString());
//			o.set
//			orderService.addOrder();
				List<User> uList = userService.selectUser(u);
				if (null!=uList && uList.size() > 0) {
					String uid = uList.get(0).getUserId();
					String parentId = uList.get(0).getParentId();
					orderService.pay(uid, parentId);
					return "OK";
				}
				return "notOK";
			} else {
				log.error("================key验证失败");
				return "notOK";
			}
		} catch (Exception e) {
			log.error("================key验证异常" + e.getMessage(), e);
			return "notOK";
		}
	}

	@RequestMapping("/returnPay")
	public ModelAndView returnPay(HttpServletRequest request, HttpServletResponse response, String orderid) {
		log.error("--------------------000000000000000");
		boolean isTrue = false;
		ModelAndView view = null;
		// 根据订单号查找相应的记录:根据结果跳转到不同的页面
		if (orderid.split("\\|").length<1) {
			return new ModelAndView("/payfailed");
		}
		String uid = orderid.split("\\|")[0];
		User u = new User();
		u.setUserId(uid);
		userService.selectUser(u);
		List<User> uList = userService.selectUser(u);
		if (null!=uList && uList.size() > 0 && uList.get(0).getIspayed()==1) {
			isTrue = true;
		}
		if (isTrue) {
			view = new ModelAndView("/returnPay");
		} else {
			view = new ModelAndView("/payfailed");
		}
		return view;
	}
}
