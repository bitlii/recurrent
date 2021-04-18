package com.bitco.recurrent.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bitco.recurrent.R;
import com.bitco.recurrent.fragment.DatePickerFragment;
import com.bitco.recurrent.model.Item;
import com.bitco.recurrent.model.TransactionType;


import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

/**
 * Activity class that handles the adding a new or editing an existing item.
 */
public class AddEditItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_SAVED_ITEM = "SAVED_ITEM";

    private EditText editName;
    private EditText editDescription;
    private EditText editAmount;
    private EditText editInterval;
    private Spinner spinnerTransactionType;
    private TextView textDate;
    private Button buttonEditDate;

    private LocalDate setDate;

    private long itemId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        editName = findViewById(R.id.editItemName);
        editDescription = findViewById(R.id.editItemDescription);
        editAmount = findViewById(R.id.editAmount);
        buttonEditDate = findViewById(R.id.buttonEditDate);
        textDate = findViewById(R.id.textDate);

        editInterval = findViewById(R.id.editInterval);
        spinnerTransactionType = findViewById(R.id.spinnerTransactionType);
        spinnerTransactionType.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, TransactionType.values()));

        Intent intent = getIntent();
        // If the activity was started by an edit event.
        if (intent.hasExtra(MainActivity.EXTRA_EDIT_ITEM)) {
            setTitle("Edit Text");

            Item item = intent.getParcelableExtra(MainActivity.EXTRA_EDIT_ITEM);
            assert item != null;
            itemId = item.getId();

            editName.setText(item.getName());
            editDescription.setText(item.getDescription());
            editAmount.setText(String.valueOf(item.getAmount()));
            editInterval.setText(String.valueOf(item.getInterval()));
            textDate.setText(item.getLastOccurrence().toString());
            setDate = item.getLastOccurrence();
            spinnerTransactionType.setSelection(item.getType().ordinal());
        }
        else {
            setTitle("Add Item");
            textDate.setText(LocalDate.now().toString());
        }

        buttonEditDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogFragment newDateFrag = new DatePickerFragment();
                newDateFrag.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        setDate = LocalDate.of(year, month+1, day);
        textDate.setText(setDate.toString());
    }

    /**
     * Create a new item based off the input given on the activity screen.
     * Sets a new intent with the new item and finishes activity.
     */
    private void saveItem() {
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        double amount = Double.parseDouble(editAmount.getText().toString());
        int interval = Integer.parseInt(editInterval.getText().toString());

        String typeString = spinnerTransactionType.getSelectedItem().toString();
        TransactionType transactionType = TransactionType.valueOf(typeString);

        Item newItem = new Item(name, description, amount, transactionType, setDate, interval);
        if (itemId != -1) {
            newItem.setId(itemId);
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SAVED_ITEM, newItem);

        setResult(RESULT_OK, intent);
        finish();
    }

    // ----- Action Bar Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveItem) {
            saveItem();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
