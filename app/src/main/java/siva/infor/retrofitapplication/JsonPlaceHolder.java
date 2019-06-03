package siva.infor.retrofitapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolder {
    @GET("posts")
    Call<List<PostClass>> getPost();
}
