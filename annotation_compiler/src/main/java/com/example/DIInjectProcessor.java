package com.example;

import com.example.annotation_declare.DIBindOnClickEvent;
import com.example.annotation_declare.DIBindView;
import com.example.genetated_java_file.GenerateJavaFile;
import com.example.moduleinfo.BindViewFieldInfo;
import com.example.moduleinfo.OnClickMethodInfo;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by pingkun.huang on 2017/7/5.
 */
@AutoService(Processor.class)
public class DIInjectProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;
    private Elements elements;
    private Map<String, GenerateJavaFile> generateJavaFileMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elements = processingEnvironment.getElementUtils();
        generateJavaFileMap = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(DIBindView.class.getCanonicalName());
        types.add(DIBindOnClickEvent.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {
        return super.getCompletions(element, annotationMirror, executableElement, s);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        processBindView(roundEnvironment);
        processOnClick(roundEnvironment);

        for (GenerateJavaFile generateJavaFile : generateJavaFileMap.values()) {
            try {
                generateJavaFile.generateJavaFile().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void processBindView(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(DIBindView.class)) {
            BindViewFieldInfo bindViewFieldInfo = new BindViewFieldInfo(element);
            getGenerateJavaFile(element).addField(bindViewFieldInfo);
        }

    }

    private void processOnClick(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(DIBindOnClickEvent.class)) {
            OnClickMethodInfo onClickMethodInfo = new OnClickMethodInfo(element);
            getGenerateJavaFile(element).addMethod(onClickMethodInfo);
        }
    }

    private GenerateJavaFile getGenerateJavaFile(Element element) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String fullName = typeElement.getQualifiedName().toString();
        GenerateJavaFile generateJavaFile = generateJavaFileMap.get(fullName);
        if (generateJavaFile == null) {
            generateJavaFile = new GenerateJavaFile(typeElement, elements);
            generateJavaFileMap.put(fullName, generateJavaFile);
        }
        return generateJavaFile;
    }
}
