package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private long itemId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        itemNameEditText = findViewById(R.id.itemNameEditText);

        itemId = getIntent().getLongExtra("itemId", -1);
        String itemName = getIntent().getStringExtra("itemName");

        if (itemId != -1 && itemName != null) {
            itemNameEditText.setText(itemName);
        } else {
            Toast.makeText(this, "Ошибка при передаче данных", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void onSaveButtonClicked() {
        String newItemName = itemNameEditText.getText().toString().trim();
        Log.d("EditItemActivity", "onSaveButtonClicked: newItemName = " + newItemName);

        if (newItemName.isEmpty()) {
            Toast.makeText(this, "Введите имя элемента", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newItemName", newItemName);
        resultIntent.putExtra("itemId", itemId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}