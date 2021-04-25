package com.embit.recurrent.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.embit.recurrent.R;
import com.embit.recurrent.fragment.DatePickerFragment;
import com.embit.recurrent.model.Item;
import com.embit.recurrent.model.TransactionType;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private TextInputLayout spinnerLayout;
    private TextInputLayout editDateLayout;

    private LocalDate setDate;

    private long itemId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextInputLayout nameLayout = findViewById(R.id.editItemName);
        TextInputLayout descriptionLayout = findViewById(R.id.editItemDescription);
        TextInputLayout amountLayout = findViewById(R.id.editAmount);
        amountLayout.setPrefixText(this.getSharedPreferences(getString(R.string.SHARED_PREF_KEY), Context.MODE_PRIVATE).getString("localeSymbol", "$"));
        TextInputLayout intervalLayout = findViewById(R.id.editInterval);
        editDateLayout = findViewById(R.id.editDate);

        spinnerLayout = findViewById(R.id.spinnerTransactionType);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, TransactionType.values());

        editName = nameLayout.getEditText();
        editDescription = descriptionLayout.getEditText();
        editAmount = amountLayout.getEditText();
        editInterval = intervalLayout.getEditText();

        Intent intent = getIntent();
        // If the activity was started by an edit event.
        if (intent.hasExtra(MainActivity.EXTRA_EDIT_ITEM)) {
            getSupportActionBar().setTitle("Edit Item");

            Item item = intent.getParcelableExtra(MainActivity.EXTRA_EDIT_ITEM);
            assert item != null;
            itemId = item.getId();

            editName.setText(item.getName());
            editDescription.setText(item.getDescription());
            editAmount.setText(String.valueOf(item.getAmount()));
            editInterval.setText(String.valueOf(item.getInterval()));
            editDateLayout.getEditText().setText(item.getLastOccurrence().toString());
            setDate = item.getLastOccurrence();
            ((AutoCompleteTextView) spinnerLayout.getEditText()).setText(adapter.getItem(item.getType().ordinal()).toString());
        }
        else {
            getSupportActionBar().setTitle("Add Item");

            editDateLayout.getEditText().setText(LocalDate.now().toString());
            setDate = LocalDate.now();
            ((AutoCompleteTextView) spinnerLayout.getEditText()).setText(adapter.getItem(0).toString());
        }
        ((AutoCompleteTextView) spinnerLayout.getEditText()).setAdapter(adapter); // Set adapter after to fix android bug.

        editDateLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newDateFrag = new DatePickerFragment();
                newDateFrag.show(getSupportFragmentManager(), "datePicker");
            }
        });


    }


    /**
     * Called when the DatePickerFragment completes and sets a new date.
     * @param datePicker the DatePicker fragment.
     * @param year selected year
     * @param month selected month
     * @param day selected day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        setDate = LocalDate.of(year, month+1, day);
        editDateLayout.getEditText().setText(setDate.toString());
    }


    /**
     * Create a new item based off the input given on the activity screen.
     * Sets a new intent with the new item and finishes activity.
     */
    private void saveItem() {

        if (!validateFields()) {
            return;
        }

        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        double amount = Double.parseDouble(editAmount.getText().toString());
        int interval = Integer.parseInt(editInterval.getText().toString());

        TransactionType transactionType = TransactionType.valueOf(((AutoCompleteTextView) spinnerLayout.getEditText()).getText().toString());


        Item newItem = new Item(name, description, amount, transactionType, setDate, interval);
        if (itemId != -1) { // New Item
            newItem.setId(itemId);
        }

        newItem.updateOccurrence();

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SAVED_ITEM, newItem);

        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Checks if all of the required fields are filled out.
     * @return true if all fields are filled out - false otherwise.
     */
    private boolean validateFields() {
        boolean validated = true;

        if (editName.getText().toString().equals("")) {
            editName.setError("Required field.");
            validated = false;
        }

        if (editAmount.getText().toString().equals("")) {
            editAmount.setError("Required field.");
            validated = false;
        }

        if (editInterval.getText().toString().equals("")) {
            editInterval.setError("Required field.");
            validated = false;
        }

        return validated;
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
