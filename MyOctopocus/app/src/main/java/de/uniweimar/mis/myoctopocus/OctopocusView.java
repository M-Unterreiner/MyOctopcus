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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class OctopocusView extends View {
    String TAG = "OctopocusView, ";

    // ######## Feedback Line ########
    private Paint mPaintGesture = new Paint();
    // encapsulates compound geometric path
    private Path mFeedbackPath = new Path();
    private Path mFeedforwardPath = new Path(); // I'm at the moment not sure, when to use this
    // private Path mPrefixPath = new Path();      // This should be the menu path
    private Path mMenuPath = new Path();      // This should be the menu path

    // ######## currentPos of the finger tip ########
    private Point mCurrentPos; // On which position is the finger at the moment
    private Point mInitPos;    // Init position of the first finger tip

    // Was genau macht dieses Object?
    private Object mSelectedObject = null; // Not sure

    // int mOBJECTSCALE = 1;     // ObjectScale should be dependent on the screen size
    // int mMAXTHICKNESS = 10;   // ObjectScale should be dependent on the screen size

    // In mObjects are the Objects stored, which are drawn in the draw-methods
    Map<String, Object> mObjects = new HashMap<>();
    // TemplateData mObjectData = new TemplateData(); // commented out, these are at the moment in the Menu class

    // ####### Menu ########
    //Menu mFeedbackMenu = new Menu();
    Menu mFeedbackMenu = new Menu(this.getWidth(),this.getHeight());



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
    Init sets the properties of the FeedbackLine = mPaintGesture and create the Menu
     */
    public void init(){
        mPaintGesture.setColor(Color.RED);
        // To avoid pixels
        mPaintGesture.setAntiAlias(true);
        mPaintGesture.setStyle(Paint.Style.STROKE);
        mPaintGesture.setStrokeWidth(10);


    }

    /*
    onDraw is drawing the feedback path, it doesn't overwrite a onDraw method
     */
    @Override
    public void onDraw(Canvas canvas){
        String TAGf = TAG + "onDraw";
        super.onDraw(canvas);

        Log.v(TAGf, "was entered");
        canvas.drawPath(mFeedbackPath, mPaintGesture);
        for (String object : mObjects.keySet()) {
            mObjects.get(object).setStartPosition(mInitPos, mCurrentPos); // current start position in object

            drawObject(canvas, mObjects.get(object));
        }

        invalidate();
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

        // TODO Hier könnnte der Grund der unendlichkeits-Schleife liegen
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v(TAGf, "MotionEvent ACTION_DOWN");
                Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);
                mInitPos = new Point (touchX, touchY);
                mCurrentPos = new Point(touchX, touchY);

                // TODO set startPoint for the menu!
                // mFeedbackPath.moveTo(touchX,touchY);

                https://stackoverflow.com/questions/4299728/how-can-i-combine-two-hashmap-objects-containing-the-same-types
                mObjects.putAll(mFeedbackMenu.addMenu(mObjects));

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.v(TAGf, "MotionEvent ACTION_MOVE");
               //  mFeedbackPath.lineTo(touchX,touchY);
                Log.v(TAGf, "lineTo: " + touchX + ", " + touchY);

                mCurrentPos = new Point(touchX, touchY);

                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                Log.v(TAGf, "MotionEvent ACTION_UP");
                //Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);

            case MotionEvent.ACTION_CANCEL:
                Log.v(TAGf, "MotionEvent ACTION_CANCEL");
                Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);

                // mCurrentPos = null;
                invalidate();
                break;
        }

        // return true is important to clarify that the event was handled successfully
        return true;
        //return super.onTouchEvent(event);
    }


    /*
i     drawObject does only draw the the Object
      mPrefixPath = is drawing the path of the menu
      mFeedbackPath is drawing the cut path (?)
      mInitPos gibt an, wo der Finger sich befindet, er wird initial gesetzt um das Menü zu setzen.
      int prefix_end_index is the difference between mInitPos and mCurrentPos
     */
    private void drawObject(Canvas canvas, Object object) {
        String TAGf = TAG + "drawObject";

        Log.v(TAGf, "was entered");
        mFeedforwardPath = new Path(); // Ist ein Pfad, wo ich noch nicht weiß, was er macht
        //mPrefixPath = new Path();
        mMenuPath = new Path();
        mFeedbackPath = new Path();

        mFeedbackPath.moveTo((int) mInitPos.X, (int) mInitPos.Y);

        // I fixed this size
        int menu_end_index = 50; //object.getNearestPointToCursor(mInitPos, mCurrentPos);

        int[] points = object.getPoints();

        // This loop is drawing all points
        for (int x = 0; x < points.length; x += 2) {
            // This 2 lines are setting the correct position of the finger
            // float x_pos = points[x] * mOBJECTSCALE + (int) mInitPos.X - points[0] * mOBJECTSCALE; // objects points to local space
            // float y_pos = points[x + 1] * mOBJECTSCALE + (int) mInitPos.Y - points[1] * mOBJECTSCALE; // objects points to local space

            float x_pos = points[x]     + (int) mInitPos.X - points[0]; // objects points to local space
            float y_pos = points[x + 1] + (int) mInitPos.Y - points[1]; // objects points to local space

            Log.v(TAGf, "x_pos: " + x_pos + " y_pos: " + y_pos);

           if (x < object.getStartPos()) {
               mFeedbackPath.lineTo(x_pos, y_pos);
           } else if (x == object.getStartPos()) {
               mFeedbackPath.lineTo(x_pos, y_pos);
               mMenuPath.moveTo(x_pos, y_pos);
               if (x >= points.length - 10) { // finger tip is near to the end of path
                   object.setExecute(true);
                   mSelectedObject = object;
               } else {
                   object.setExecute(false);
               }
           } else if (x < menu_end_index) {
               // mMenuPath is drawing the path of the menu
               mMenuPath.lineTo(x_pos, y_pos);
           } else if (x == menu_end_index) {
               mMenuPath.lineTo(x_pos, y_pos);
               mFeedforwardPath.moveTo(x_pos, y_pos);
               canvas.drawText(object.getName(), x_pos, y_pos, object.getTextPaint());
           } else {
                mFeedforwardPath.lineTo(x_pos, y_pos);
           }

           // mPrefixPath.lineTo(x_pos, y_pos);
           // mFeedbackPath.lineTo(x_pos, y_pos);
           //canvas.drawText(object.getName(), x_pos, y_pos,  object.getTextPaint());
        }

        Log.v(TAGf, "for end ended");
        // mFeedbackPaint.setStrokeWidth(object.getThickness());
        // canvas.drawPath(mFeedbackPath, mFeedbackPaint);

        // Auskommentiert dies verändert die Größe des Striches
        // mPaintGesture.setStrokeWidth(object.getThickness());


        if (object.getThickness() != 0) {
            canvas.drawPath(mFeedforwardPath, object.getPathPaint());

            // canvas.drawPath(mPrefixPath, object.getPrefixPaint());
            canvas.drawPath(mMenuPath, object.getPrefixPaint());
        }
    }
}
