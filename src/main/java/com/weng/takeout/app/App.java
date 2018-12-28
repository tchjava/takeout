package com.weng.takeout.app;

import com.weng.takeout.entity.Scheme;
import com.weng.takeout.util.ComputeFactory;
import com.weng.takeout.util.TakeoutUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        //请输入菜品价格
        System.out.println("-----欢迎进入省钱小助手-----");
        while (true) {
            System.out.println("计算最佳省钱方案请按1");
            System.out.println("退出系统请按0");
            String instrcut = scanner("请输入指令");
            if ("0".equals(instrcut)) {
                System.out.println("----欢迎使用省钱小助手，下次再见！----");
                System.exit(0);
            } else if ("1".equals(instrcut)) {
                appMain(scanner("菜品价格[多个用逗号隔开]:"));
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

            //测试代码
            for(List<Double> sss:finalScheme){
                System.out.println("测试sss集合长度，理论上长度是1，但是结果是:"+sss.size());
            }
            //测试代码结束


            //询问饭费的价格
            double mealPrice = Double.parseDouble(scanner("米饭的价格"));
            //询问米饭数量
            int mealNum=Integer.parseInt(scanner("米饭数量"));
            //询问餐盒费
            double lunchBoxPrice = Double.parseDouble(scanner("餐盒费"));
            //询问配送费
            double disPacthing=Double.parseDouble(scanner("配送费"));
            //满减
            TreeMap<Double, Double> planMap = new TreeMap<>();
            while(true){
                planMap=putPlan(scanner("满减的方案如满30减5元请写成-->30,5"),planMap);
               String instrcut=scanner("还有吗?y/n");
               if("n".equalsIgnoreCase(instrcut)){
                   break;
               }
               if(!"y".equalsIgnoreCase(instrcut)){
                   System.out.println("指令有误--就当你没有了哈..");
                   try{
                       Thread.sleep(1500);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   break;
               }
            }
            TreeMap<Double, Double> readPacketMap = new TreeMap<>();
            //红包
            while(true){
                readPacketMap=putPlan(scanner("红包的方案如满30,5元可用请写成-->30,5"),readPacketMap);
                String instrcut=scanner("还有吗?y/n");
                if("n".equalsIgnoreCase(instrcut)){
                    break;
                }
                if(!"y".equalsIgnoreCase(instrcut)){
                    System.out.println("指令有误--就当你没有了哈..");
                    try{
                        Thread.sleep(1500);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
            List<Scheme> schemes = new ArrayList<>();
            for(List<Double> doubles:finalScheme){
                Scheme scheme=new Scheme();
                //设置米饭的费用
                scheme.setMealCost(mealPrice);
                //设置米饭的数量
                scheme.setMealNum(mealNum);
                //设置餐盒费
                scheme.setLunchBoxCost(lunchBoxPrice);
                //设置配送费
                scheme.setDisPatching(disPacthing);
                //设置满减
                scheme.setSalePlan(planMap);
                //设置红包
                scheme.setRedPacket(readPacketMap);
                //设置集合
                scheme.setScheme(doubles);
                schemes.add(scheme);
            }
            Scheme scheme=ComputeFactory.firstOptimalScheme(schemes);
            System.out.println(scheme.getDishNum()+"个菜品的最优方案:");
            System.out.print("菜品价格:");
            for(Double doubleKey:scheme.getScheme()){
                System.out.print(doubleKey.doubleValue()+" ");
            }
            System.out.println();
            System.out.println("-----完毕------");
        }catch(Exception e){
            throw new NumberFormatException("是真的价格还是乱填，自己心里没点B数吗?");
        }

    }

    /**
     * 压入满减或者红包的计划
     * @param plan
     * @param planMap
     * @return
     */
    public static TreeMap<Double,Double> putPlan(String plan,TreeMap<Double, Double> planMap){
        try{
           String[] str=plan.split(",");
            planMap.put(Double.valueOf(str[0]),Double.valueOf(str[1]));
            return planMap;
        }catch (Exception e){
            throw new NumberFormatException("是真的价格还是乱填，自己心里没点B数吗?");
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
