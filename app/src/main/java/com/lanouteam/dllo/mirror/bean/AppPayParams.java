package com.lanouteam.dllo.mirror.bean;

/**
 * Created by dllo on 16/4/12.
 */
public interface AppPayParams  {
    //支付宝的签名
    String  AliPaySignature ="66995bee028e028b8751d5da991c387d";

    //根据token
    //goods_id  96Psa1455524521
    //goods_num    1
    //price 0.01
    //device_type  2
//    {"result":"1","msg":"","data":{"order_id":"1460530743A52","address":{"addr_id":"203",
//            "username":"31231","cellphone":"15241460682","addr_info":"41412423ads","zip_code"
//        :"","city":""},"goods":{"goods_name":"GENTLE MONSTER","goods_num":"","des":
//        "BIG BULLY 飛行員太陽鏡","price":"0.01","pic":"http://7xprhi.com2.z0.glb.qiniucdn.com/gmsliver15" +
//                "5cbc9a39c911ee63efda10378130330.jpg","book_copy":
//        "文案（订购商品）文案（订购商品）文案（订购商品）文案（订购商品）"}
//        ,"if_ordain":"1"}}


//现在需要 order_id  1460530743A52
    //商品名 goods_name  GENTLE MONSTER
    //addr_id   203

    //返回一堆给支付宝的一大堆字符串
//    {"msg":"","data":{"str":"service=\"mobile.securitypay.pay\"&partner=\"2088021758262531\"&_input_charset=\"utf-8\"&notify_url=\"http%3A%2F%2Fapi.mirroreye.cn%2Findex.php%2Fali_notify\"&out_trade_no=\"1460530743A52\"&subject=\"GENTLE MONSTER\"&payment_type=\"1\"&seller_id=\"2088021758262531\"&total_fee=\"0.01\"&body=\"GENTLE MONSTER\"&it_b_pay =\"30m\"&sign=\"Z2hwTg7hOlGQY1pPcDffaAPx5xwCuRVuKsdAtY7DwpsPkPv18WhHEMgqraxZy5JRXCBFXxkQ0mydpoZ35azmeL4Mb%2BNxd6vrFGP7Tru%2B%2FLQPqDiTL92pApAoXppk28L8Ji80gBIXeZqO84Ogo6A7WOBJjDAhBny7QK8IC6%2B6UFw%3D\"&sign_type=\"RSA\""}}

}
