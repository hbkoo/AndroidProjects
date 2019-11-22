package com.example.acer.experimenttext2.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.acer.experimenttext2.R;

public class MaterialFixActivity extends AppCompatActivity {
    private Button submitFix,exitFix;
    private EditText teachBuild,material;
    private Spinner classroom_spinner;

    private ArrayAdapter<String> adapter = null;
    private static String[] classroom = {"--请选择--","1-501","1-302","1-402","1-502","3-101","3-201","3-301","3-303"};


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_fix);
        submitFix = (Button) findViewById(R.id.submitFix_button);
        exitFix = (Button) findViewById(R.id.exitFix_button);
     //   teachBuild = (EditText)findViewById(R.id.teachBuild_EditText);
        classroom_spinner = (Spinner) findViewById(R.id.classroom_Sptinner);
        material = (EditText) findViewById(R.id.material_EditText);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new ArrayAdapter<String>(MaterialFixActivity.this,
                android.R.layout.simple_spinner_dropdown_item,classroom);
        classroom_spinner.setFocusable(true);
        classroom_spinner.setAdapter(adapter);


        //TODO 报修器材数据的提交
        submitFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        exitFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
