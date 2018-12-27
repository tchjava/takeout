package com.weng.takeout.app;

import com.weng.takeout.util.TakeoutUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        //请输入菜品价格
        System.out.println("-----欢迎进入省钱系统-----");
        while (true) {
            System.out.println("计算最佳省钱方案请按1");
            System.out.println("退出系统请按0");
            String instrcut = scanner("请输入指令");
            if ("0".equals(instrcut)) {
                System.out.println("----欢迎使用省钱系统，下次再见！----");
                System.exit(0);
            } else if ("1".equals(instrcut)) {
                appMain(scanner("请输入菜品价格[多个用逗号隔开]:"));
            } else {
                System.out.println("指令错误，请重新输入哟....");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 主逻辑方法
     *
     * @param prices
     */
    public static void appMain(String prices) {
        try{
            //切割菜品价格的集合
            List<String> list = Arrays.stream(prices.split(",")).collect(Collectors.toList());
            //复制了菜品价格数量的菜品价格集合
            List<List<String>> lists = TakeoutUtil.getmoreList(list,Integer.parseInt(scanner("请问您打算吃多少个菜")));
            //得到未知维度的笛卡儿积
            List<String> dkrList = TakeoutUtil.unknownDimemsional(lists);
            //得到不重复的排列组合
            Collection<List<Double>> finalScheme = TakeoutUtil.noRepetition(dkrList);
            for(List<Double> doubles:finalScheme){
                for(Double doubleKey:doubles){
                    System.out.print(doubleKey+" ");
                }
                System.out.println();
            }
        }catch(Exception e){
            throw new NumberFormatException("自己心里没点B数吗?");
        }

    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new RuntimeException("错了哦...");
    }
}
