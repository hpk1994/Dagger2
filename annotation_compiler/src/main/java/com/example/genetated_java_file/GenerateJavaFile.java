package com.example.genetated_java_file;

import com.example.moduleinfo.BindViewFieldInfo;
import com.example.moduleinfo.OnClickMethodInfo;
import com.example.util.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public class GenerateJavaFile {
    private TypeElement mTypeElement;
    private ArrayList<BindViewFieldInfo> mFields;
    private ArrayList<OnClickMethodInfo> mMethods;
    private Elements mElements;

    public GenerateJavaFile(TypeElement typeElement, Elements elements) {
        mTypeElement = typeElement;
        mElements = elements;
        mFields = new ArrayList<>();
        mMethods = new ArrayList<>();
    }

    public String getFullClassName() {
        return mTypeElement.getQualifiedName().toString();
    }

    public void addField(BindViewFieldInfo field) {
        mFields.add(field);
    }

    public void addMethod(OnClickMethodInfo method) {
        mMethods.add(method);
    }

    public JavaFile generateJavaFile() {
        //generateMethod
        MethodSpec.Builder injectMethod = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "target", Modifier.FINAL)
                .addParameter(TypeUtil.PROVIDER,"provider");

        for(BindViewFieldInfo field : mFields){
            // find views
            injectMethod.addStatement("target.$N = ($T)(provider.findViewAcross(target, $L))",
                    field.getFiledName(),
                    ClassName.get(field.getFieldType()), field.getViewId());
        }

        for(OnClickMethodInfo method :mMethods){
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("target.$N()", method.getMethodName())
                            .build())
                    .build();
            injectMethod.addStatement("View.OnClickListener listener = $L ", listener);
            injectMethod.addStatement("provider.findViewAcross(target, $L).setOnClickListener(listener)", method.getViewId());

        }

        //generaClass
        TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$ViewInject")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.INJET, TypeName.get(mTypeElement.asType())))
                .addMethod(injectMethod.build())
                .build();

        String packgeName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile.builder(packgeName, injectClass).build();
    }
}
