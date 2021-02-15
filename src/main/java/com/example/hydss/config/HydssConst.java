package com.example.hydss.config;

import java.util.ArrayList;

public class HydssConst {
    final public static int JDCODE=0,SNCODE=1;
    public static String JD="JD",SN="SN";

    public static ArrayList<String> SHOPS=new ArrayList<String>();
    static {
        SHOPS.add(JD);
        SHOPS.add(SN);
    }

    public static int SPACE_ASC=32;
    public static int TAB_ASC=9;
    public static String ACCMAP="accMap";
    public static String ACCSTATEMAP="accStateMap";
    public static String TESTLOGINSN="https://order.suning.com/order/orderList.do";
    public static String TESTLOGINSNPATTERN="<title>我的订单</title>";

}
