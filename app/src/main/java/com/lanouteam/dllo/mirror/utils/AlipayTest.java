package com.lanouteam.dllo.mirror.utils;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;

/**
 * Created by dllo on 16/4/13.
 */
public class AlipayTest {

    //实例化客户端
    AlipayClient client = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key");
    //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify

    AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
    //SDK已经封装掉了公共参数，这里只需要传入业务参数
    //此次只是参数展示，未进行字符串转义，实际情况下请转义





}
