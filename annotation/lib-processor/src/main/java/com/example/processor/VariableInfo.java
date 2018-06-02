package com.example.processor;

import javax.lang.model.element.VariableElement;

/**
 * Created by zhaobing04 on 2018/5/28.
 */

public class VariableInfo {

    //被注解的id值
    int viewId;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public void setVariableElement(VariableElement variableElement) {
        this.variableElement = variableElement;
    }


    //记录被注解的view信息
    VariableElement variableElement;

}
