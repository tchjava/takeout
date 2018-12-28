package com.weng.takeout.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: 计算系统中ean
 * @Author: wengzhongjie
 * @CreateDate: 2018-12-28 9:22
 */
@Data
public class Scheme {
    /**
     * 主键
     */
    private String id;
    /**
     * 餐盒费
     */
    private double lunchBoxCost;
    /**
     * 米饭费用
     */
    private double mealCost;
    /**
     * 米饭的数量
     */
    private double mealNum;
    /**
     * 方案，就是菜品价格的集合
     */
    private List<Double> scheme;
    /**
     * 菜品总的价格
     */
    public double getFoodPriceTotal(){
        double doubleSum=0D;
        for(Double doubleKey:this.scheme){
            doubleSum+=doubleKey.doubleValue();
        }
        return doubleSum;
    }
    /**
     * 菜品价格数量
     */
    private int dishNum;
    /**
     * 总价格
     */
    private double totalPrice;
    /**
     * 打折后的价格
     */
    private double saleAfterPrice;
    /**
     * 满减的方案
     */
    private TreeMap<Double, Double> salePlan;
    /**
     * 红包
     */
    private Map<Double, Double> redPacket;
    /**
     * 配送费
     */
    private double disPatching;
    /**
     * 最终付费价格
     */
    private double finalPrice;

}
