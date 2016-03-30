package com.lanouteam.dllo.mirror.bean;

/**
 * Created by dllo on 16/3/30.
 */
public  interface RequestUrls {
    //请求测试服务器网址
    String SERVER_URL = "http://api101.test.mirroreye.cn/";

    /**
     * 以下是请求各类数据的URL
     */
    //用户注册
    String USER_REGISTER = SERVER_URL + "index.php/user/reg";

    //故事列表
    String STORY_LIST = SERVER_URL + "index.php/story/story_list";

    //商品列表
    String GOODS_LIST = SERVER_URL + "index.php/products/goods_list";

    //商品详情
    String GOODS_INFO = SERVER_URL + "index.php/products/goods_info";

    //商品分类列表
    String CATEGORY_LIST = SERVER_URL + "index.php/products/category_list";

    //个人中心
    String USER_INFO = SERVER_URL + "index.php/user/user_info";

    //绑定账号
    String USER_BUNDLING = SERVER_URL + "index.php/user/bundling";

    //加入购物车
    String JOIN_SHOPPING_CART = SERVER_URL + "index.php/order/join_shopping_cart";

    //订单列表
    String ORDER_LIST = SERVER_URL + "index.php/order/order_list";

    //购物车列表
    String SHOPPING_CART_LIST = SERVER_URL + "index.php/order/shopping_cart_list";

    //我的收货地址列表
    String ADDRESS_LIST = SERVER_URL + "index.php/user/address_list";

    //添加收货地址
    String ADD_ADDRESS = SERVER_URL + "index.php/user/add_address";

    //编辑收货地址
    String EDIT_ADDRESS = SERVER_URL + "index.php/user/edit_address";

    //删除收货地址
    String DEL_ADDRESS = SERVER_URL + "index.php/user/del_address";

    //设置默认收货地址
    String MR_ADDRESS = SERVER_URL + "index.php/user/mr_address";

    //下订单
    String ORDER_SUB = SERVER_URL + "index.php/order/sub";

    //支付宝支付
    String ALI_PAY_SUB = SERVER_URL + "index.php/pay/ali_pay_sub";

    //微信支付
    String WX_PAY_SUB = SERVER_URL + "index.php/pay/wx_pay_sub";

    //获取微信支付结果
    String WX_ORDERQUERY = SERVER_URL + "index.php/pay/wx_orderquery";

    //物流信息查询
    //TODO 此项信息无请求类名
    String LOGISTICS_INFORMATION_QUERY = SERVER_URL;

    //我的订单详情
    String ORDER_INFO = SERVER_URL + "index.php/order/info";

    //申请退款
    //TODO 此项信息无请求类名
    String APPLICATION_FOR_DRAWBACK = SERVER_URL;

    //退款原因列表
    String REFUND_REASON_LIST = SERVER_URL;

    //故事详情
    String STORY_INFO = SERVER_URL + "index.php/story/info";

    //我的优惠券列表
    String DISCOUNT_LIST = SERVER_URL + "index.php/user/discount_list";


}
