package siva.infor.retrofitapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button receiveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text_view);
        receiveButton = findViewById(R.id.button);
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("sivaaaa");
                sendBroadcast(intent);
            }
        });
       Handler handler = new Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
           }
       };

//        sendStickyOrderedBroadcast();

       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://jsonplaceholder.typicode.com/")
               .addConverterFactory(GsonConverterFactory.create())
               .build();

       JsonPlaceHolder postClass = retrofit.create(JsonPlaceHolder.class);
       Call<List<PostClass>> listCall = postClass.getPost();
       listCall.enqueue(new Callback<List<PostClass>>() {
           @Override
           public void onResponse(Call<List<PostClass>> call, Response<List<PostClass>> response) {
                if (!response.isSuccessful()){
                    textView.setText("code:"+response.code());
                    return;
                }
                List<PostClass> postClasses = response.body();
                for (PostClass aClass:postClasses){
                    String s = "";
                    s += "id:" +aClass.getId() + "\n";
                    textView.append(s);
                }
           }

           @Override
           public void onFailure(Call<List<PostClass>> call, Throwable t) {
                textView.setText(t.getMessage());
           }
       });
    }
}
