package com.wallpaper.unsplash.common.data.service;

import com.google.gson.GsonBuilder;
import com.wallpaper.unsplash.UnsplashApplication;
import com.wallpaper.unsplash.common.data.BaseOkHttpClient;
import com.wallpaper.unsplash.common.data.api.PhotoInfoApi;
import com.wallpaper.unsplash.common.data.entity.unsplash.Photo;
import com.wallpaper.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Photo info service.
 */

public class PhotoInfoService {


    private CompositeDisposable compositeDisposable;

    public static PhotoInfoService getService() {
        return new PhotoInfoService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private PhotoInfoApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((PhotoInfoApi.class));
    }

    private PhotoInfoApi buildApiRx(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((PhotoInfoApi.class));
    }

    /*public void requestAPhoto(String id, final OnRequestSinglePhotoListener l) {
        Call<Photo> getPhotoInfo = buildApi(buildClient()).getPhotoInfo(id);
        getPhotoInfo.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (l != null) {
                    l.onRequestSinglePhotoSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                if (l != null) {
                    l.onRequestSinglePhotoFailed(call, t);
                }
            }
        });
        call = getPhotoInfo;
    }*/

    private Observable<Photo> requestPhoto(String id) {
        return buildApiRx(buildClient()).getPhotoInfoRx(id);
    }

    private Observable<Photo.RelatedPhotos> requestPhotoRelated(String id) {
        return buildApiRx(buildClient()).getPhotoInfoRelated(id);
    }

    public void onZipRequestPhotoInfo(String id, final PhotoService.OnRequestMutilPhotoListener listener) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        Observable.zip(requestPhoto(id), requestPhotoRelated(id), new BiFunction<Photo, Photo.RelatedPhotos, PhotoService.Groups>() {
            @Override
            public PhotoService.Groups apply(Photo photo, Photo.RelatedPhotos photo2) throws Exception {
                return new PhotoService.Groups(photo, photo2.results);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhotoService.Groups>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(PhotoService.Groups groups) {
                        if (listener != null) {
                            listener.onRequestMutilPhotoSuccess(groups);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onRequestMutilPhotoFailed(e);
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    // interface.

    /*public interface OnRequestSinglePhotoListener {
        void onRequestSinglePhotoSuccess(Call<Photo> call, retrofit2.Response<Photo> response);

        void onRequestSinglePhotoFailed(Call<Photo> call, Throwable t);
    }*/
}
