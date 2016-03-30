package com.lanouteam.dllo.mirror.bean;

/**
 * Created by dllo on 16/3/30.
 * 所有POST 请求需要的参数,
 */
public interface RequestParams {


    /**
     * 请求用户注册数据  USER_REGISTER
     * 输入参数:手机号,验证码,密码
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),凭证(token),用户id(uid)
     */
    //用户注册
    String PHONE_NUMBER = "phone_number";  //手机号
    String NUMBER = "number";               //验证码
    String PASSWORD = "password";           //密码

    /**
     * 请求故事列表数据 STORY_LIST
     * 输入参数:凭证(token),用户id(uid),设备类型(device_type),请求条数(page),
     * 最后一条数据插入时间(last_time)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数据数组(pagination)
     * 故事标题(story_title),故事描述(story_des),故事图片(story_img),
     * 故事ID(story_id),故事分享Url(story_url),是否为原创(if_original),
     * 原文连接(original_url),来自哪里(from),多维数组(story_data)
     */

    //故事列表
    String TOKEN = "token";                //凭证
    String UID = "uid";                     //用户 id
    String DEVICE_TYPE = "device_type";     //设备类型
    String PAGE = "page";                   //请求条数
    String LAST_TIME = "last_time";         //最后一条数据插入时间

    /**
     * 请求商品列表数据
     * 输入参数:凭证(token),设备类型(device_type),请求条数(page),最后一条数据插入时间(last_time)
     *          ,分类ID(category_id),版本(version)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数据数组(pagination)
     *          ,商品ID(goods_id),商品名称(goods_name),剩余库存(last_storge),总库存(whole_storge),
     *          是否可以预定(ordain),型号(model),商品封面图片(goods_img),产地(product_area),价格(goods_price),
     *          商品标准图(goods_pic),详情(info_des),品牌名称(brand)
     * 如果涉及到眼镜的功能描述,请参考接口文档
     * */

    //商品列表
    String CATEGORY_ID="category_id";
    String APP_VERSION="version";

    /**
     * 请求商品详情数据
     * 输入参数:凭证(token),设备类型(device_type),分类ID(category_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),商品ID(goods_id),封面图片(cover),
     *         封面标题(goods_name),商品颜色数组(goos_colors),封面描述(cover_des),商品图片数组(goods_imgs),
     *         价格(goods_price),预计发货时间(send_time),产品性别(goods_sex),商品标准图(goods_pic),尺寸图片(goods_size_img),
     *         商品详情描述数组(goods_des),设计故事数组(design_des)
     * */

    //商品详情(参数重复,可直接调用)

    /**
     * 请求商品分类列表数据
     * 输入参数:用户凭证(token)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分类名称(category_name),分类ID(category_id)
     * */

    //商品分类列表(请求参数重复,可直接调用)








}
