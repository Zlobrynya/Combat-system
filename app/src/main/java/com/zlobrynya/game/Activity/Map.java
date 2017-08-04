package com.zlobrynya.game.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.zlobrynya.game.ImageViewMap;
import com.zlobrynya.game.R;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP;

public class Map extends AppCompatActivity {
    private ImageViewMap imageViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        imageViewMap = (ImageViewMap) findViewById(R.id.imageViewMap);
        imageViewMap.setImage(ImageSource.resource(R.drawable.map_of_europe).tilingDisabled());
        imageViewMap.setMinimumScaleType(SCALE_TYPE_CENTER_CROP);
    }
}
