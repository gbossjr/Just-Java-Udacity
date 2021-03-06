/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox onClickWhipped = findViewById(R.id.checkBox2);
        boolean  hasWhippedCream = onClickWhipped.isChecked();

        CheckBox chocolateClicked = findViewById(R.id.checkBox3);
        boolean chocChecked = chocolateClicked.isChecked();

        EditText nameInfo = findViewById(R.id.nameInput);
        String nameOnOrder = nameInfo.getText().toString();

        int price = calculatePrice(hasWhippedCream, chocChecked);

        String order = createOrderSummary(nameOnOrder, price, hasWhippedCream, chocChecked);

        displayMessage(order);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + nameOnOrder);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Calculates the price of the order.
     *
     * //@param quant is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean whipped, boolean Chocolate) {
        int basePrice = 5;

        if (whipped){
            basePrice += 1;
        }

        if (Chocolate){
            basePrice += 2;
        }
        return basePrice * quantity;
    }


    public void increment(View view) {
        if (quantity == 100){
            Context context = getApplicationContext();
            CharSequence text = "You Cannot Order More than 100 Cups of Coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity <= 0){
            Context context = getApplicationContext();
            CharSequence text = "Increase Quantity";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1 ;
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int figure) {
         TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + figure);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean Chocolate){
        String template = "Name: " + name;
        template += "\nAdd whipped Cream? " +hasWhippedCream;
        template += "\nAdd Chocolate? " + Chocolate;
        template += "\nQuantity: " + quantity;
        template += "\nTotal: $" + price;
        template += "\nThank You";
        return  template;
    }

}
