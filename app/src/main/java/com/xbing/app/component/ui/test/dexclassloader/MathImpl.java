package com.xbing.app.component.ui.test.dexclassloader;

public class MathImpl implements IMath {

    @Override
    public String divide() {
        return "除数为0，有异常了";
    }
}
