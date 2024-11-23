package edu.utsa.cs3773.pathseer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class PayFilterDialogFragment extends DialogFragment {
    LayoutInflater layoutInflater;
    View view;
    Bundle args;
    EditText upperEditText;
    EditText lowerEditText;

    // interface that allows dialog to pass data back to the search screen
    public interface PayFilterDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    PayFilterDialogListener listener;

    // sets up the listener for passing data back
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PayFilterDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        layoutInflater = requireActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.dialog_pay_filter, null);

        args = getArguments();
        upperEditText = view.findViewById(R.id.input_upper_bound);
        lowerEditText = view.findViewById(R.id.input_lower_bound);

        // set the text if there already is a filter set through args
        if (args.getDouble("upper") > -1) { upperEditText.setText(String.format("%,.2f", args.getDouble("upper"))); }
        if (args.getDouble("lower") > -1) { lowerEditText.setText(String.format("%,.2f", args.getDouble("lower"))); }


        builder.setView(view)
                .setPositiveButton("Set filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // set args to new values to pass back into the search screen
                        args.clear();
                        args.putDouble("upper", Double.parseDouble(upperEditText.getText().toString()));
                        args.putDouble("lower", Double.parseDouble(lowerEditText.getText().toString()));
                        listener.onDialogPositiveClick(PayFilterDialogFragment.this); // call the positive click function in search screen
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PayFilterDialogFragment.this.getDialog().cancel(); // just do nothing
                    }
                });

        return builder.create();
    }
}
