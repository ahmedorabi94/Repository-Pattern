package com.example.testrepositoryjava.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testrepositoryjava.R;
import com.example.testrepositoryjava.repository.data.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserAdapter extends ArrayAdapter<User> {

   private List<User> userArrayList;

    public UserAdapter(@NonNull Context context, List<User> users) {
        super(context, 0,users);
        this.userArrayList =users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = userArrayList.get(position);

        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);

        TextView fistName = view.findViewById(R.id.firsName);
        TextView lastName = view.findViewById(R.id.lsatName);

        fistName.setText(user.getFirst());
        lastName.setText(user.getLast());


        return view;
    }
}
