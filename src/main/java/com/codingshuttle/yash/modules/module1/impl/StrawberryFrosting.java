package com.codingshuttle.yash.modules.module1.impl;

import com.codingshuttle.yash.modules.module1.Frosting;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("strawberryFrost")
public class StrawberryFrosting implements Frosting {
    @Override
    public void getFrostingType() {
        System.out.println("StrawberryFrosting selected");
    }
}
