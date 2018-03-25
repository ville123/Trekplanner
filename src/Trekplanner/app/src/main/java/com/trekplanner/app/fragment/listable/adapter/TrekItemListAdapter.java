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
import com.trekplanner.app.model.Item;

import java.util.List;

public class TrekItemListAdapter extends ListviewAdapter {

    private List<Item> listRows;

    public TrekItemListAdapter(Context context) {
        super(context);
    }

    public void setListRows(List<Item> rows) {
        this.listRows = rows;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Item item = (Item) getChild(groupPosition, childPosition);

        Log.d("TREK_TrekItemListAdaptr", "Getting list item #" + childPosition + " for group #" + groupPosition + " with itemname " + item.getName());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.editview_row_item_content_layout, null);
        }
/**
        RadioButton rbtn1 = convertView.findViewById(R.id.listview_item_rbtn_status2);
        RadioButton rbtn2 = convertView.findViewById(R.id.listview_item_rbtn_status2);
        RadioButton rbtn3 = convertView.findViewById(R.id.listview_item_rbtn_status3);

        String status = String.valueOf(item.getStatus());

        if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus1))) {
            rbtn1.setChecked(true);
        } else if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus2))) {
            rbtn2.setChecked(true);
        } else if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus3))) {
            rbtn3.setChecked(true);
        }

        RadioGroup radioGroup = convertView.findViewById(R.id.listview_item_status_rbtgroup);
        final View finalConvertView = convertView;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = finalConvertView.findViewById(checkedId);

                if (radioButton.getId() == R.id.listview_item_rbtn_status2) {
                    item.setStatus(1);
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status2) {
                    item.setStatus(2);
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status3) {
                    item.setStatus(3);
                }

                // TODO: save status
                Log.d("TREK/TrekItemListAdapter.getChildView.Radiobutton.onChangeListener", "Item status changed for item " + item.getName() + " to " + radioButton.getText());
                Snackbar.make(finalConvertView, "Item status changed for item " + item.getName() + " to " + radioButton.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
 **/

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

        Log.d("TREK_TrekItemListAdaptr", "Getting group item #" + groupPosition + " with groupname " + item.getName());

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.editview_row_header_layout, null);
        }

        TextView headlineTextView = convertView
                .findViewById(R.id.editview_row_header_headline);
        headlineTextView.setText(item.getName());

        TextView textView = convertView
                .findViewById(R.id.editview_row_header_text);
        //textView.setText(String.valueOf(item.getWeight()) + " " + convertView.getContext().getString(R.string.term_kilogram));
        textView.setText("2 " + convertView.getContext().getString(R.string.term_pieces));

        ImageView imageView = convertView.findViewById(R.id.editview_row_header_image);
        String type = String.valueOf(item.getType());

        if (type.equals(convertView.getContext().getString(R.string.enum_itemtype1))) {
            imageView.setImageResource(R.drawable.idea);
        } else if (type.equals(convertView.getContext().getString(R.string.enum_itemtype2))) {
            imageView.setImageResource(R.drawable.food);
        } else if (type.equals(convertView.getContext().getString(R.string.enum_itemtype3))) {
            imageView.setImageResource(R.drawable.backpack);
        }

        ImageView addBtn = convertView.findViewById(R.id.editview_row_add_count_button);

        addBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked add count for item " + item.getName());
                Snackbar.make(view, "Clicked add count for item " + item.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //TODO: add and save trekitem count + 1
                //actionListener.onForwardButtonClick(item);

            }
        });

        ImageView subBtn = convertView.findViewById(R.id.editview_row_substract_count_button);

        subBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked substract count for item " + item.getName());
                Snackbar.make(view, "Clicked substract count for item " + item.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //TODO: add and save trekitem count 1 1
                //actionListener.onForwardButtonClick(item);

            }
        });

        ImageView delBtn = convertView.findViewById(R.id.editview_row_delete_button);

        subBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked delete for item " + item.getName());
                Snackbar.make(view, "Clicked delete for item " + item.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //TODO: delete item from trek
                //actionListener.onForwardButtonClick(item);

            }
        });

        return convertView;
    }
}