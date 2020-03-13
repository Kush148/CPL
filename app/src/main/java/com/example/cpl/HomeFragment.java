package com.example.cpl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    ViewFlipper viewFlipper;
    Animation slide_in_right, slide_out_left;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmenthome = inflater.inflate(R.layout.fragment_home,container,false);

        int  images[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8, R.drawable.image9,R.drawable.image10};

        viewFlipper=fragmenthome.findViewById(R.id.vFlipper);

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

        slide_in_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        slide_out_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);

        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_left);

    }
}