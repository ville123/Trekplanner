package com.trekplanner.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trekplanner.app.R;

public class PictureFragment extends Fragment {

    private static PictureFragment instance;
    private Bitmap pic;

    public static PictureFragment getInstance() {
        Log.d("TREK_PictureFrag", "Returning PictureFragment -instance");
        // using singelton -pattern picturefrag
        if (instance == null) {
            instance = new PictureFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.picture_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.pic != null) {
            ImageView v = getActivity().findViewById(android.R.id.content).findViewById(R.id.picture_layout_imageview);
            v.setImageBitmap(this.pic);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.pic = null;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }
}
