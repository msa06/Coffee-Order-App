/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.msa.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    boolean hasWhippedCheck=false;
    boolean hasChocolateCheck=false;
    String whippedCreamMsg = "";
    String chocolateMsg = "";
    float price=0;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String orderSummary = orderSummary();
        if(checkQuantity()){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL,"suhaib.md06@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT,"COFFEE ORDER SUMMARY!!");
            intent.putExtra(Intent.EXTRA_TEXT,orderSummary);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(this,"Quantity should be in between 1 - 100!!",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Quantity is set to default!!",Toast.LENGTH_SHORT).show();
            displayQuantity(2);
            orderSummary();
        }
    }

    private String orderSummary() {
        int quantity = getQuantity();
        EditText nameEditText = (EditText) findViewById(R.id.name_textinput);
        String username=nameEditText.getText().toString();
        price =  quantity*50;
        if(hasWhippedCheck){
            whippedCreamMsg = "\nWhipped Cream is Added for 10";
            price+=10*quantity;
        }
        else if(!hasWhippedCheck){
            whippedCreamMsg="";
        }
        if(hasChocolateCheck){
            chocolateMsg = "\nChocolate is Added for 20";
            price+=20*quantity;
        }
        else if(!hasChocolateCheck){
            chocolateMsg="";
        }


       return username
               +"\nYour Order is Ready!!"
               + whippedCreamMsg
               + chocolateMsg
               + "\nQuantity: "+ quantity
               + "\nTotal : "
               + NumberFormat.getCurrencyInstance().format(price)
               + "\nThank You!!";
    }

    private boolean checkQuantity() {
        if(getQuantity()<1 || getQuantity()>100)
            return false;
        else
            return true;
    }

    public void decreaseQuantity(View view) {
        int quantity = getQuantity();
        if(quantity>1){
            quantity = quantity-1;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"You Have to Choose atleast 1 Coffee!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void increaseQuantity(View view) {
        int quantity = getQuantity();
        if(quantity<100){
            quantity = quantity+1;
            displayQuantity(quantity);
        }
        else{
            Toast.makeText(this,"We Do not provide more than 100 Coffee!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_value);
        quantityTextView.setText("" + quantity);
    }


    public void WhippedCheck(View view) {
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCheck = whippedCheckBox.isChecked();
    }

    public void ChocolateCheck(View view) {
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolateCheck = chocolateCheckBox.isChecked();
    }
    private int getQuantity(){
        EditText quanityEditText = (EditText) findViewById(R.id.quantity_value);
        return Integer.parseInt(quanityEditText.getText().toString());
    }

    public void orderSubmit(View view) {
        Intent order = new Intent(this,OrderActivity.class);
        startActivity(order);
    }
}
