package com.codingshuttle.yash.modules.module1.impl;

import com.codingshuttle.yash.modules.module1.Frosting;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ChocolateFrosting implements Frosting {
    @Override
    public void getFrostingType() {
        System.out.println("ChocolateFrosting selected");
    }
}
