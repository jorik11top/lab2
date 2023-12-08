package com.example.lab2;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private List<ShoppingItem> shoppingList;
    private ShoppingListAdapter adapter;

    private static final int REQUEST_CODE_EDIT_ITEM = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingList = new ArrayList<>();
        adapter = new ShoppingListAdapter(shoppingList, this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddItemDialog());
    }

    private void showAddItemDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить элемент");


        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newItemName = input.getText().toString().trim();
                if (!newItemName.isEmpty()) {

                    ShoppingItem newItem = new ShoppingItem(System.currentTimeMillis(), newItemName);
                    shoppingList.add(newItem);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onItemClick(ShoppingItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите действие")
                .setItems(new CharSequence[]{"Редактировать", "Удалить"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Редактировать
                                editItem(item);
                                break;
                            case 1:
                                // Удалить
                                deleteItem(item);
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void editItem(ShoppingItem item) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("itemId", item.getId());
        intent.putExtra("itemName", item.getName());
        startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK && data != null) {
            String newItemName = data.getStringExtra("newItemName");
            long itemId = data.getLongExtra("itemId", -1);
            for (int i = 0; i < shoppingList.size(); i++) {
                ShoppingItem item = shoppingList.get(i);
                if (item.getId() == data.getLongExtra("itemId", -1)) {
                    item.setName(newItemName);
                    break;
                }
            }
            adapter.notifyDataSetChanged(); // Обновляем весь список
        }
    }
    private void deleteItem(ShoppingItem item) {

        shoppingList.remove(item);
        adapter.notifyDataSetChanged();
    }
}