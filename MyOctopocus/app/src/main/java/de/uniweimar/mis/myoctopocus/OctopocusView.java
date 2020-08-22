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

import java.util.HashMap;
import java.util.Map;

public class OctopocusView extends View {
    String TAG = "OctopocusView";

    private Paint mPaintGesture = new Paint();
    // encapsulates compound geometric path
    private Path mFeedbackPath = new Path();

    Map<String, Object> mObjects = new HashMap<>();
    TemplateData mObjectData = new TemplateData();



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
    /*
    Init sets the properties of the drawingLine
     */
    public void init(){
        mPaintGesture.setColor(Color.RED);
        // To avoid pixels
        mPaintGesture.setAntiAlias(true);
        mPaintGesture.setStyle(Paint.Style.STROKE);
        mPaintGesture.setStrokeWidth(10);
    }

    /*
    onDraw is drawing the feedback path
     */
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(mFeedbackPath, mPaintGesture);
    }

    /*
    onTouchEvent is drawing the feedback line, important was the return true to show that the event
    was handled properly. If user is touching the screen a line drawn.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String TAGf = TAG + "onTouchEvent";

        float touchX = event.getX();
        float touchY = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v(TAGf, "MotionEvent ACTION_DOWN");
                Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);
                mFeedbackPath.moveTo(touchX,touchY);
                showMenu();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(TAGf, "MotionEvent ACTION_MOVE");
                mFeedbackPath.lineTo(touchX,touchY);
                Log.v(TAGf, "lineTo: " + touchX + ", " + touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAGf, "MotionEvent ACTION_UP");
                //Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);
                //
            case MotionEvent.ACTION_CANCEL:
                Log.v(TAGf, "MotionEvent ACTION_CANCEL");
                Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);

                invalidate();
                break;
        }

        // return true is important to clarify that the event was handled successfully
        return true;
        //return super.onTouchEvent(event);
    }

    /*
    showMenu shows the different path for the user
     */
    public void showMenu(){
        String TAGf = TAG + "showMenu";

        mObjects.put("Copy", new Object("Copy", mObjectData.copyPoints, "#ccccff", "#7f7fff", 3, 10));
        mObjects.put("New Path: Copy", new Object("New Path: Copy", mObjectData.newCopyPath, "#7a7a7a", "#3b3b3b", 3, 10));

    }
}
