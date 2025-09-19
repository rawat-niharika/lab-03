package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class EditCityFragment extends DialogFragment {
    private static final String ARG_CITY = "arg_city";

    public interface EditCityDialogListener {
        void editCity(City city);
    }

    private EditCityDialogListener listener;
    private City cityToEdit;

    public static EditCityFragment newInstance(City city) {
        EditCityFragment frag = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, (Serializable) city);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            Serializable s = args.getSerializable(ARG_CITY);
            if (s instanceof City) {
                cityToEdit = (City) s;
            }
        }

        View view = requireActivity().getLayoutInflater().inflate(R.layout.fragment_edit_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_name);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_name);

        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    if (cityToEdit != null) {
                        String newName = editCityName.getText().toString().trim();
                        String newProv = editProvinceName.getText().toString().trim();
                        cityToEdit.setName(newName);
                        cityToEdit.setProvince(newProv);
                        if (listener != null) {
                            listener.editCity(cityToEdit);
                        }
                    }
                })
                .create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
