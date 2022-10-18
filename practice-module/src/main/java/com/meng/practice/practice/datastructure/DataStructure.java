package com.meng.practice.practice.datastructure;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataStructure {

    public void baseStructure() {


        //1 字符串/数组初始化
        String[] str01 = new String[] {"aa", "bb"};
        String[] str02 = {"aa", "bb"};
        String[] str04 = {};
        String[][] str03 = {
                {"aa", "bb"},{"cc", "dd"}
        };


        //2 数字型array数组初始化
        int[] int01 = {};
        int[] int02 = {1, 2};
        int[] int03 = new int[]{1, 2};
        int[][] int04 = {
                {1, 2},{1, 2}
        };
        int[] int05 = Arrays.copyOf(int02, 2);
        int search01 = Arrays.binarySearch(int02, 1);
        int aa = Arrays.stream(int02).filter(t -> t < 3).sum();
        java.util.List<int[]> list01 = new ArrayList<>();
        list01.add(int01);
        int[][] int2d = list01.toArray(new int[0][0]);

        //3 字符串数字，list的转化
        java.util.List<Integer> listInt01 = Arrays.asList(1, 2, 3);
        int[] int06 = listInt01.stream().mapToInt(t -> t).toArray();
        Integer[] int07 = Arrays.stream(int06).boxed().toArray(Integer[]::new);

        java.util.List<Integer> list02 = Arrays.asList(int07);
        java.util.List<String> list03 = Arrays.asList("111", "222");
        java.util.List<String> list04 = new ArrayList<String>(){
            {add("111");
            add("222");}
        };
        list04.removeIf(str -> {
            str.equals("aaa");
            return true;
        });

        Collections.sort(list03);
        Collections.sort(list03, Collections.reverseOrder());
        Collections.sort(list03, String::compareTo);
        list03.sort(String::compareTo);
        Collections.sort(list03, (t1, t2) -> {
            return t1.compareTo(t2);
        });
        //4 数组和集合的转化
        String[] input01 = {"aa", "bb"};
        java.util.List<String> list10 = Arrays.asList(input01);
        String[] input02 = list10.toArray(new String[0]);

        //5 字符串和map的转化
        Map<String, Integer> map01 = new HashMap<String, Integer>(){
            {put("a", 1);
            put("b", 2);}
        };
        Map<Character, Integer> mapChar = new HashMap<Character, Integer>(){
            {put('a', 1);
             put('b', 2);}
        };

        //6 栈的创建和应用
        Stack<Integer> stackI = new Stack<>();
        Stack<String> stackS = new Stack<>();
        stackI.push(12);
        stackI.push(13);
        stackS.push("aa");
        stackS.push("bb");
        int outPeek = stackI.peek(); // 取数，数据还在stack中
        int outPop = stackI.pop(); // 取数，数据不再stack中
        Enumeration items = stackI.elements();
        while (items.hasMoreElements()) {
            System.out.println(items.nextElement());
        }

        //7队列的创建和应用
        Queue<String> queueStr = new LinkedList<>();
        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        PriorityQueue<String> priorityQueue1 = new PriorityQueue<>(Comparator.comparingInt(String::length));
        PriorityQueue<String> priorityQueue2 = new PriorityQueue<>((t1, t2) -> {
            return t1.compareTo(t2);
        });
        Queue<Integer> queueInt = new LinkedList<>();
        PriorityQueue<Integer> priorityQueue3 = new PriorityQueue<>(new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        queueStr.offer("aa");
        String strPeek = queueStr.peek(); //取数，数据还在queue
        String strPoll = queueStr.poll(); //取数，数据不再queue
        for (String item: queueStr) {
            System.out.println(item);
        }

        //8 字符串和字符数组转化
        String str = "abck";
        StringBuffer sb = new StringBuffer();
        char[] chars = str.toCharArray();
        sb.append(chars);
        String charToStr = sb.toString();
        String charToStr2 = String.valueOf(chars);
        String charToStr3 = String.copyValueOf(chars);
        String ccharToStr4 = new String(chars);
        char[] chars1 = Character.toChars('A');

        //9 数组,集合和set转化
        String[] arrStr = {"aa", "bb", "cc"};
        java.util.List<String> list101 = Arrays.asList(arrStr);
        Set<String> set01 = new HashSet<>(list101);
        Set<String> set02 = new HashSet<>(Arrays.asList(arrStr));
        String[] arrStr01 = set01.toArray(new String[0]);
        Object[] arrStr02 = set01.toArray();

        // 有序集合  floor：小于某个数对最大数； ceiling：大于某个数对最小数;没有则返回null
        TreeSet<Long> treeSet = new TreeSet<>();
        treeSet.ceiling(21L);
        treeSet.floor(21L);

        TreeMap treeMap = new TreeMap();// 功能和treeSet类似

        //10 将list和arr终端元素连接起来
        String joins = String.join("-", arrStr);
    }

    public void sortData() {
        //1 对Array的排序
        int[] sortInt01 = {1, 3, 2};
        Arrays.sort(sortInt01);
        Integer[] intBox = new Integer[]{1, 3, 2};
        Arrays.sort(intBox, (t1, t2) -> t2 - t1); //降序需要使用包装器，可以使用表达式

        //2 对集合Collection的排序
        java.util.List<String> sortList01 = Arrays.asList("11", "ee", "dd");
        Collections.sort(sortList01);
        Collections.sort(sortList01, Collections.reverseOrder());
        sortList01.sort(Collections.reverseOrder());
        sortList01.sort((t1, t2) -> {
            return t1.compareTo(t2);
        });

        //3 使用lambda表达式,遍历，筛选，过滤，计数
        sortList01.forEach(str -> System.out.println(str));
        long p = sortList01.stream().filter(n -> n.length() < 3).count();
        java.util.List list = sortList01.stream().filter(str -> str.length() < 3).collect(Collectors.toList());

        //4 lambda转数组
        Map<String, Integer> map = new HashMap<String, Integer>(){
            {put("aa", 1); put("bb", 2);}
        };
        map.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });

        //5 二维数组排序
        int[][] inputInt = {
                {1, 2, 3}, {1, 3, 2}
        };
        String[][] str = {
                {"aa", "bb"}, {"dd", "cc"}
        };
        Arrays.sort(inputInt, (o1, o2) -> {
            if (o1[0] == o2[0]) return o1[1] - o2[1];
            return o1[0] - o2[0];
        });

        //6 对list集合数据对操作，部分取指等等
        java.util.List<Record> records = new ArrayList<Record>(){
            {
                add(new Record("id1", "name1", 1, 3));
                add(new Record("id2", "name2", 2, 4));
                add(new Record("id3", "name3", 3, 5));
            }
        };
        java.util.List<String> listRet = records.stream().filter(item -> item.weight > 1).distinct().sorted()
                .map(item -> item.names).collect(Collectors.toList());
        java.util.List<String> listRet2 = records.stream().map(item -> item.strId).collect(Collectors.toCollection(ArrayList::new));
        Integer[] integers = records.stream().sorted().map(item -> item.weight).toArray(Integer[]::new);
        Integer[] integers1 = records.stream().map(item -> item.height).collect(Collectors.toList()).toArray(new Integer[0]);
        int[] ints = records.stream().sorted().map(item -> item.height).mapToInt(t -> t).toArray();
        int av1 = Arrays.stream(ints).sum();

        //7 集合转map
        Map<String, String> map2 = records.stream().collect(Collectors.toMap(t -> t.strId, t -> t.names));
        Map<String, Record> map3 = records.stream().collect(Collectors.toMap(t -> t.strId, t ->t));
        Map<String, String> map4 = records.stream().collect(Collectors.toMap(t -> t.strId, t -> t.names, (n1, n2) -> n1 + n2));
//        TreeMap<String, String> map5 = records.stream().collect(Collectors.toMap(t -> t, t2 -> t2));
        map2.merge("aa", "bb", (a, b) -> a + b);// 如果添加的时候，有key aa，则把aa的值和bb的值相加

        //8 集合转set
        Set<Record> set01 = new HashSet<>(records);
        TreeSet<String> set02 = records.stream().map(item -> item.names).collect(Collectors.toCollection(TreeSet::new));

        //9 treeMap,它的其他排序，需要先转成list，然后在使用Collections对list进行排序
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        TreeMap<Integer, Integer> treeSet2 = new TreeMap<>((t1, t2) -> {
            return t1 - t2;
        });
        treeMap.forEach((k, v) -> {
            System.out.println(k + "  " + v);
        });
        TreeMap<String, String> treeMap1 = new TreeMap<>();
        Iterator<Map.Entry<String, String>> itmap = treeMap1.entrySet().iterator();
        while (itmap.hasNext()) {
            Map.Entry<String, String> entry = itmap.next();
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
        java.util.List<String> list1 = treeMap1.entrySet().stream().map(t -> t.getKey()).sorted(Collections.reverseOrder()).collect(Collectors.toList());

    }

    //s: 输入对数字  x：原数据对进制   y：需要转化对进制
    private static String transForm(String s, int x, int y) {
        return new java.math.BigInteger(s, x).toString(y);
    }

    private void otherUtil() {
        //去除字符串中对0
        String aa = "0000234";
        int b = Integer.parseInt(aa);
        String s1 = aa.replace("^0*", "");
        String s2 = aa.replaceAll("^(0+)", "");
        String s3 = aa.replaceFirst("^0*", "");

        //list 对比较
        java.util.List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        boolean flag = list1.equals(list2);
    }

    public void treeOp() {



    }

    class Record {
        public String strId;
        public String names;
        int height;
        int weight;
        Record(String strId, String names, int h, int w) {
            this.strId = strId;
            this.names = names;
            this.height = h;
            this.weight = w;
        }
    }



}
