package com.wallpapers.unsplash.common.data.service;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.wallpapers.unsplash.UnsplashApplication;
import com.wallpapers.unsplash.common.data.BaseOkHttpClient;
import com.wallpapers.unsplash.common.data.api.PhotoApi;
import com.wallpapers.unsplash.common.data.entity.unsplash.LikePhotoResult;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photo;
import com.wallpapers.unsplash.common.data.entity.unsplash.PhotoStats;
import com.wallpapers.unsplash.common.data.entity.unsplash.PhotorResponse;
import com.wallpapers.unsplash.common.data.entity.unsplash.Photos;
import com.wallpapers.unsplash.common.utils.widget.interceptor.AuthInterceptor;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Photo service.
 */

public class PhotoService {
    // widget
    private Call call;
    private CompositeDisposable compositeDisposable;

    public static PhotoService getService() {
        return new PhotoService();
    }

    private OkHttpClient buildClient() {
        return new BaseOkHttpClient().invoke()
                .addInterceptor(new AuthInterceptor())
                .build();
    }

    private PhotoApi buildApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_API_BASE_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((PhotoApi.class));
    }

    private PhotoApi buildApiInfo(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(UnsplashApplication.UNSPLASH_URL)
                .client(client)
                .addConverterFactory(
                        GsonConverterFactory.create(
                                new GsonBuilder()
                                        .setDateFormat(UnsplashApplication.DATE_FORMAT)
                                        .create()))
                .build()
                .create((PhotoApi.class));
    }

    private PhotoApi buildApiRx(OkHttpClient client) {
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
                .create((PhotoApi.class));
    }

    public void requestPhotos(@UnsplashApplication.PageRule int page,
                              @UnsplashApplication.PerPageRule int per_page,
                              String order_by,
                              final OnRequestPhotosListener l) {
        Call<List<Photo>> getPhotos = buildApi(buildClient()).getPhotos(page, per_page, order_by);
        getPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getPhotos;
    }

    public void requestCuratePhotos(@UnsplashApplication.PageRule int page,
                                    @UnsplashApplication.PerPageRule int per_page,
                                    String order_by,
                                    final OnRequestPhotosListener l) {
        Call<List<Photo>> getCuratePhotos = buildApi(buildClient()).getCuratedPhotos(page, per_page, order_by);
        getCuratePhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getCuratePhotos;
    }

    public void requestStats(String id, final OnRequestStatsListener l) {
        Call<PhotoStats> getStats = buildApi(buildClient()).getPhotoStats(id);
        getStats.enqueue(new Callback<PhotoStats>() {
            @Override
            public void onResponse(Call<PhotoStats> call, retrofit2.Response<PhotoStats> response) {
                if (l != null) {
                    l.onRequestStatsSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<PhotoStats> call, Throwable t) {
                if (l != null) {
                    l.onRequestStatsFailed(call, t);
                }
            }
        });
        call = getStats;
    }

    public void requestPhotosInAGivenCategory(int id,
                                              @UnsplashApplication.PageRule int page,
                                              @UnsplashApplication.PerPageRule int per_page,
                                              final OnRequestPhotosListener l) {
        Call<List<Photo>> getPhotosInAGivenCategory = buildApi(buildClient()).getPhotosInAGivenCategory(id, page, per_page);
        getPhotosInAGivenCategory.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getPhotosInAGivenCategory;
    }

    public void setLikeForAPhoto(String id, boolean like, final OnSetLikeListener listener) {
        Call<LikePhotoResult> setLikeForAPhoto = like ?
                buildApi(buildClient()).likeAPhoto(id) : buildApi(buildClient()).unlikeAPhoto(id);
        setLikeForAPhoto.enqueue(new Callback<LikePhotoResult>() {
            @Override
            public void onResponse(Call<LikePhotoResult> call, Response<LikePhotoResult> response) {
                if (listener != null) {
                    listener.onSetLikeSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<LikePhotoResult> call, Throwable t) {
                if (listener != null) {
                    listener.onSetLikeFailed(call, t);
                }
            }
        });
        call = setLikeForAPhoto;
    }

    /*public void requestAPhoto(String id, final PhotoInfoService.OnRequestSinglePhotoListener l) {
        Call<Photo> getAPhoto = buildApiInfo(buildClient()).getPhotoInfo(id);
        getAPhoto.enqueue(new Callback<Photo>() {
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
    }*/

    private Observable<Photo> requestPhoto(String id) {
        return buildApiRx(buildClient()).getPhotoInfoRx(id);
    }

    private Observable<Photo.RelatedPhotos> requestPhotoRelated(String id) {
        return buildApiRx(buildClient()).getPhotoInfoRelated(id);
    }

    public void onZipRequestPhotoInfo(String id, final OnRequestMutilPhotoListener listener) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        Observable.zip(requestPhoto(id), requestPhotoRelated(id), new BiFunction<Photo, Photo.RelatedPhotos, Groups>() {
            @Override
            public Groups apply(Photo photo, Photo.RelatedPhotos photo2) throws Exception {
                return new Groups(photo, photo2.results);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Groups>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Groups groups) {
                        if (listener != null) {
                            listener.onRequestMutilPhotoSuccess(groups);
                        }
                        Log.d("PhotoInfoService", "onNext: " + groups.photoRe.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (listener != null) {
                            listener.onRequestMutilPhotoFailed(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*Observable.mergeDelayError(requestPhoto(id), requestPhotoRelated(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Parcelable>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Parcelable parcelable) {
                        if (listener != null) {
                            if (parcelable instanceof Photo) {
                                listener.onRequestMutilPhotoSuccess((Photo) parcelable);
                            }
                            if (parcelable instanceof Photo.RelatedPhotos) {
                                listener.onRequestMutilPhotoSuccess((Photo.RelatedPhotos) parcelable);
                            }
                        }
                        Log.d("PhotoInfoService", "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (listener != null) {
                            listener.onRequestMutilPhotoFailed(e);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    public static class Groups {
        private Photo photo;
        private List<Photo> photoRe;

        public Groups(Photo photo, List<Photo> photoRe) {
            this.photo = photo;
            this.photoRe = photoRe;
        }

        public Photo getPhoto() {
            return photo;
        }

        public List<Photo> getPhotoRe() {
            return photoRe;
        }
    }

    public void requestUserPhotos(String username,
                                  @UnsplashApplication.PageRule int page,
                                  @UnsplashApplication.PerPageRule int per_page,
                                  String order_by,
                                  final OnRequestPhotosListener l) {
        Call<List<Photo>> getUserPhotos = buildApi(buildClient())
                .getUserPhotos(username, page, per_page, order_by);
        getUserPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getUserPhotos;
    }

    public void requestUserLikes(String username,
                                 @UnsplashApplication.PageRule int page,
                                 @UnsplashApplication.PerPageRule int per_page,
                                 String order_by, final
                                 OnRequestPhotosListener l) {
        Call<List<Photo>> getUserLikes = buildApi(buildClient())
                .getUserLikes(username, page, per_page, order_by);
        getUserLikes.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getUserLikes;
    }

    public void requestCollectionPhotos(int collectionId,
                                        @UnsplashApplication.PageRule int page,
                                        @UnsplashApplication.PerPageRule int per_page,
                                        final OnRequestPhotosListener l) {
        Call<List<Photo>> getCollectionPhotos = buildApi(buildClient())
                .getCollectionPhotos(collectionId, page, per_page);
        getCollectionPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getCollectionPhotos;
    }

    @Nullable
    public List<Photo> requestCollectionPhotos(int collectionId,
                                               @UnsplashApplication.PageRule int page,
                                               @UnsplashApplication.PerPageRule int per_page) {
        Call<List<Photo>> getCollectionPhotos = buildApi(buildClient())
                .getCollectionPhotos(collectionId, page, per_page);
        try {
            Response<List<Photo>> response = getCollectionPhotos.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestCuratedCollectionPhotos(int collectionId,
                                               @UnsplashApplication.PageRule int page,
                                               @UnsplashApplication.PerPageRule int per_page,
                                               final OnRequestPhotosListener l) {
        Call<List<Photo>> getCuratedCollectionPhotos = buildApi(buildClient())
                .getCuratedCollectionPhotos(collectionId, page, per_page);
        getCuratedCollectionPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getCuratedCollectionPhotos;
    }

    @Nullable
    public List<Photo> requestCurateCollectionPhotos(int collectionId,
                                                     @UnsplashApplication.PageRule int page,
                                                     @UnsplashApplication.PerPageRule int per_page) {
        Call<List<Photo>> getCuratedCollectionPhotos = buildApi(buildClient())
                .getCuratedCollectionPhotos(collectionId, page, per_page);
        try {
            Response<List<Photo>> response = getCuratedCollectionPhotos.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void requestRandomPhotos(Integer categoryId,
                                    Boolean featured, String username, String query, String orientation,
                                    final OnRequestPhotosListener l) {
        Call<List<Photo>> getRandomPhotos = buildApi(buildClient()).getRandomPhotos(
                categoryId, featured,
                username, query,
                orientation, UnsplashApplication.DEFAULT_PER_PAGE);
        getRandomPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (l != null) {
                    l.onRequestPhotosSuccess(call, response);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                if (l != null) {
                    l.onRequestPhotosFailed(call, t);
                }
            }
        });
        call = getRandomPhotos;
    }

    /**
     * @param query
     * @param page
     * @param per_pag
     * @param listener
     * @method use {@link com.wallpapers.unsplash.main.presenter.widget.PhotosImplementor#requestPhotoByTag}
     */
    public void requestPhotoByQuery(String query, @UnsplashApplication.PageRule int page,
                                    @UnsplashApplication.PerPageRule int per_pag, final OnRequestPhotoByQueryListener listener) {
        Call<Photos> resultPhoto = buildApiInfo(buildClient()).getPhotoByTag(query, per_pag, page);
        resultPhoto.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                if (listener != null) {
                    listener.onRequestPhotosSuccesss(call, response);
                }
            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {
                if (listener != null) {
                    listener.onRequestPhotosFailedd(call, t);
                }
            }
        });
        call = resultPhoto;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    // interface.

    public interface OnRequestMutilPhotoListener {
        void onRequestMutilPhotoSuccess(Groups groups);

        void onRequestMutilPhotoFailed(Throwable t);
    }

    public interface OnRequestPhotosListener {
        void onRequestPhotosSuccess(Call<List<Photo>> call, retrofit2.Response<List<Photo>> response);

        void onRequestPhotosFailed(Call<List<Photo>> call, Throwable t);

    }

    public interface OnRequestStatsListener {
        void onRequestStatsSuccess(Call<PhotoStats> call, retrofit2.Response<PhotoStats> response);

        void onRequestStatsFailed(Call<PhotoStats> call, Throwable t);
    }

    public interface OnSetLikeListener {
        void onSetLikeSuccess(Call<LikePhotoResult> call, retrofit2.Response<LikePhotoResult> response);

        void onSetLikeFailed(Call<LikePhotoResult> call, Throwable t);
    }

    public interface OnRequestPhotosByQueryListener {
        void onRequestPhotosSuccess(Call<PhotorResponse> call, retrofit2.Response<PhotorResponse> response);

        void onRequestPhotosFailed(Call<PhotorResponse> call, Throwable t);
    }

    public interface OnRequestPhotoByQueryListener {
        void onRequestPhotosSuccesss(Call<Photos> call, retrofit2.Response<Photos> response);

        void onRequestPhotosFailedd(Call<Photos> call, Throwable t);
    }
}
