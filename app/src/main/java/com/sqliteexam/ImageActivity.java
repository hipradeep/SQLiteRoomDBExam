package com.sqliteexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView=findViewById(R.id.imageView);

        Glide.with(this).load("https://i.ibb.co/kmgZjnb/trending-2.png").placeholder(R.drawable.ic_launcher_background).dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView);
    }
}