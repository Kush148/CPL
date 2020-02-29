package com.example.cpl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmenthome = inflater.inflate(R.layout.fragment_home,container,false);

        int  images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8};

        viewFlipper=fragmenthome.findViewById(R.id.vFlipper);

//        for(int i=0;i<images.length;i++)
//        {
//            imageFlipper(images[i]);
//        }
//
        for(int image:images)
        {
            imageFlipper(image);
        }

        return fragmenthome;
    }

    public void imageFlipper(int images)
    {
        ImageView imageView=new ImageView(getActivity());
        imageView.setBackgroundResource(images);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);

    }
}