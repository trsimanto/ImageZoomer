package com.towhid.imagezoomer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        // DecimalFormat rounds to 2 decimal places.
        final DecimalFormat df = new DecimalFormat("#.##");
        final com.ortiz.touchview.TouchImageView imageSingle = (com.ortiz.touchview.TouchImageView) findViewById(R.id.imageSingle);
        // Set the OnTouchImageViewListener which updates edit texts with zoom and scroll diagnostics.
        Picasso.get().load("https://rico2b.com/wp-content/uploads/2020/02/a8d968d6-688f-4194-a5f0-7e4c17b154eb1579909183209-4.jpg").into(imageSingle);
        imageSingle.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                PointF point = imageSingle.getScrollPosition();
                RectF rect = imageSingle.getZoomedRect();
                float currentZoom = imageSingle.getCurrentZoom();
                boolean isZoomed = imageSingle.isZoomed();
            }
        });


/*
        imageSingle.setOnTouchImageViewListener {
            val point = imageSingle.scrollPosition
            val rect = imageSingle.zoomedRect
            val currentZoom = imageSingle.currentZoom
            val isZoomed = imageSingle.isZoomed
            scroll_position.text = "x: " + df.format(point !!.x.toDouble())
            +" y: " + df.format(point.y.toDouble())
            zoomed_rect.text = ("left: " + df.format(rect.left.toDouble()) + " top: " + df.format(rect.top.toDouble())
                    + "\nright: " + df.format(rect.right.toDouble()) + " bottom: " + df.format(rect.bottom.toDouble()))
            current_zoom.text = "getCurrentZoom(): $currentZoom isZoomed(): $isZoomed"
        }*/


    }
}
