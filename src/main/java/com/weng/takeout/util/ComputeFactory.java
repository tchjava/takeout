package com.weng.takeout.util;

import com.weng.takeout.entity.Scheme;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
* @Description:    计算工厂
* @Author:         wengzhongjie
* @CreateDate:     2018-12-28 9:36
*/
public class ComputeFactory {
    /**
     * P:?
     * 得到最优方案排序
     * @param schemes
     * @return
     */
    public static List<Scheme> optimalScheme(List<Scheme> schemes){
        if(schemes.isEmpty()){
            return null;
        }
        if(schemes.size()==1){
            return schemes;
        }
        //补充scheme的属性
        for(Scheme scheme:schemes){
            //设置总价格->计算方式:饭盒费*(饭的数量+菜品的数量)+饭的价格*饭的数量+菜品的总价格
           double totalPirce= scheme.getLunchBoxCost()*(scheme.getMealNum()+scheme.getDishNum())+
                    scheme.getMealNum()*scheme.getMealCost()+
                    scheme.getFoodPriceTotal();
            scheme.setTotalPrice(
                    scheme.getLunchBoxCost()*(scheme.getMealNum()+scheme.getDishNum())+
                            scheme.getMealNum()*scheme.getMealCost()+
                            scheme.getFoodPriceTotal()
            );
            //设置打折后的价格->判断满减的方案,来减去金额
            if(scheme.getSalePlan().size()==0){
                scheme.setSaleAfterPrice(scheme.getTotalPrice());
            }else{
                //根据满减的方案和目前的总价格得到一个具体满减
                 Map<Double,Double>saleMap= arriveStage(scheme.getSalePlan(),scheme.getTotalPrice());
                //具体减多少
                scheme.setSaleAfterPrice(scheme.getTotalPrice()-saleMap.get(((TreeMap<Double,Double>)saleMap).firstKey()));
            }
            //设置最终价格->打折后的价格+配送费+红包的方案
            scheme.setFinalPrice(scheme.getSaleAfterPrice()+scheme.getDisPatching());
            Map<Double,Double> usableRedPackMap=arriveStage(scheme.getRedPacket(),scheme.getTotalPrice());
            if(0!=usableRedPackMap.size()){
                scheme.setFinalPrice(scheme.getFinalPrice()+usableRedPackMap.get(((TreeMap<Double,Double>)usableRedPackMap).firstKey()));
            }

        }
        //排序按最优的排序
        return schemes.stream().sorted(
                (s1,s2)->{
                   return Double.compare(s1.getFinalPrice(),s2.getFinalPrice());
                }
        ).collect(Collectors.toList());
    }

    /**
     * 是否取最优
     * @param schemes
     * @return
     */
    public static Scheme firstOptimalScheme(List<Scheme> schemes){
        return optimalScheme(schemes).get(0);
    }

    /**
     * 指定价格，达到第几个阶段
     * 例子:满减    30减15  40减20  给定31价格   得到的是30减15
     * 例子:红包    满30可用5元 满40可用6元   给定31价格  得到 30可用5元
     * @param targetMap
     * @param key
     * @return
     */
    public static Map<Double,Double>  arriveStage(Map<Double,Double> targetMap, double key){
        TreeMap<Double, Double> treeMap = new TreeMap<>();
        for(Double doubleKey:targetMap.keySet()){
            if(key>doubleKey.doubleValue()){
                treeMap.put(doubleKey,targetMap.get(doubleKey));
            }
        }
        if(treeMap.size()==0){
            treeMap.put(0d, 0d);
        }
        return treeMap;
    }
}
