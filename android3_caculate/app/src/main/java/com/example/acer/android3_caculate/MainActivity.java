package com.example.acer.android3_caculate;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.text.*;

public class MainActivity extends AppCompatActivity {

    private Button btnC,btnCHU,btnDEL,btnCHENG,btnJIA,btnJIAN,btnDENG;
    private Button btnNum [] = new Button[11];
    private EditText editText,editText1;
    Editable edit1,edit;
    int index1,index;
    double result =0,num1 =0,num2 =0;
    String command,str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        editText1 = (EditText) findViewById(R.id.editText1);
        btnC = (Button) findViewById(R.id.buttonC);
        btnCHU = (Button) findViewById(R.id.buttonCHU);
        btnDEL = (Button) findViewById(R.id.buttonDEL);
        btnCHENG = (Button) findViewById(R.id.buttonCHENG);
        btnJIA = (Button) findViewById(R.id.buttonJIA);
        btnJIAN = (Button) findViewById(R.id.buttonJIAN);
        btnDENG = (Button) findViewById(R.id.buttonDENG);
        btnNum[0] = (Button) findViewById(R.id.button0);
        btnNum[1] = (Button) findViewById(R.id.button1);
        btnNum[2] = (Button) findViewById(R.id.button2);
        btnNum[3] = (Button) findViewById(R.id.button3);
        btnNum[4] = (Button) findViewById(R.id.button4);
        btnNum[5] = (Button) findViewById(R.id.button5);
        btnNum[6] = (Button) findViewById(R.id.button6);
        btnNum[7] = (Button) findViewById(R.id.button7);
        btnNum[8] = (Button) findViewById(R.id.button8);
        btnNum[9] = (Button) findViewById(R.id.button9);
        btnNum[10] = (Button) findViewById(R.id.buttonD);

        btnC.setOnClickListener(new mClick());
        btnCHU.setOnClickListener(new mClick());
        btnDEL.setOnClickListener(new mClick());
        btnCHENG.setOnClickListener(new mClick());
        btnJIA.setOnClickListener(new mClick());
        btnJIAN.setOnClickListener(new mClick());
        btnDENG.setOnClickListener(new mClick());
        btnNum[0].setOnClickListener(new mClick());
        btnNum[1].setOnClickListener(new mClick());
        btnNum[2].setOnClickListener(new mClick());
        btnNum[3].setOnClickListener(new mClick());
        btnNum[4].setOnClickListener(new mClick());
        btnNum[5].setOnClickListener(new mClick());
        btnNum[6].setOnClickListener(new mClick());
        btnNum[7].setOnClickListener(new mClick());
        btnNum[8].setOnClickListener(new mClick());
        btnNum[9].setOnClickListener(new mClick());
        btnNum[10].setOnClickListener(new mClick());
    }

    class mClick implements View.OnClickListener{

        void Insert(String str1){
            index1 = editText1.getSelectionStart();
            index = editText.getSelectionStart();//获取光标所在位置
            edit1 = editText1.getEditableText();
            edit = editText.getEditableText();//获取EditText的文字
            if (index < 0 || index >= edit.length() || index1 < 0 || index1 >= edit1.length()){
                edit.append(str1);
                edit1.append(str1);
            }else{
                edit.insert(index,str1);
                edit1.insert(index1,str1);//光标所在位置插入文字
            }
        }

        public void onClick(View argo){

            if(argo == btnNum[0])
                Insert("0");
            if(argo == btnNum[1])
                Insert("1");
            if(argo == btnNum[2])
                Insert("2");
            if(argo == btnNum[3])
                Insert("3");
            if(argo == btnNum[4])
                Insert("4");
            if(argo == btnNum[5])
                Insert("5");
            if(argo == btnNum[6])
                Insert("6");
            if(argo == btnNum[7])
                Insert("7");
            if(argo == btnNum[8])
                Insert("8");
            if(argo == btnNum[9])
                Insert("9");
            if(argo == btnNum[10])
                Insert(".");
            else if(argo == btnC){

                editText.setText("");
                editText1.setText("");
                result = 0;

            }

            else if(argo == btnDEL){


                editText.setText("123456");

            }

            else if(argo == btnCHU){

                str = editText.getText().toString();
                num1 = Double.parseDouble(str);
                command = "÷";
                index1 = editText1.getSelectionStart();
                edit1 = editText1.getEditableText();
                if (index1 < 0 || index1 >= edit1.length() )
                    edit1.append(command);
                else
                    edit1.insert(index1,command);
                editText.setText("");

            }

            else if(argo == btnCHENG){

                str = editText.getText().toString();
                if(str.equals(null))
                    num1=0;
                num1 = Double.parseDouble(str);
                command = "×";
                index1 = editText1.getSelectionStart();
                edit1 = editText1.getEditableText();
                if (index1 < 0 || index1 >= edit1.length() )
                    edit1.append(command);
                else
                    edit1.insert(index1,command);
                editText.setText("");

            }

            else if(argo == btnJIA){

                str = editText.getText().toString();
                num1 = Double.parseDouble(str);
                command = "＋";
                index1 = editText1.getSelectionStart();
                edit1 = editText1.getEditableText();
                if (index1 < 0 || index1 >= edit1.length() )
                    edit1.append(command);
                else
                    edit1.insert(index1,command);
                editText.setText("");

            }

            else if(argo == btnJIAN){

                str = editText.getText().toString();
                num1 = Double.parseDouble(str);
                command = "－";
                index1 = editText1.getSelectionStart();
                edit1 = editText1.getEditableText();
                if (index1 < 0 || index1 >= edit1.length() )
                    edit1.append(command);
                else
                    edit1.insert(index1,command);
                editText.setText("");

            }

            else if(argo == btnDENG){

                str = editText.getText().toString();
                num2 = Double.parseDouble(str);
                switch(command){
                    case "＋":result = num1+num2;break;
                    case "－":result = num1-num2;break;
                    case "×":result = num1*num2;break;
                    case "÷":result = num1/num2;break;
                }
                str=""+result;
                editText.setText(str);
                editText1.setText(str);

            }





        }

    }

}




