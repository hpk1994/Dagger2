package com.example.moduleinfo;

import com.example.annotation_declare.DIBindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public class BindViewFieldInfo {
    private VariableElement mVariableElement;
    private int viewId;

    public BindViewFieldInfo(Element variableElement) {
        if (variableElement.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(String.format("Only field can be annotated with @%s", DIBindView.class.getSimpleName()));
        }

        mVariableElement = (VariableElement) variableElement;
        DIBindView diBindView = mVariableElement.getAnnotation(DIBindView.class);
        viewId = diBindView.viewId();
        if (viewId < 0) {
            throw new IllegalArgumentException(String.format("viewId() in %s for field %s is not valid !", DIBindView.class.getSimpleName(),
                    mVariableElement.getSimpleName()));
        }
    }

    /**
     * 获取变量名
     */
    public Name getFiledName() {
        return mVariableElement.getSimpleName();
    }

    /**
     * 获取注解viewId值
     */
    public int getViewId() {
        return viewId;
    }

    /**
     * 获取变量类型
     */
    public TypeMirror getFieldType() {
        return mVariableElement.asType();
    }
}
