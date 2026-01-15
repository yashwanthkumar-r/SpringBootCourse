package com.codingshuttle.yash.modules.module1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CakeBaker {

    private Frosting frosting;
    private Syrup syrup;

    @Autowired
    private Map<String, Frosting> frostingMap = new HashMap<>();

    @Autowired
    private Map<String, Syrup> syrupMap = new HashMap<>();


    public CakeBaker(@Qualifier("strawberryFrost") Frosting frosting, Syrup syrup) {
        this.frosting = frosting;
        this.syrup = syrup;
    }

    public void bakeCake() {

        System.out.println("Please select cake type to bake");

        frosting.getFrostingType();
        syrup.getSyrupType();

        System.out.println("Cake is getting baked...");


        System.out.println("\nNow lets try running all impl using MAP");

        for (var frost : frostingMap.entrySet()) {
            frost.getValue().getFrostingType();
        }

        for (var syrup : syrupMap.entrySet()) {
            syrup.getValue().getSyrupType();
        }

    }
}
