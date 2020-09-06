package com.example.coffeeorderingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import com.example.coffeeorderingapp.R;

import static android.widget.Toast.*;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity==100) {
            makeText(this,"You cannot have more than 100 coffees", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1) {
            makeText(this,"You cannot have less than 1 coffees", LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name_field = (EditText)findViewById(R.id.namefield);
        String name = name_field.getText().toString();
        Log.v("MainActivity","Name:" + name);

        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.whippedcream);
        boolean haswhippedcream=whippedCreamCheckBox.isChecked();
        Log.v("MainActivity","Has Whipped Cream:" + haswhippedcream);

        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate);
        boolean haschocolate=chocolateCheckBox.isChecked();
        Log.v("MainActivity","Has Chocolate:" + haschocolate);

        int price= calculateprice(haswhippedcream,haschocolate);
        String priceMessage=createordersummary(name,price,haswhippedcream,haschocolate);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
            intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
    }

    private int calculateprice(boolean addwhippedcream,boolean addchocolate) {
        int baseprice=5;
        if(addwhippedcream) baseprice += 1;
        if(addchocolate) baseprice += 2;
        return quantity*baseprice;
    }

    private  String createordersummary(String addname,int price, boolean addwhipppedcream, boolean addchocolate) {
        String priceMessage = "Total: $ " + price + ("\n " + getString(R.string.Order_Summary_Name, addname)) + ("\nAdd Whipped Cream:  " + addwhipppedcream) + ("\nAdd chocolate:  " + addchocolate);
        priceMessage+="\nQuantity:  " + quantity;
        priceMessage=priceMessage +  "\n"+ getString(R.string.Thank_You);
        return  priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}