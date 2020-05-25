package com.example.apparkinglot.logic;

import com.example.apparkinglot.logic.Boundaries.User.NewUserDetailsBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @POST("users")
    Call<UserBoundary> CreateNewUser (@Body NewUserDetailsBoundary userDetailsBoundary);

    @GET("users/login/{userDomain}/{userEmail}")
    Call<UserBoundary> Login (@Path("userDomain") String userDomain , @Path("userEmail") String userEmail);
}
