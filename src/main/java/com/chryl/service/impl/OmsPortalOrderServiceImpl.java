package com.chryl.service.impl;

import com.chryl.mall.QueueEnum;
import com.chryl.mall.dto.CommonResult;
import com.chryl.mall.dto.OrderParam;
import com.chryl.sender.CancelOrderSender;
import com.chryl.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台订单管理Service
 */
@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

    private static Logger LOGGER = LoggerFactory.getLogger(OmsPortalOrderServiceImpl.class);

    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Autowired
    private AmqpTemplate amqpTemplate;

    //    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //todo 执行一系类下单操作，具体参考mall项目
        LOGGER.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null, "下单成功");
    }

    //    @Override
    public void cancelOrder(Long orderId) {
        //todo 执行一系类取消订单操作，具体参考mall项目
        LOGGER.info("process cancelOrder orderId:{}", orderId);
    }

    @Override
    public void pay(Long orderId) {
        //todo 执行付款操作,消费延时消息
        LOGGER.info("---------------已付款,删除延时消息");
//        amqpTemplate.receive("mall.order.cancel.ttl", orderId);//key:queueName,val:messageId
        amqpTemplate.receive(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName(), orderId);//key:queueName,val:messageId
        LOGGER.info("付款:pay Order orderId:{}", orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为60分钟
        long delayTimes = 60 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
