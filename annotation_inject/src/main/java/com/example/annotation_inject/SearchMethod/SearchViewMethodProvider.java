package com.example.annotation_inject.SearchMethod;

import android.content.Context;
import android.view.View;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public interface SearchViewMethodProvider {
    Context getContext(Object target);

    View findViewAcross(Object searchTarget,int viewId);
}
