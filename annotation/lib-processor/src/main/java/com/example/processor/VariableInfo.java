package com.example.processor;

import javax.lang.model.element.VariableElement;

/**
 * Created by zhaobing04 on 2018/5/28.
 */

public class VariableInfo {

    //��ע���idֵ
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


    //��¼��ע���view��Ϣ
    VariableElement variableElement;

}
