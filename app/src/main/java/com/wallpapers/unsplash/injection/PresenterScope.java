package com.wallpapers.unsplash.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by hoanghiep on 1/17/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PresenterScope {
}
