package com.example.ftq194.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;


public class EditItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String selectedItemText = getIntent().getStringExtra("selectedItemText");
        EditText editText = (EditText)findViewById(R.id.editText);
        editText.setText(selectedItemText);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent data = new Intent();
                        EditText editText = (EditText)findViewById(R.id.editText);
                        data.putExtra("selectedItemText", editText.getText().toString());
                        data.putExtra("code", 200);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }
        );
    }
}
