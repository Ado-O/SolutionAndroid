package com.example.android.justjava;

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
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // kod koji se odnosi na checkbox da li je true ili false
        CheckBox whipeedCreamCheckbox = (CheckBox) findViewById(R.id.whipeed_cream_checkbox);
        boolean hasWhipeedCream = whipeedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCream = chocolateCheckbox.isChecked();
        // kod koji se odnosi na unosenje edit texta
        EditText textName = (EditText) findViewById(R.id.descrption_view);
        String hasTextName = textName.getText().toString();
        //  Log.v("MainActivity", "da li je: " + hasWhipeedCream);
        int price = calculatePrice(hasWhipeedCream, hasChocolateCream);
        String priceMessage = createOrderSummary(price, hasWhipeedCream, hasChocolateCream, hasTextName);
        // intent dio koda koji salje direktno na email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for : ");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * metoda za izbacivanje punog teksta za cijenom koju
     * dobijemo od submitOrder metode
     */
    private String createOrderSummary(int price, boolean addWhipeedCream, boolean addChocolate, String addTextName) {

        String priceMessage = "\nName: " + addTextName;
        priceMessage += "\nAdd Whipeed Cream: " + addWhipeedCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you";
        return priceMessage;
    }

    /*
     * metoda za izracunavanje cijene
     */
    private int calculatePrice(boolean addWhipeedCream, boolean addChocolate) {
        // cijena 1 cup of caffe
        int basePrice = 5;

        if (addWhipeedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }

    /**
     * metoda za +
     */
    public void increment(View view) {
        if (quantity == 100) {
            // toast message
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * metoda za -
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // toast message
            Toast.makeText(this, "You cannot have more than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
