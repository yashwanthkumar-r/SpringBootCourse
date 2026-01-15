package com.codingshuttle.yash.modules.module1.impl;

import com.codingshuttle.yash.modules.module1.Syrup;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StrawberrySyrup implements Syrup {
    @Override
    public void getSyrupType() {
        System.out.println("StrawberrySyrup selected");
    }
}
