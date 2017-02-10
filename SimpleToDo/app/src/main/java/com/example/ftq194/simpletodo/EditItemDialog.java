package com.example.ftq194.simpletodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemDialog extends DialogFragment {
    private EditText mEditText;

    public EditItemDialog() {
    }

    public static EditItemDialog newInstance(String title) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("itemTitle", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item_dialog, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText)view.findViewById(R.id.item_title);
        String title = getArguments().getString("itemTitle", "Todo item");
        mEditText.setText(title);

        // Show soft keyboard automatically and request focus to field.
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
