package com.wallpapers.unsplash.exprore;

import com.wallpapers.unsplash.UnsplashFramework;
import com.wallpapers.unsplash.api.ApiRootEndpoints;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photos;

import io.reactivex.Observable;

/**
 * Created by hoanghiep on 1/17/18.
 */

public class ExproreApi {
    private ApiRootEndpoints apiRootEndpoints;

    /**
     * Lazily evaluates an instance of {@link ApiRootEndpoints}.
     */
    private ApiRootEndpoints getApiInstance() {
        if (apiRootEndpoints == null) {
            apiRootEndpoints = UnsplashFramework.getRetrofitApiInstance().
                    create(ApiRootEndpoints.class);
        }
        return apiRootEndpoints;
    }

    private ApiRootEndpoints getApiRootInstance() {
        if (apiRootEndpoints == null) {
            apiRootEndpoints = UnsplashFramework.getRetrofitApiRootInstance().
                    create(ApiRootEndpoints.class);
        }
        return apiRootEndpoints;
    }

    public Observable<Photos> getPhotoByTag(String query,
                                            int perPage,
                                            int page) {
        return getApiRootInstance().getPhotoByTag(query, perPage, page);
    }
}
