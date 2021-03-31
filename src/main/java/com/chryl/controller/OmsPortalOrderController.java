package com.chryl.controller;

import com.chryl.mall.dto.OrderParam;
import com.chryl.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单管理Controller
 */
@Controller
@RequestMapping("/order")
public class OmsPortalOrderController {
    @Autowired
    private OmsPortalOrderService portalOrderService;

    /**
     * 下单,开启延迟消息
     *
     * @param orderParam
     * @return
     */
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }

    /**
     * 付款,
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object payOrder(String orderId) {
        portalOrderService.pay(Long.valueOf(orderId));
        return "suc";
    }
}
