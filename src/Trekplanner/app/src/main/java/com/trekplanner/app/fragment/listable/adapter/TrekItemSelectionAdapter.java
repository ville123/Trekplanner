package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.utils.AppUtils;

import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for treklistselection
 */
public class TrekItemSelectionAdapter extends ArrayAdapter<Item>  {

    private final List<Item> rows;

    public TrekItemSelectionAdapter(List<Item> rows, Context context) {
        super(context, R.layout.trekitem_selection_row_layout, rows);
        this.rows = rows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = rows.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.trekitem_selection_row_layout, parent, false);
        }

        TextView headlineView = convertView.findViewById(R.id.listview_row_header_headline);
        headlineView.setText(item.getName());

        TextView textView = convertView.findViewById(R.id.listview_row_header_text);
        ImageView imageView = convertView.findViewById(R.id.listview_row_header_image);
        AppUtils.setItemRowContent(getContext(), item, textView, imageView);

        return convertView;
    }

}