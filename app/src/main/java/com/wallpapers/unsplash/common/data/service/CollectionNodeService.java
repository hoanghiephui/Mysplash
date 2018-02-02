package com.wallpapers.unsplash.common.data.service;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.BaseOkHttpClient;
import com.wallpapers.unsplash.common.data.api.CollectionNodeApi;
import com.wallpapers.unsplash.common.data.entity.unsplash.Collection;
import com.wallpapers.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wallpapers.unsplash.UnsplashApplication.DATE_FORMAT;
import static com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_NODE_API_URL;
import static com.wallpapers.unsplash.UnsplashApplication.UNSPLASH_URL;

/**
 * Created by hoanghiep on 1/15/18.
 */

public class CollectionNodeService {
    private Call call;

    static CollectionNodeService getService() {
        return TextUtils.isEmpty(UNSPLASH_NODE_API_URL) ? null : new CollectionNodeService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private CollectionNodeApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UNSPLASH_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(DATE_FORMAT)
                                        .create()))
                .build()
                .create((CollectionNodeApi.class));
    }

    void requestAllCollections(@UnsplashApplication.PageRule int page,
                               @UnsplashApplication.PerPageRule int per_page,
                               final CollectionService.OnRequestCollectionsListener l) {
        Call<List<Collection>> getAllCollections = buildApi(buildClient()).getAllCollections(page, per_page);
        getAllCollections.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (l != null) {
                    l.onRequestCollectionsSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                if (l != null) {
                    l.onRequestCollectionsFailed(call, t);
                }
            }
        });
        call = getAllCollections;
    }

    void requestCuratedCollections(@UnsplashApplication.PageRule int page,
                                   @UnsplashApplication.PerPageRule int per_page,
                                   final CollectionService.OnRequestCollectionsListener l) {
        Call<List<Collection>> getCuratedCollections = buildApi(buildClient()).getCuratedCollections(page, per_page);
        getCuratedCollections.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (l != null) {
                    l.onRequestCollectionsSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                if (l != null) {
                    l.onRequestCollectionsFailed(call, t);
                }
            }
        });
        call = getCuratedCollections;
    }

    void requestFeaturedCollections(@UnsplashApplication.PageRule int page,
                                    @UnsplashApplication.PerPageRule int per_page,
                                    final CollectionService.OnRequestCollectionsListener l) {
        Call<List<Collection>> getFeaturedCollections = buildApi(buildClient()).getFeaturedCollections(page, per_page);
        getFeaturedCollections.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (l != null) {
                    l.onRequestCollectionsSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                if (l != null) {
                    l.onRequestCollectionsFailed(call, t);
                }
            }
        });
        call = getFeaturedCollections;
    }

    void requestACollections(String id,
                             final CollectionService.OnRequestSingleCollectionListener l) {
        Call<Collection> getACollection = buildApi(buildClient()).getACollection(id);
        getACollection.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (l != null) {
                    l.onRequestSingleCollectionSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                if (l != null) {
                    l.onRequestSingleCollectionFailed(call, t);
                }
            }
        });
        call = getACollection;
    }

    void requestACuratedCollections(String id,
                                    final CollectionService.OnRequestSingleCollectionListener l) {
        Call<Collection> getACuratedCollection = buildApi(buildClient()).getACuratedCollection(id);
        getACuratedCollection.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (l != null) {
                    l.onRequestSingleCollectionSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                if (l != null) {
                    l.onRequestSingleCollectionFailed(call, t);
                }
            }
        });
        call = getACuratedCollection;
    }

    void requestUserCollections(String username,
                                @UnsplashApplication.PageRule int page,
                                @UnsplashApplication.PerPageRule int per_page,
                                final CollectionService.OnRequestCollectionsListener l) {
        Call<List<Collection>> getUserCollections = buildApi(buildClient())
                .getUserCollections(username, page, per_page);
        getUserCollections.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(
                    Call<List<Collection>> call,
                    Response<List<Collection>> response) {
                if (l != null) {
                    l.onRequestCollectionsSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                if (l != null) {
                    l.onRequestCollectionsFailed(call, t);
                }
            }
        });
        call = getUserCollections;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}
