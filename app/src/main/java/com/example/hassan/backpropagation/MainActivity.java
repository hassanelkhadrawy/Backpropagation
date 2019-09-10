package com.example.hassan.backpropagation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double w0, w1, w2, w3, w4, w5, w6, w7, w8;
    double b1 = .35, b2 = .60;
    double i1 = .05, i2 = .10;
    double target_o1 = .01, target_o2 = .99;
    double eta = .5;
    double net_h1, net_h2, net_o1, net_o2;
    double out_h1, out_h2, out_o1, out_o2;
    double exp_h1, exp_h2, exp_o1, exp_o2;
    double E_o1, E_o2;
    double Tot_Error;
    double E_total_to_w5, E_total_to_w6, E_total_to_w7, E_total_to_w8;
    double E_total_to_w1, E_total_to_w2, E_total_to_w3, E_total_to_w4;
    TextView result;
    double E_total_o1_to_Out_h1, E_total_o2_to_Out_h1, E_total_to_Out_h1;
    double E_out_h1_to_neth1;
    EditText input_value1, input_vale2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        input_value1 = findViewById(R.id.input_value_1);
        input_vale2 = findViewById(R.id.input_value_2);

        CreateWaigths();


    }


    private void CreateWaigths() {


        w0 = Math.expm1(0.8);
        w1 = Math.expm1(0.8);
        w2 = Math.expm1(0.8);
        w3 = Math.expm1(0.8);
        w4 = Math.expm1(0.8);
        w5 = Math.expm1(0.8);
        w6 = Math.expm1(0.8);
        w7 = Math.expm1(0.8);
        w8 = Math.expm1(0.8);


    }

    private void Calculate_Forward() {


        net_h1 = (w1 * i1) + (w2 * i2) + (b1 * w0);
        net_h2 = (w3 * i1) + (w4 * i2) + (b1 * w0);


        exp_h1 = Math.exp(-net_h1);
        exp_h2 = Math.exp(-net_h2);

        out_h1 = 1 / (1 + exp_h1);
        out_h2 = 1 / (1 / 1 + exp_h2);

        net_o1 = (w5 * out_h1) + (w6 * out_h2) + (b2 * w0);
        net_o2 = (w7 * out_h1) + (w8 * out_h2) + (b2 * w0);

        exp_o1 = Math.exp(-net_o1);
        exp_o2 = Math.exp(-net_o2);

        out_o1 = 1 / (1 + exp_o1);
        out_o2 = 1 / (1 / 1 + exp_o2);


    }

    private void Calculate_Total_Error() {

        E_o1 = .5 * Math.pow(target_o1 - out_o1,2);
        E_o2 = .5 * Math.pow(target_o2 - out_o2,2);
        Tot_Error = E_o1 + E_o2;



    }
///Backword

    private void UpDate_Weight_for_Output_Layer() {

//        E_total_to_out_o1=-target_o1+out_o1;
//        out_o1_to_net_o1=out_o1*(1-out_o1);
//        net_o1_to_w5=out_h1;
//        E_total_to_w5=E_total_to_out_o1*out_o1_to_net_o1*net_o1_to_w5;
        E_total_to_w5 = ((-target_o1 + out_o1) * out_o1 * (1 - out_o1)) * out_h1;
        E_total_to_w6 = ((-target_o1 + out_o1) * out_o1 * (1 - out_o1)) * out_h2;
        E_total_to_w7 = ((-target_o2 + out_o2) * out_o2 * (1 - out_o2)) * out_h1;
        E_total_to_w8 = ((-target_o2 + out_o2) * out_o2 * (1 - out_o2)) * out_h2;

        w5 = w5 - eta * E_total_to_w5;
        w6 = w6 - eta * E_total_to_w6;
        w7 = w7 - eta * E_total_to_w7;
        w8 = w8 - eta * E_total_to_w8;


        result.setText("w5= "+w5 + "\n" +"w6= "+ w6 + "\n" + "w7= "+w7 + "\n" +"w8= "+ w8 + "\n");


    }

    private void UpDate_Weight_for_Hidden_Layer() {

        double E_o1_to_net_o1 = ((-target_o1 + out_o1) * (out_o1 * (1 - out_o1)));
        E_total_o1_to_Out_h1 = E_o1_to_net_o1 * w5;

        double E_o2_to_net_o2 = ((-target_o2 + out_o2) * (out_o2 * (1 - out_o2)));
        E_total_o2_to_Out_h1 = E_o2_to_net_o2 * w7;


//        E_total_to_Out_h1=E_total_o1_to_Out_h1 +E_total_o2_to_Out_h1;
//
//       E_out_h1_to_neth1=out_h1*(1-out_o1);
//
//       E_total_to_w1=E_total_to_Out_h1*E_out_h1_to_neth1*w1;


        E_total_to_w1 = ((-target_o1 + out_h1) * (1 - out_h1)) * i1;
        E_total_to_w2 = ((-target_o1 + out_h1) * (1 - out_h1)) * i2;
        E_total_to_w3 = ((-target_o2 + out_h2) * (1 - out_h2)) * i1;
        E_total_to_w4 = ((-target_o1 + out_h2) * (1 - out_h2)) * i2;


        w1 = w1 - eta * E_total_to_w1;
        w2 = w3 - eta * E_total_to_w2;
        w3 = w3 - eta * E_total_to_w3;
        w4 = w4 - eta * E_total_to_w4;

        result.append("w1= "+w1 + "\n" + "w2= "+w2 + "\n" + "w3= "+w3 + "\n" +"w4= "+w4+"\n" );


    }


    public void action(View view) {

        i1 = Double.parseDouble(input_value1.getText().toString());
        i2 = Double.parseDouble(input_vale2.getText().toString());

        Calculate_Forward();
        Calculate_Total_Error();
//        UpDate_Weight_for_Output_Layer();
//        UpDate_Weight_for_Hidden_Layer();


        if (Tot_Error==0){

            Toast.makeText(this, "Total Error = 0", Toast.LENGTH_SHORT).show();
        }else {

            for (int i=0;i<9;i++){

                Calculate_Forward();
                Calculate_Total_Error();
                UpDate_Weight_for_Output_Layer();
                UpDate_Weight_for_Hidden_Layer();

            }

            result.append("Total Error"+Tot_Error);

        }






    }
}
