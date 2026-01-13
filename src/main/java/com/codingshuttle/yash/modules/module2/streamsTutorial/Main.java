package com.codingshuttle.yash.modules.module2.streamsTutorial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //******OLD-WAY***********
        /*Walkable obj = new Walk();
        System.out.println(obj.exp(2,true));*/

        //******NEW-WAY***********
//        Walkable obj = (n,flag) -> n*2;
//        System.out.println(obj.exp(4,true));


        List<String> fruits = List.of("Banana","apple","orange","Kiwi","berry");

        Set<Integer> fruitsList =fruits
                .stream()
                .map(fruit -> fruit.length())
                .sorted()
                .collect(Collectors.toSet());

        System.out.println(fruitsList);

//        Stream<String> stream = fruits.stream();
//
//        stream
//                .sorted()
//                .filter(fruit -> fruit.length()>=5)
//                .map(fruit -> fruit.length())
//                .forEach(fruit -> System.out.println(fruit));


    }
}

interface Walkable{
     int exp(int n, boolean flag);

}

//******OLD-WAY***********
/*class Walk implements Walkable{
    public int exp(int n, boolean flag){
        return 2*n;
    }
}*/