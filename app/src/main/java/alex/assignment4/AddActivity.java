package alex.assignment4;

import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;


public class AddActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    Button add_btn;
    Button back_btn;

    EditText itemInput;
    EditText priceInput;
    EditText priorityInput;
    EditText quantityInput;

    TextView itemError;
    TextView priceError;
    TextView priorityError;
    TextView quantityError;

    String id;
    CognitoCachingCredentialsProvider credentialsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        IdentityManager idm = new IdentityManager(getApplicationContext(),
                new AWSConfiguration(getApplicationContext()));
        IdentityManager.setDefaultIdentityManager(idm);

        credentialsProvider = IdentityManager.getDefaultIdentityManager().getUnderlyingProvider();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        add_btn = (Button) findViewById(R.id.add_button);
        back_btn = (Button) findViewById(R.id.button);
        itemInput = findViewById(R.id.item_input);
        priceInput = findViewById(R.id.price_input);
        priorityInput = findViewById(R.id.priority_input);
        quantityInput =  findViewById(R.id.quantity_input);

        itemError = (TextView) findViewById(R.id.itemerror_text);
        priceError = (TextView) findViewById(R.id.priceerror_text);
        priorityError = (TextView) findViewById(R.id.priorityerror_text);
        quantityError = (TextView) findViewById(R.id.quantityerror_text);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = itemInput.getText().toString();
                String price = priceInput.getText().toString();
                String priority = priorityInput.getText().toString();
                String quantity = quantityInput.getText().toString();

                boolean validate = false;

            //Validate Item (Database connection)
                boolean check_item = true;

            //Validate price
                boolean check_price = false;
                if (isDouble(price)) {
                    check_price = true;
                }
                else{
                    priceError.setText("Enter Price Value");
                    priceError.setTextColor(Color.RED);
                    priceInput.setText("");
                }

            //Validate priority
                boolean check_priority = false;
                if (isInt(priority)) {
                    check_priority = true;
                }
                else{
                    priorityError.setText("Enter Integer");
                    priorityError.setTextColor(Color.RED);
                    priorityInput.setText("");
                }
            //Validate quantity
                boolean check_quantity = false;
                if (isInt(quantity)) {
                    check_quantity = true;
                }
                else{
                    priorityError.setText("Enter Integer");
                    priorityError.setTextColor(Color.RED);
                    priorityInput.setText("");
                }

                if (check_item && check_price && check_priority && check_quantity){
                    validate = true;
                }

            //If validated, make to next activity.
                if (validate){
                    createShoppingListItem(credentialsProvider, item, Double.parseDouble(price), Integer.parseInt(priority), Integer.parseInt(quantity));

                    itemInput.getText().clear();
                    itemError.setText("");
                    priceInput.getText().clear();
                    priceError.setText("");
                    priorityInput.getText().clear();
                    priorityError.setText("");
                    quantityInput.getText().clear();
                    quantityError.setText("");

                    Context context = getApplicationContext();
                    CharSequence text = item + " Added Successfully";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (AddActivity.this, MainActivity.class));
            }
        });

    }

    //********************************************************//

    public boolean isInt(String message){
        try{
            int i = Integer.parseInt(message);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public boolean isDouble(String message){
        try{
            double i = Double.parseDouble(message);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public void createShoppingListItem (CognitoCachingCredentialsProvider credentialsProvider, String item, Double price, int priority, int quantity) {
        final ShoppingListDO Item = new ShoppingListDO();

        Item.setUserId(credentialsProvider.getCachedIdentityId());

        Item.setItemName(item);
        Item.setPrice(price);
        Item.setPriority(priority);
        Item.setQuantity(quantity);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(Item);
            }
        }).start();

    }

    public void loadItem (final String item) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ShoppingListDO itemRead = dynamoDBMapper.load(
                        ShoppingListDO.class,
                        credentialsProvider.getCachedIdentityId(),
                        item);

                // Item read

            }
        }).start();
    }



}
