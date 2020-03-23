package com.example.tryretro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import javax.xml.transform.Templates;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
    JsonPlaceHolderAPI jsonPlaceHolderAPI;

    TextView textResults;
    private String base_url="https://jsonplaceholder.typicode.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResults=findViewById(R.id.textResult);
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient=new OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
     jsonPlaceHolderAPI=retrofit.create(JsonPlaceHolderAPI.class);
       // getPosts();
        getComments();

    }

    private void getComments() {
        Call<List<comments>> getcomment=jsonPlaceHolderAPI.getcomments(2);

        getcomment.enqueue(new Callback<List<comments>>() {
            @Override
            public void onResponse(Call<List<comments>> call, Response<List<comments>> response) {

                if (!response.isSuccessful()){
                    textResults.setText(response.code());

                }

                List<comments> comments=response.body();
                for (comments comment:comments){
                    String content="";
                    content+= "id: "+ comment.getId()+"\n";
                    content+= "userId: "+ comment.getUserid()+"\n";
                    content+= "body: "+ comment.getBody()+"\n";
                    content+="title: " +comment.getTitle()+"\n";
                    textResults.append(content);


                }



            }

            @Override
            public void onFailure(Call<List<comments>> call, Throwable t) {

            }
        });
    }

    private void getPosts() {

        Call<List<posts>> getpost=jsonPlaceHolderAPI.getPosts();
        //Use enqueue instead of execute as it runs in background  thread so app wont stop
        getpost.enqueue(new Callback<List<posts>>() {
            @Override
            public void onResponse(Call<List<posts>> call, Response<List<posts>> response) {

                if (!response.isSuccessful()){
                    textResults.setText(response.code());

                }

                List<posts> posts=response.body();
                for (posts post:posts){
                     String content="";
                     content+= "id: "+ post.getId()+"\n";
                     content+= "userId: "+ post.getUserid()+"\n";
                    content+= "body: "+ post.getBody()+"\n";
                    content+="title: " +post.getTitle()+"\n";
                    textResults.append(content);


                }



            }

            @Override
            public void onFailure(Call<List<posts>> call, Throwable t) {
                textResults.setText(t.getMessage());

            }
        });

    }
}
