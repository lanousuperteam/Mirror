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
     * ,分类ID(category_id),版本(version)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数据数组(pagination)
     * ,商品ID(goods_id),商品名称(goods_name),剩余库存(last_storge),总库存(whole_storge),
     * 是否可以预定(ordain),型号(model),商品封面图片(goods_img),产地(product_area),价格(goods_price),
     * 商品标准图(goods_pic),详情(info_des),品牌名称(brand)
     * 如果涉及到眼镜的功能描述,请参考接口文档
     */
    //商品列表
    String CATEGORY_ID = "category_id";
    String APP_VERSION = "version";


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


    /**
     * 请求个人中心数据
     * 输入参数:用户凭证(token)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),用户名称(username),用户头像(heading),
     *         是否绑定(if_bind)
     * */
    //个人中心(请求参数重复,可直接调用)


    /**
     * 请求绑定账号数据
     * 输入参数:是微博登录还是微信登录(iswb_orwx),微博名称(wb_name),微博头像(wb_img),微博ID(wb_id),
     * 微信名称(wx_name),微信头像(wx_img),微信ID(wx_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),凭证(token);
     */
    //绑定账号
    String ISWB_ORWX = "iswb_orwx";
    String WB_NAME = "wb_name";
    String WB_IMG = "wb_img";
    String WB_ID = "wb_id";
    String WX_NAME = "wx_name";
    String WX_IMG = "wx_img";

    /**
     * 请求加入购物车数据
     * 输入参数:用户凭证(token),商品ID(goods_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data)
     */
    //加入购物车
    String GOODS_ID = "goods_id";


    /**
     * 请求订单列表数据
     * 输入参数:用户凭证(token),设备类型(device_type),请求条数(page),
     *         最后一条数据插入时间(last_time)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数组数据(pagination),
     *         是否支付(if_pay),快递号(post_no),是否签收(sign_for),订单状态(order_status),
     *         商品详情(goods_info)
     * */
    //订单列表(请求参数重复,可直接调用)


    /**
     * 请求购物车列表数据
     * 输入参数:用户凭证(token),设备类型(device_type),请求条数(page),
     *         最后一条数据插入时间(last_time)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数组数据(pagination),
     *         商品ID(goods_id),商品名称(goods_name),商品图片(goods_img),商品价格(goods_price)
     * */
    //购物车列表(请求参数重复,可直接调用)


    /**
     * 请求我的收货地址列表数据
     * 输入参数:用户凭证(token),设备类型(device_type),请求条数(page),
     *         最后一条数据插入时间(last_time)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),分页数组数据(pagination),
     *         地址ID(addr_id),收件人(username),收件人电话(cellphone),详细地址(addr_info),
     * */
    //我的收货地址(请求参数重复,可直接调用)


    /**
     * 请求添加收货地址数据
     * 输入参数:用户凭证(token),收货人(username),收货人电话(cellphone),详细地址(addr_info),
     *         邮编(zip_code),城市(city)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data)
     * */
    //添加收货地址
    String USERNAME="username";
    String CELLPHONE="cellphone";
    String ADDR_INFO="addr_info";
    String ZIP_CODE="zip_code";
    String CITY="city";

    /**
     * 请求编辑收货地址数据
     * 输入参数:用户凭证(token),地址ID(addr_id),收货人(username),收货人电话(cellphone),
     *          详细地址(addr_info),邮编(zip_code),城市(city)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data)
     * */
    //编辑收货地址(其他请求参数重复)
    String ADDR_ID="addr_id";


    /**
     * 请求删除收货地址数据
     * 输入参数:用户凭证(token),地址ID(addr_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data)
     * */
    //删除收货地址(请求参数重复,可直接调用)


    /**
     * 设置默认收货地址
     * 输入参数:用户凭证(token),地址ID(addr_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data)
     * */
    //设置默认收货地址(请求参数重复,可直接调用)


    /**
     * 请求下订单数据
     * 输入参数:用户凭证(token),商品ID(goods_id),商品数量(goods_num),订单价格(price),
     *         优惠券ID(discout_id),设备类型(device_type),
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),订单号(order_id),
     *         地址ID(addr_id),收件人(username),收件人电话(cellphone),详细地址(addr_info),
     *         城市(city),邮编(zip_code),商品名称(goods_name),商品数量(goods_num),
     *         商品简介(des),商品单价(price),商品图(pic),是否为预定订单(if_ordain)
     * */
    //下订单数据(剩余参数重复,可直接调用)
    String GOODS_NUM="goods_num";
    String PRICE="price";
    String DISCOUT_ID="discout_id";

    /**
     * 请求我的订单详情
     * 输入参数:用户凭证(token),设备类型(device_type),订单号(order_id)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),订单号(order_id),
     *         订单金额(order_price),下单时间(order_time),订单状态(order_status),
     *         订单状态对应大图(status_big_img),收货人(username),收货人电话(cellphone),
     *         收货地址(addr_info),商品信息数组(goods_list),是否有物流信息(if_post),物流公司(post_name),
     *         物流单号(post_no),物流信息数组(post_info).
     * */
    //我的订单详情
    String ORDER_ID="order_id";


    /**
     * 请求故事详情
     * 输入参数:用户凭证(token),设备类型(device_type),故事ID(story_id)
     * 输入参数:调用结果(result),错误信息(msg),返回内容(data),故事标题(story_title),
     *         故事描述(story_des),故事图片(story_des),故事ID(story_id),是否为原创(if_original),
     *         原文连接(original_url),来自哪里(from),故事分享Url(story_url),多维数组(story_data)
     * */
    //故事详情
    String STORY_ID="story_id";


    /**
     * 请求我的优惠券列表
     * 输入参数:用户凭证(token)
     * 输出参数:调用结果(result),错误信息(msg),返回内容(data),优惠券ID(discount_id),
     *         优惠券名称(discount_name)
     * */
    //我的优惠券列表(请求参数重复,可直接调用)














}
