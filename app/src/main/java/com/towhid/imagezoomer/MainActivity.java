package com.towhid.imagezoomer;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // DecimalFormat rounds to 2 decimal places.
        DecimalFormat df = new DecimalFormat("#.##");
        ImageView imageSingle=(ImageView)findViewById(R.id.imageEnhance);
        Picasso.get().load("https://rico2b.com/wp-content/uploads/2020/02/a8d968d6-688f-4194-a5f0-7e4c17b154eb1579909183209-4.jpg").into(imageSingle);
        // Set the OnTouchImageViewListener which updates edit texts with zoom and scroll diagnostics.
        imageSingle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub

                ImageView view = (ImageView) v;
                dumpEvent(event);
                // Handle touch events here...
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                       // Log.d(TAG, "mode=DRAG");
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                       // Log.d(TAG, "oldDist=" + oldDist);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                         //   Log.d(TAG, "mode=ZOOM");
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                      //  Log.d(TAG, "mode=NONE");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            // ...
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY()
                                    - start.y);
                        } else if (mode == ZOOM) {
                            float newDist = spacing(event);
                      //      Log.d(TAG, "newDist=" + newDist);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                        }
                        break;
                }
                view.setImageMatrix(matrix);
                return true;
            }
            private void dumpEvent(MotionEvent event) {
                String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                        "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
                StringBuilder sb = new StringBuilder();
                int action = event.getAction();
                int actionCode = action & MotionEvent.ACTION_MASK;
                sb.append("event ACTION_").append(names[actionCode]);
                if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                        || actionCode == MotionEvent.ACTION_POINTER_UP) {
                    sb.append("(pid ").append(
                            action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                    sb.append(")");
                }
                sb.append("[");
                for (int i = 0; i < event.getPointerCount(); i++) {
                    sb.append("#").append(i);
                    sb.append("(pid ").append(event.getPointerId(i));
                    sb.append(")=").append((int) event.getX(i));
                    sb.append(",").append((int) event.getY(i));
                    if (i + 1 < event.getPointerCount())
                        sb.append(";");
                }
                sb.append("]");
                //Log.d(TAG, sb.toString());
            }

            /** Determine the space between the first two fingers */
            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float)Math.sqrt(x * x + y * y);
            }

            /** Calculate the mid point of the first two fingers */
            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }

        });




       /* imageSingle.setOnTouchImageViewListener {
            val point = imageSingle.scrollPosition
            val rect = imageSingle.zoomedRect
            val currentZoom = imageSingle.currentZoom
            val isZoomed = imageSingle.isZoomed
            scroll_position.text = "x: " + df.format(point!!.x.toDouble()) + " y: " + df.format(point.y.toDouble())
            zoomed_rect.text = ("left: " + df.format(rect.left.toDouble()) + " top: " + df.format(rect.top.toDouble())
                    + "\nright: " + df.format(rect.right.toDouble()) + " bottom: " + df.format(rect.bottom.toDouble()))
            current_zoom.text = "getCurrentZoom(): $currentZoom isZoomed(): $isZoomed"
        }*/




    }
}
