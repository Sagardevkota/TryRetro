package com.example.tryretro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderAPI {

     @GET("posts")
    Call<List<posts>> getPosts();

     @GET("comments")  @Headers({"API_key:12afhk342kja@"})
     Call<List<comments>> getcomments(
             @Query("postId") Integer postid


     );





}
