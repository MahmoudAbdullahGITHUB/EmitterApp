package com.example.emitterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.emitterapp.pojo.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements UsersAdapter.OnUsersListener {

    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    Button okSendingB;
    int position;
    ArrayList<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        usersAdapter = new UsersAdapter(this);
        recyclerView.setAdapter(usersAdapter);
        usersList = new ArrayList<>();

//        View view = getLayoutInflater().inflate(R.layout.pop_up_window, null);
//        okSendingB = view.findViewById(R.id.ok_id);

        getAllUsers();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void getAllUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<Users>> call = apiInterface.getAllUsers();

        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                assert response.body() != null;
                System.out.println("success" + response.body().get(0).getName());
                usersAdapter.setList((ArrayList<Users>) response.body());
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                System.out.println("failed = " + t.toString());
            }
        });

    }

    @Override
    public void onUserClicked(int position, ArrayList<Users> usersList) {
        Toast.makeText(this, "user position = " + position + "", Toast.LENGTH_LONG).show();

        this.position = position;
        this.usersList = usersList;

        showPopupWindow();
    }


    public void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);


        okSendingB = popupView.findViewById(R.id.ok_id);
        okSendingB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Ok = " + usersList.get(position).getName() + "yes ");
                sendBroadcast();
                popupWindow.dismiss();
            }
        });
    }

    public void sendBroadcast() {
        Intent intent = new Intent("Emitter.Sender");
        intent.putExtra("userName", usersList.get(position).getName());
        sendBroadcast(intent);
    }







    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra("Emitter.Sender");
            //textView.setText(receivedText);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("Emitter.Sender");
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

}