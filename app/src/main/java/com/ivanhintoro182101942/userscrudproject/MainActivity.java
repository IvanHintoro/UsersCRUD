package com.ivanhintoro182101942.userscrudproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ivanhintoro182101942.userscrudproject.adapter.UsersAdapter;
import com.ivanhintoro182101942.userscrudproject.model.GetUser;
import com.ivanhintoro182101942.userscrudproject.model.User;
import com.ivanhintoro182101942.userscrudproject.rest.ApiClient;
import com.ivanhintoro182101942.userscrudproject.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnInsert;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert=(Button)findViewById((R.id.btnInsert));
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,InsertActivity.class));
            }
        });
        mRecyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma = this;
        refresh();
    }

    public void refresh(){
        Call<GetUser> userCall = mApiInterface.getUser();
        userCall.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                List<User> userList=response.body().getData();
                Log.d("Retrofit Get", "Jumlah data User "+String.valueOf(userList.size()));
                mAdapter = new UsersAdapter(userList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

}