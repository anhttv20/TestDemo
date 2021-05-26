package com.example.testdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testdemo.model.MyTodo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    List<MyTodo> myTodos;
    private Todo_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.todo_list_view);
        fab=findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("mytodo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myTodos = new ArrayList<MyTodo>();
                for(DataSnapshot item: snapshot.getChildren())
                {
                    MyTodo myTodo = item.getValue(MyTodo.class);
                    myTodos.add(myTodo);
                }
                adapter = new Todo_Adapter(MainActivity.this,
                        myTodos);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        NewTodoActivity.class);
                startActivity(intent);
            }
        });
    }

}
