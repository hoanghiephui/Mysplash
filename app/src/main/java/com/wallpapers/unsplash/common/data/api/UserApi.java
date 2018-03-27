package com.wallpapers.unsplash.common.data.api;

import com.wallpapers.unsplash.common.data.entity.unsplash.Me;
import com.wallpapers.unsplash.common.data.entity.unsplash.User;
import com.wallpapers.unsplash.common.data.entity.unsplash.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * User api.
 */

public interface UserApi {

    @GET("users/{username}")
    Call<User> getUserProfile(@Path("username") String username,
                              @Query("w") int w,
                              @Query("h") int h);

    @GET("me")
    Call<Me> getMeProfile();

    @PUT("me")
    Call<Me> updateMeProfile(@Query("username") String username,
                             @Query("first_name") String first_name,
                             @Query("last_name") String last_name,
                             @Query("email") String email,
                             @Query("url") String url,
                             @Query("location") String location,
                             @Query("bio") String bio);

    @GET("users/{username}/following")
    Call<List<User>> getFolloweing(@Path("username") String username,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page);

    @GET("users/{username}/followers")
    Call<List<User>> getFollowers(@Path("username") String username,
                                  @Query("page") int page,
                                  @Query("per_page") int per_page);

    /**
     * @param query
     * @param perPage
     * @param page
     * @return {@link Users}
     * @method {@link com.wallpapers.unsplash.common.data.service.UserService#requestUserByQuery}
     */
    @GET("napi/search/users")
    Call<Users> getListUserByQuery(@Query("query") String query,
                                   @Query("per_page") int perPage,
                                   @Query("page") int page);
}