package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.TrekItem;

import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for trekitems
 */
public class TrekItemAdapter extends ExpandableListAdapter {

    private List<TrekItem> listRows;

    public TrekItemAdapter(Context context, ListFragment.ListViewActionListener listener) {

        super(context, listener);
    }

    public void setListRows(List<TrekItem> rows) {
        this.listRows = rows;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final TrekItem trekItem = (TrekItem) getChild(groupPosition, childPosition);

        Log.d("TREK_TrekItemListAdaptr", "Getting list trekItem #" + childPosition + " for group #" + groupPosition + " with itemname " + trekItem.getItem().getName());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.editview_row_item_content_layout, null);
        }

        // status field

        RadioButton rbtn1 = convertView.findViewById(R.id.editview_row_item_rbtn_status1);
        RadioButton rbtn2 = convertView.findViewById(R.id.editview_row_item_rbtn_status2);
        RadioButton rbtn3 = convertView.findViewById(R.id.editview_row_item_rbtn_status3);

        if (trekItem.getStatus().equals(convertView.getContext().getString(R.string.enum_trekitemstatus1))) {
            rbtn1.setChecked(true);
        } else if (trekItem.getStatus().equals(convertView.getContext().getString(R.string.enum_trekitemstatus2))) {
            rbtn2.setChecked(true);
        } else if (trekItem.getStatus().equals(convertView.getContext().getString(R.string.enum_trekitemstatus3))) {
            rbtn3.setChecked(true);
        }

        RadioGroup radioGroup = convertView.findViewById(R.id.editview_row_item_status_rbtgroup);
        final View finalConvertView = convertView;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = finalConvertView.findViewById(checkedId);

                if (radioButton.getId() == R.id.editview_row_item_rbtn_status1) {
                    trekItem.setStatus(finalConvertView.getContext().getString(R.string.enum_trekitemstatus1));
                } else if (radioButton.getId() == R.id.editview_row_item_rbtn_status2) {
                    trekItem.setStatus(finalConvertView.getContext().getString(R.string.enum_trekitemstatus2));
                } else if (radioButton.getId() == R.id.editview_row_item_rbtn_status3) {
                    trekItem.setStatus(finalConvertView.getContext().getString(R.string.enum_trekitemstatus3));
                }

                Log.d("TREK_TrekItemAdapter", "Item status changed for trekItem " + trekItem.getItem().getName() + " to " + trekItem.getStatus());
                viewActionListener.saveButtonClicked(trekItem);
            }
        });

        // was-used field
        CheckBox wasUsed = convertView.findViewById(R.id.editview_row_item_was_used_cb);

        if (trekItem.getWasUsed()) {
            wasUsed.setChecked(true);
        } else {
            wasUsed.setChecked(false);
        }

        wasUsed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                trekItem.setWasUsed(b);

                Log.d("TREK_TrekItemAdapter", "Was used changed for trekItem " + trekItem.getItem().getName() + " to " + trekItem.getWasUsed());
                viewActionListener.saveButtonClicked(trekItem);

            }
        });

        final EditText notes = convertView.findViewById(R.id.editview_row_item_notes_fld);

        notes.setText(trekItem.getNotes());

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                trekItem.setNotes(notes.getText().toString());

                Log.d("TREK_TrekItemAdapter", "Notes changed for trekItem " + trekItem.getItem().getName() + " to " + trekItem.getNotes());
                viewActionListener.saveButtonClicked(trekItem);
            }
        });


        return convertView;
    }

    public void remove(TrekItem trekItem) {
        this.listRows.remove(trekItem);
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

        final TrekItem trekItem = (TrekItem) getGroup(groupPosition);

        Log.d("TREK_TrekItemListAdaptr", "Getting group trekitem #" + groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.editview_row_header_layout, null);
        }

        TextView headlineTextView = convertView
                .findViewById(R.id.editview_row_header_headline);
        headlineTextView.setText(trekItem.getItem().getName());

        TextView textView = convertView
                .findViewById(R.id.editview_row_header_text);
        textView.setText(trekItem.getCount() + convertView.getContext().getString(R.string.term_pieces));

        ImageView imageView = convertView.findViewById(R.id.editview_row_header_image);

        if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype1))) {
            imageView.setImageResource(R.drawable.item);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype2))) {
            imageView.setImageResource(R.drawable.backpack);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype3))) {
            imageView.setImageResource(R.drawable.food);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype4))) {
            imageView.setImageResource(R.drawable.idea);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype5))) {
            imageView.setImageResource(R.drawable.remember);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype6))) {
            imageView.setImageResource(R.drawable.remember);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype7))) {
            imageView.setImageResource(R.drawable.task);
        } else if (trekItem.getItem().getType().equals(convertView.getContext().getString(R.string.enum_itemtype8))) {
            imageView.setImageResource(R.drawable.cutlery);
        }

        ImageView addBtn = convertView.findViewById(R.id.editview_row_add_count_button);

        addBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked add count for item " + trekItem.getItem().getName());

                trekItem.setCount(trekItem.getCount() + 1);
                viewActionListener.onModifyCountButtonClicked(trekItem);
                notifyDataSetChanged();

            }
        });

        ImageView subBtn = convertView.findViewById(R.id.editview_row_substract_count_button);

        subBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (trekItem.getCount() > 1) {
                    trekItem.setCount(trekItem.getCount() - 1);
                    viewActionListener.onModifyCountButtonClicked(trekItem);
                    notifyDataSetChanged();
                }

            }
        });

        ImageView delBtn = convertView.findViewById(R.id.editview_row_delete_button);

        delBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewActionListener.onDeleteButtonClicked(trekItem);
            }
        });

        return convertView;
    }

    public void add(TrekItem titem) {
        this.listRows.add(titem);
    }
}