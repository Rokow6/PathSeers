package edu.utsa.cs3773.pathseer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class MultiChoiceFilterDialogFragment extends DialogFragment {
    Bundle args;
    String[] items;
    ArrayList<String> selectedItems;
    boolean[] checkedItems;

    // interface that allows dialog to pass data back to the search screen
    public interface MultiChoiceFilterDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    MultiChoiceFilterDialogListener listener;

    // sets up the listener for passing data back
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (MultiChoiceFilterDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int i;
        args = getArguments();

        // setup items from the arraylist passed in from the job search screen
        ArrayList<String> itemTexts = args.getStringArrayList("itemTexts");
        items = itemTexts.toArray(new String[itemTexts.size()]);

        checkedItems = new boolean[items.length]; // initialize checkedItems to false based on items length
        for (i = 0; i < checkedItems.length; i++) {
            checkedItems[i] = false;
        }

        // setup selected items
        selectedItems = args.getStringArrayList("selectedItems");

        // look through items and selected items and check the ones that are already selected
        for (i = 0; i < items.length; i++) {
            if (selectedItems.contains(items[i])) {
                checkedItems[i] = true;
            }
        }

        builder.setTitle(args.getString("title"))
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedItems.add(items[which].toString()); // add the clicked item to selected items
                        }
                        else {
                            selectedItems.remove(items[which].toString()); // remove the clicked item from selected items
                        }
                    }
                })
                .setPositiveButton("Set filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // set args to new values to pass back into the search screen
                        args.clear();
                        args.putStringArrayList("selectedItems", selectedItems);
                        listener.onDialogPositiveClick(MultiChoiceFilterDialogFragment.this); // call the positive click function in search screen
                    }
                })
                .setNegativeButton("Clear filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // fragment ID is already in args, so don't clear it and just call the negative click listener
                        listener.onDialogNegativeClick(MultiChoiceFilterDialogFragment.this); // call the negative click function in search screen
                    }
                });

        return builder.create();
    }
}
