package com.weiller.identity.utils.comm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
/**
 * uuid生成器
 * @author mac
 *
 */
public class UUIDGenerator {

	public UUIDGenerator() {  
    }  
	
    public static String getUUID1() {  
        return UUID.randomUUID().toString();  
    }
    
    public static String getUUID2() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        str = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return str;  
    }
    
    public static String[] getUUID(int number,int type) {  
        if (number < 1) {  
            return null;  
        }  
        String[] ss = new String[number];  
        for (int i = 0; i < number; i++) { 
        	if(type==1){
        		ss[i] = getUUID2();  
        	}else{
        		ss[i] = getUUID1();  
        	}
        }  
        return ss;  
    }


    /**
     *   生成订单编号
         前8位数（20150826）是年月日格式化：yyyyMMdd
         中间的8位数（00001000）是：UID 前4位和后4位
         在后两位（04）：随机生成一个两位数
         在后两位（00）：又是固定的两个0
         接下来的6位数是（617496）：时分秒的格式化HHmmss
         最后两位是（94）：又是随机生成
     * @param uid 用户编号
     * @return
     */
    public static String getOID(String uid){
        StringBuffer sb = new StringBuffer();
        sb.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));// 8位
        sb.append(new SimpleDateFormat("HHmmss").format(new Date()));// 6位
        sb.append(uid.substring(0, 4));//UID 前4位
        sb.append(uid.substring(uid.length()-4, uid.length()));//UID后4位
        sb.append(getRandom(2));    //随机生成一个2位数

        return sb.toString();
    }


    public static String getReportID(){
        return "report_"+getUUID2();
    }

    /**
     * 产生随机的n位数
     * @return
     */
    private static String getRandom(int n){
        Random rad=new Random();

        String result  = "";
        int i = 0;
        while (i<n){
            result += String.valueOf(rad.nextInt(10));
            i++;
        }
        return result;
    }
 /*   public static void main(String[] args) {
        System.out.println(getOID( "211184738160283651"));
    }*/
}
