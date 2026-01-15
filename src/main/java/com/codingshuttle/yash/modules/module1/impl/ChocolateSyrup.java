package com.codingshuttle.yash.modules.module1.impl;

import com.codingshuttle.yash.modules.module1.Syrup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("chocoSyrup")
public class ChocolateSyrup implements Syrup {
    @Override
    public void getSyrupType() {
        System.out.println("ChocolateSyrup selected");
    }
}
