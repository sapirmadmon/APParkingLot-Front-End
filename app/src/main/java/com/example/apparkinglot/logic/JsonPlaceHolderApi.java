package com.example.apparkinglot.logic;

import com.example.apparkinglot.logic.Boundaries.Action.ActionBoundary;
import com.example.apparkinglot.logic.Boundaries.Element.ElementBoundary;
import com.example.apparkinglot.logic.Boundaries.User.NewUserDetailsBoundary;
import com.example.apparkinglot.logic.Boundaries.User.UserBoundary;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @POST("users")
    Call<UserBoundary> CreateNewUser (@Body NewUserDetailsBoundary userDetailsBoundary);

    @GET("users/login/{userDomain}/{userEmail}")
    Call<UserBoundary> Login (@Path("userDomain") String userDomain , @Path("userEmail") String userEmail);

    @PUT("users/{userDomain}/{userEmail}")
    Call<Void> updateUserDetails(@Path("userDomain") String userDomain , @Path("userEmail") String userEmail, @Body UserBoundary userBoundary);

    @POST("elements/{managerDomain}/{managerEmail}")
    Call<ElementBoundary> CreateNewElement(@Path("managerDomain") String managerDomain , @Path("managerEmail") String managerEmail, @Body ElementBoundary elementBoundary);

    @POST("actions")
    Call<Object> invokeAction(@Body ActionBoundary actionBoundary);

    @PUT("elements/{managerDomain}/{managerEmail}/{elementDomain}/{elementId}")
    Call<Void> updateElementDetails(@Path("managerDomain") String managerDomain , @Path("managerEmail") String managerEmail,
             @Path("elementDomain") String elementDomain, @Path("elementId") String elementId, @Body ElementBoundary elementBoundary);


    @GET("elements/{userDomain}/{userEmail}/{elementDomain}/{elementId}")
    Call<ElementBoundary> RetreiveElement(@Path("userDomain") String userDomain , @Path("userEmail") String userEmail,
                                          @Path("elementDomain") String elementDomain, @Path("elementId") String elementId);


}
