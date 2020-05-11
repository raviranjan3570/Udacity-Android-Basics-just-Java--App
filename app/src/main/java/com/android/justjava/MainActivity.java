package com.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method displays the given quantity value on the screen.
     */

    private void displayQuantity(int numberOfCoffees) {

        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String temp = "" + numberOfCoffees;
        quantityTextView.setText(temp);
    }

    /**
     * This method increments the given quantity value on the screen.
     */

    public void increment(View view) {

        if (quantity == 100) {
            Context context = getApplicationContext();
            String text = "You cannot have more than 100 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the given quantity value on the screen.
     */

    public void decrement(View view) {

        if (quantity == 1) {
            Context context = getApplicationContext();
            String text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants cream topping.
     * @param addChocolate    is whether or not the user wants chocolate topping.
     * @return total price
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * create order summary method gives the summary of the order
     *
     * @param price           of the order
     * @param addWhippedCream whether or not user wants whipped cream
     * @return textSummary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String inputNameText) {

        String priceMessage = " Name: " + inputNameText;
        priceMessage = priceMessage + "\n Add Whipped Cream: " + addWhippedCream;
        priceMessage = priceMessage + "\n Add Chocolate: " + addChocolate;
        priceMessage = priceMessage + "\n Quantity: " + quantity;
        priceMessage = priceMessage + "\n Total: $ " + price;
        priceMessage = priceMessage + "\n Thank You!";
        return priceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateToppingCheckbox = findViewById(R.id.chocolate_topping_checkbox);
        boolean hasChocolate = chocolateToppingCheckbox.isChecked();

        EditText inputName = findViewById(R.id.input_name);
        String inputNameString = inputName.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, inputNameString);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + inputNameString);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}