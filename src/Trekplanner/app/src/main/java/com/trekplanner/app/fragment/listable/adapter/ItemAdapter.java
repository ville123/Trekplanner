package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for itemlist
 */
public class ItemAdapter extends ListAdapter {

    private List<Item> listRows;
    private List<Item> copyOfListRows;

    public ItemAdapter(Context context, ListFragment.ListViewActionListener listener) {
        super(context, listener);
    }

    @Override
    public void updateDataSetWithQuery(String query) {
        List<Item> items = new ArrayList<>();
        for (Item item : copyOfListRows) {
            if (item.getName().toLowerCase().contains(query)) {
                items.add(item);
            }
        }
        this.listRows = items;
        notifyDataSetChanged();
    }

    public void setListRows(List<Item> rows) {
        this.listRows = rows;
        this.copyOfListRows = new ArrayList<>();
        this.copyOfListRows.addAll(this.listRows); // for quering items
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item item = (Item) getChild(groupPosition, childPosition);

        Log.d("TREK_ItemListAdaptr", "Getting list item #" + childPosition + " for group #" + groupPosition + " with itemname " + item.getName());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listview_row_item_content_layout, null);
        }

        // TODO: dont open child for abstract items
        /**if (item.getType().equals(this.get.getContext().getString(R.string.enum_itemtype4)) ||
                item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype5)) ||
                item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype6)) ||
                item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype7))
                )
        {
            return ?;
        }**/

        RadioButton rbtn1 = convertView.findViewById(R.id.listview_item_rbtn_status1);
        RadioButton rbtn2 = convertView.findViewById(R.id.listview_item_rbtn_status2);
        RadioButton rbtn3 = convertView.findViewById(R.id.listview_item_rbtn_status3);

        if (item.getStatus().equals(convertView.getContext().getString(R.string.enum_itemstatus1))) {
            rbtn1.setChecked(true);
        } else if (item.getStatus().equals(convertView.getContext().getString(R.string.enum_itemstatus2))) {
            rbtn2.setChecked(true);
        } else if (item.getStatus().equals(convertView.getContext().getString(R.string.enum_itemstatus3))) {
            rbtn3.setChecked(true);
        }

        RadioGroup radioGroup = convertView.findViewById(R.id.listview_item_status_rbtgroup);
        final View finalConvertView = convertView;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = finalConvertView.findViewById(checkedId);

                if (radioButton.getId() == R.id.listview_item_rbtn_status1) {
                    item.setStatus(finalConvertView.getContext().getString(R.string.enum_itemstatus1));
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status2) {
                    item.setStatus(finalConvertView.getContext().getString(R.string.enum_itemstatus2));
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status3) {
                    item.setStatus(finalConvertView.getContext().getString(R.string.enum_itemstatus3));
                }

                viewActionListener.saveButtonClicked(item);
                Log.d("TREK_ItemListAdaptr", "Item status changed for item " + item.getName() + " to " + item.getStatus());
            }
        });

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

        final Item item = (Item) getGroup(groupPosition);

        Log.d("TREK_ItemListAdaptr", "Getting group item #" + groupPosition + " with groupname " + item.getName());

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_row_header_layout, null);
        }

        TextView headlineTextView = convertView
                .findViewById(R.id.listview_row_header_headline);
        headlineTextView.setText(item.getName());

        TextView textView = convertView
                .findViewById(R.id.listview_row_header_text);

        TextView expandMark
                = convertView.findViewById(R.id.listview_row_header_expandtext);
        expandMark.setText(R.string.expand_marks);

        ImageView imageView = convertView.findViewById(R.id.listview_row_header_image);

        if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype1))) {
            textView.setText(String.valueOf(item.getWeight()) + " " + convertView.getContext().getString(R.string.term_kilogram));
            imageView.setImageResource(R.drawable.item);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype2))) {
            textView.setText(String.valueOf(item.getWeight()) + " " + convertView.getContext().getString(R.string.term_kilogram));
            imageView.setImageResource(R.drawable.backpack);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype3))) {
            textView.setText(String.valueOf(item.getWeight()) + " " + convertView.getContext().getString(R.string.term_kilogram));
            imageView.setImageResource(R.drawable.food);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype4))) {
            textView.setText("");
            imageView.setImageResource(R.drawable.idea);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype5))) {
            textView.setText(AppUtils.formatDateTime(item.getDeadline(), AppUtils.DATETIME_FORMAT_DATE_TIME));
            imageView.setImageResource(R.drawable.remember);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype6))) {
            textView.setText(AppUtils.formatDateTime(item.getDeadline(), AppUtils.DATETIME_FORMAT_DATE_TIME));
            imageView.setImageResource(R.drawable.remember);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype7))) {
            textView.setText(AppUtils.formatDateTime(item.getDeadline(), AppUtils.DATETIME_FORMAT_DATE_TIME));
            imageView.setImageResource(R.drawable.task);
        } else if (item.getType().equals(convertView.getContext().getString(R.string.enum_itemtype8))) {
            textView.setText(String.valueOf(item.getWeight()) + " " + convertView.getContext().getString(R.string.term_kilogram));
             imageView.setImageResource(R.drawable.cutlery);
        }

        ImageView button = convertView.findViewById(R.id.listview_forward_button);

        button.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_ItemListAdaptr", "Clicked forward for item " + item.getName());
                Snackbar.make(view, "Clicked forward for item " + item.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                viewActionListener.onForwardButtonClick(item);

            }
        });

        return convertView;
    }
}