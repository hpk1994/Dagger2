package com.example.annotation_inject.inject;

import com.example.annotation_inject.SearchMethod.SearchViewMethodProvider;

/**
 * Created by pingkun.huang on 2017/7/7.
 */

public interface InjectInterface<T> {
    void inject(T target, SearchViewMethodProvider provider);
}
