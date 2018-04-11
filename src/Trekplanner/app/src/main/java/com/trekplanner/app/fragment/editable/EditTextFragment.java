package com.trekplanner.app.fragment.editable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.utils.AppUtils;

public class EditTextFragment extends Fragment {

    private EditText field;
    private AppUtils.EditTextOkListener okListener;

    public static EditTextFragment getInstance(EditText field, AppUtils.EditTextOkListener okListener) {
        Log.d("TREK_EditTextFrag", "Returning EditTextFragment -instance");
        EditTextFragment instance = new EditTextFragment();
        instance.field = field;
        instance.okListener = okListener;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.edittext_layout, container, false);
        final EditText editText = view.findViewById(R.id.edittext_fld);
        editText.setText(this.field.getText());

        final Button okBtn = view.findViewById(R.id.edittext_save_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                okListener.onOk(editText.getText().toString());
            }
        });

        return view;
    }
}
