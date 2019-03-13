package com.weiller.utils.demo;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormLogin {


    static Map<String, String> COOKIES;

    static String CODE = "0123456789abcdefghigklmnopqrstuvwxyz";

    public static void main(String[] args) throws Exception {
        while (true){
            String pwd = createPwd();
            if(COOKIES==null){
                login("wangjia", pwd);// 输入 用户名，和密码
            }
            if(getInfo()){
                System.out.println("password ：" + pwd);
                break;
            }
        }

    }

    static String createPwd(){
        int len = 10;
        StringBuffer sb = new StringBuffer();
        for (int i=6;i<=len;i++){
            char[] crs = new char[i];
            for(int x = 0;x<26;x++){

            }

        }
        return "";
    }

    public static  void login(String userName,String pwd) throws IOException {
        // 首次请求
        Connection con = Jsoup.connect("http://parttime.zhms.cn/login.aspx");
        con.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");// 配置模拟浏览器
        Connection.Response response = con.execute();

        Document document = Jsoup.parse(response.body());
        Elements formEl = document.select("#ctl00");


        Map<String,String> datas = new HashMap<>();
        for (Element e: formEl.get(0).getElementsByTag("input")){
            if (e.attr("name").equals("LoginName")) {
                e.attr("value", userName);// 设置用户名
            }
            if (e.attr("name").equals("PassWord")) {
                e.attr("value", pwd); // 设置用户密码
            }
            if (e.attr("name").length() > 0) {// 排除空值表单属性
                datas.put(e.attr("name"), e.attr("value"));
            }
        }

        // 二次请求
        Connection con2 = Jsoup.connect("http://parttime.zhms.cn/login.aspx");
        con2.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");// 配置模拟浏览器
        Connection.Response response2 = con2.ignoreContentType(true)
                .method(Connection.Method.POST)
                .data(datas)
                .cookies(response.cookies())
                .execute();
        String body = response2.body();
        System.out.println(body);

        Map<String, String> cookies = response2.cookies();
        COOKIES = cookies;
        for (Map.Entry entry : cookies.entrySet()){
            System.out.println( entry.getKey()+"::" +entry.getValue());
        }

    }

    static boolean getInfo( ) throws IOException {
        // 三次访问用户信息
        Connection con3 = Jsoup.connect("http://parttime.zhms.cn/user/myinfo.aspx");
        con3.header("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36");// 配置模拟浏览器
        Connection.Response resp3 = con3.ignoreContentType(true)
                .method(Connection.Method.GET)
                .cookies(COOKIES)
                .execute();
        String body3 = resp3.body();
        Document document = Jsoup.parse(body3);
        Element dashboard = document.body().getElementById("dashboard");
        Elements select = dashboard.select(".text");
        if(select.size()>0){
            for (Element s : select){
                System.out.println(s.text());
            }
            return true;
        }else{
            return false;
        }
    }
}
