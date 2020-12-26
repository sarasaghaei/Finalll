package ir.sarasaghaei.final_project.Ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ir.sarasaghaei.final_project.R;

public class myanimatin extends View {
    public float height, width, cx, cv;
    Bitmap b;

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        b = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(b, cx, cv, null);
    }

    public myanimatin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    public myanimatin(Context context) {
        super(context);

        init();


    }
    public void init()
    {

        this.post(new Runnable() {
            @Override
            public void run() {
                height = getMeasuredHeight();
                width = getMeasuredWidth();
                cx = width/2-150;
                cv = height;
                invalidate();
            }
        });
    }


}
