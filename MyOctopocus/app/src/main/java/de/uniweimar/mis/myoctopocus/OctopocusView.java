package de.uniweimar.mis.myoctopocus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class OctopocusView extends View {
    String TAG = "OctopocusView";

    private Paint mPaintGesture = new Paint();
    // encapsulates compound geometric path
    private Path mFeedbackPath = new Path();

    public OctopocusView(Context context) {
        super(context);
        init();
    }

    public OctopocusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OctopocusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        mPaintGesture.setColor(Color.RED);
        // To avoid pixels
        mPaintGesture.setAntiAlias(true);
        mPaintGesture.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(mFeedbackPath, mPaintGesture);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String TAGf = TAG + "onTouchEvent";

        float touchX = event.getX();
        float touchY = event.getY();

        // Log.v(TAGf, "X: " + touchX + " Y: " + touchY);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v(TAGf, "MotionEvent ACTION_DOWN");
                mFeedbackPath.moveTo(touchX,touchY);
            case MotionEvent.ACTION_MOVE:
               Log.v(TAGf, "MotionEvent ACTION_MOVE");
               mFeedbackPath.lineTo(touchX,touchY);
            case MotionEvent.ACTION_UP:
                Log.v(TAGf, "MotionEvent ACTION_MOVE");
            case MotionEvent.ACTION_CANCEL:
                Log.v(TAGf, "MotionEvent ACTION_CANCEL");

        }
        // invalidate();
        return super.onTouchEvent(event);
    }
}
