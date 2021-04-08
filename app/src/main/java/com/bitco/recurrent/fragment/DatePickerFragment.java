package com.bitco.recurrent.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LocalDate now = LocalDate.now();

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
    }



}
