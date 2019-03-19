package com.example.testrepositoryjava.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testrepositoryjava.R;
import com.example.testrepositoryjava.viewmodel.MainActivityViewModel;
import com.example.testrepositoryjava.vo.Status;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private UserAdapter userAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.usersList);

        AndroidInjection.inject(this);


        final MainActivityViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        observeViewModel(viewModel);
    }


    private void observeViewModel(MainActivityViewModel viewModel) {

        viewModel.getModelResonse().observe(this, usersList -> {

            if (usersList.status == Status.SUCCESS){
                userAdapter = new UserAdapter(getApplicationContext(), usersList.data);
                listView.setAdapter(userAdapter);
            }



//            if (usersList.status == Status.ERROR) {
//                Toast.makeText(MainActivity.this, "network failure.", Toast.LENGTH_SHORT).show();
//            } else {
//
//                userAdapter = new UserAdapter(getApplicationContext(), usersList.data);
//                listView.setAdapter(userAdapter);
//            }


        });

    }

}
