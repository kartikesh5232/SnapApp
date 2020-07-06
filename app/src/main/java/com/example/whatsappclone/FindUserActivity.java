package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {
    private RecyclerView userlistRecycleview;
    private RecyclerView.Adapter userlistAdapter;
    private RecyclerView.LayoutManager userlistLayoutManager;
    ArrayList<UserObject> contactlist = new ArrayList<UserObject>();
    ArrayList<UserObject> userlist = new ArrayList<UserObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        userlistRecycleview = findViewById(R.id.userlist);
        userlistRecycleview.setNestedScrollingEnabled(false);
        userlistRecycleview.setHasFixedSize(false);
        userlistLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        userlistRecycleview.setLayoutManager(userlistLayoutManager);

        userlistAdapter = new UserListAdapter(userlist);

        userlistRecycleview.setAdapter(userlistAdapter);
        getContactList();

    }


    private void getContactList() {
        String iso = "+91";

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            phone = phone.replace(" ", "");
            phone = phone.replace("-", "");
            phone = phone.replace("(", "");
            phone = phone.replace(")", "");

            if (!String.valueOf(phone.charAt(0)).equals("+")) {
                phone = iso + phone;
                Log.i("number", phone);

            }

            UserObject user = new UserObject("",name, phone);
            contactlist.add(user);
            getUserDetail(user);
        }

    }

    private void getUserDetail(UserObject user) {
        DatabaseReference userdatabaseref = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = userdatabaseref.orderByChild("phone").equalTo(user.getPhone());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String phone = "", name = "";
                    for (DataSnapshot childsnapshot : snapshot.getChildren()) {
                        if (childsnapshot.child("phone").getValue() != null)
                            phone = childsnapshot.child("phone").getValue().toString();
                        if (childsnapshot.child("name").getValue() != null)
                            name = childsnapshot.child("name").getValue().toString();

                        UserObject userobject = new UserObject(childsnapshot.getKey(),name, phone);
                        if (name.equals(phone)) {
                            for (UserObject iterator : contactlist) {
                                if (iterator.getPhone().equals(userobject.getPhone())) {
                                    userobject.setName(iterator.getName());
                                }

                            }
                        }

                        userlist.add(userobject);
                        userlistAdapter.notifyDataSetChanged();
                        return;

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
