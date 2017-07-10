package com.example.moduleinfo;

import com.example.annotation_declare.DIBindOnClickEvent;
import com.example.annotation_declare.DIBindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public class OnClickMethodInfo {
    private ExecutableElement executableElement;
    private Name methodName;
    private int viewId;

    public OnClickMethodInfo(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(String.format("Only Method can be annotated with @%s", DIBindOnClickEvent.class.getSimpleName()));
        }

        executableElement = (ExecutableElement) element;
        viewId = executableElement.getAnnotation(DIBindOnClickEvent.class).viewId();
        if (viewId < 0) {
            throw new IllegalArgumentException(String.format("Must set valid id for @%s",
                    DIBindOnClickEvent.class.getSimpleName()));
        }
        methodName = executableElement.getSimpleName();
    }

    public Name getMethodName() {
        return methodName;
    }

    public int getViewId() {
        return viewId;
    }
}
