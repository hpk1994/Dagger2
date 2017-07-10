package com.example.annotation_inject.inject;

import android.app.Activity;
import android.view.View;

import com.example.annotation_inject.SearchMethod.ActivityMethodProvider;
import com.example.annotation_inject.SearchMethod.SearchViewMethodProvider;
import com.example.annotation_inject.SearchMethod.ViewMethodProvider;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public class ViewInject {
    private static final SearchViewMethodProvider searchViewAcrossActivity = new ActivityMethodProvider();
    private static final SearchViewMethodProvider searchViewAcrossView = new ViewMethodProvider();

    private static final Map<String, InjectInterface> injectInterfaceMap = new LinkedHashMap<>();

    public static void inject(Activity activity) {
        inject(activity, searchViewAcrossActivity);
    }

    public static void inject(View view) {
        inject(view, searchViewAcrossView);
    }

    public static void inject(Object target, SearchViewMethodProvider searchViewMethodProvider) {
        String targetClassName = target.getClass().getName();
        String generatedClassName = targetClassName + "$$ViewInject";
        InjectInterface injectInterface = injectInterfaceMap.get(generatedClassName);
        try {
            if (injectInterface == null) {
                injectInterface = (InjectInterface) Class.forName(generatedClassName).newInstance();
                injectInterfaceMap.put(generatedClassName, injectInterface);
            }
            injectInterface.inject(target, searchViewMethodProvider);
        } catch (Exception e) {

        }
    }
}
