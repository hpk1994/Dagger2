package com.example.annotation_inject.SearchMethod;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public class ActivityMethodProvider implements SearchViewMethodProvider {
    @Override
    public Context getContext(Object target) {
        if (target instanceof Activity) {
            return (Activity) target;
        } else {
            throw new IllegalArgumentException("target must be instance of Activity");
        }
    }

    @Override
    public View findViewAcross(Object searchTarget, int viewId) {
        if (searchTarget instanceof Activity) {
            if (viewId < 0) {
                throw new RuntimeException("you must assign a meaningful int value");
            } else {
                return ((Activity) searchTarget).findViewById(viewId);
            }
        } else {
            throw new IllegalArgumentException("target must be instance of Activity");
        }
    }
}
