package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.utils.AppUtils;

import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for treklist
 */
public class TrekAdapter extends ListAdapter {

    private List<Trek> listRows;

    public TrekAdapter(Context context, ListFragment.ListViewActionListener listener) {
        super(context, listener);
    }

    @Override
    public void updateDataSetWithQuery(String query) {
        // do nothing
    }

    public void setListRows(List<Trek> rows) {
        this.listRows = rows;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listRows.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final Trek trek = (Trek) getGroup(groupPosition);

        Log.d("TREK_ItemListAdapter", "Getting group item #" + groupPosition + " with groupname " + trek.getDescription());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listview_row_header_layout, null);

            convertView.setEnabled(false);
        }

        TextView headlineTextView = convertView
                .findViewById(R.id.listview_row_header_headline);
        headlineTextView.setText(trek.getDescription());

        TextView textView = convertView
                .findViewById(R.id.listview_row_header_text);
        textView.setText(
                AppUtils.formatDateTime(trek.getStart(), AppUtils.DATETIME_FORMAT_ONLY_DATE)
                        + " - "
                        + AppUtils.formatDateTime(trek.getEnd(), AppUtils.DATETIME_FORMAT_ONLY_DATE));

        ImageView button = convertView.findViewById(R.id.listview_forward_button);

        button.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekListAdapter", "Clicked forwardbutton for trek " + trek.getDescription());
                Snackbar.make(view, "Clicked forward for trek " + trek.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                viewActionListener.onForwardButtonClick(trek);

            }
        });

        return convertView;
    }
}