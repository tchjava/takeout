package com.weng.takeout.util;

import java.util.*;
import java.util.stream.Collectors;

public class TakeoutUtil {
    /**
     * 指定集合，复制多份
     */
    public static List<List<String>> getmoreList(List<String> list, int n){
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i <n; i++) {
            lists.add(list);
        }
        return lists;
    }
    /**
     * 得到不重复的排列组合的集合
     *
     * @param list
     * @return
     */
    public static Collection<List<Double>> noRepetition(List<String> list) {
        Map<Integer, List<Double>> map = new HashMap<>();
        //放一个hashcode的List
        List<Integer> hashList = new ArrayList<>();
        for (String s : list) {
            List<Double> target = stringListToDoubleListAndSort(s);
                int hashCodeKey=getCustomHashCode(target);
                if(!hashList.contains(hashCodeKey)){
                    hashList.add(hashCodeKey);
                    map.put(target.hashCode(), target);
                }
        }
        return map.values();
    }
    /**
     * 得到一个特殊的hashcode
     */
    public static int getCustomHashCode(List<Double> list){
        int result=0;
        for(Double doubleKey:list){
            result+=doubleKey.hashCode()+list.size();
        }
        return result;
    }
    /**
     * 比较两个集合内容是否相同
     */
    public static boolean eqList(List<Double> list1, List<Double> list2) {
        if (list1 == null || list2 == null) {
            return false;
        }
        for (Double i : list1) {
            if (!list2.contains(i)) {
                return false;
            }
        }
        for (Double i : list2) {
            if (!list1.contains(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将字符串转换成排列好的集合
     */
    public static List<Double> stringListToDoubleListAndSort(String target) {
        try {
            return Arrays.stream(target.split(","))
                    .map(s -> Double.valueOf(s)).sorted().collect(Collectors.toList());
        } catch (Exception e) {
            throw new NumberFormatException("心里没点B数吗?");
        }

    }

    /**
     * 未知维度的笛卡儿积
     *
     * @param lists
     * @return
     */
    public static List<String> unknownDimemsional(List<List<String>> lists) {
        if (lists.size() == 1) {
            return lists.get(0);
        }
        List<String> list = cartesian(lists.get(0), lists.get(1));
        if (2 == lists.size()) {
            return list;
        }
        int index = 2;
        while (true) {
            if (index == lists.size() - 1) {
                return cartesian(list, lists.get(index));
            } else {
                list = cartesian(list, lists.get(index));
                index++;
            }
        }
    }

    /**
     * 笛卡儿积
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> cartesian(List<String> list1, List<String> list2) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                String content = list1.get(i);
                String secondString = list2.get(j);
                list.add(content + "," + secondString);
            }
        }
        return list;
    }

}
