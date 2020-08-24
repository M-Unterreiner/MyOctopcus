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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Point mCurrentPos = new Point(0,0); // On which position is the finger at the moment
    private Point mInitPos;    // Init position of the first finger tip

    // Was genau macht dieses Object?
    private Object mSelectedObject = null; // Not sure

    // In mObjects are the Objects stored, which are drawn in the draw-methods
    Map<String, Object> mObjects = new HashMap<>();
    // TemplateData mObjectData = new TemplateData(); // commented out, these are at the moment in the Menu class

    // ####### Menu ########
    //Menu mFeedbackMenu = new Menu();
    Menu mFeedbackMenu = new Menu(this.getWidth(),this.getHeight());


    // ####### Dollar ########
    private List<Double> newPath = new ArrayList<>();
    private String mNewObjectName = "";

    private Dollar mDollar = new Dollar(1);


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

        mDollar.addPoint((int) mCurrentPos.X, (int) mCurrentPos.Y);

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

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v(TAGf, "MotionEvent ACTION_DOWN");
                Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);
                mInitPos = new Point (touchX, touchY);
                mCurrentPos = new Point(touchX, touchY);

                https://stackoverflow.com/questions/4299728/how-can-i-combine-two-hashmap-objects-containing-the-same-types
                mObjects.putAll(mFeedbackMenu.addMenu(mObjects));

                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.v(TAGf, "MotionEvent ACTION_MOVE");
               //  mFeedbackPath.lineTo(touchX,touchY);

                mCurrentPos = new Point(touchX, touchY);

                Log.v(TAGf, "MotionEvent: " + touchX + ", " + touchY + " mCurrentPos: " + mCurrentPos.X + " , " + mCurrentPos.Y + " " + mInitPos.X + " " + mInitPos.Y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                Log.v(TAGf, "MotionEvent ACTION_UP");
                //Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);
                mDollar.recognize();

                ((MainActivity) this.getContext()).writeDollar(mDollar);
                String execute_name = mDollar.result.Name;
                for (String objectName : mObjects.keySet()) {
                    Object object = mObjects.get(objectName);
                    if (object.getName().equals(execute_name)) {
                        object.setExecute(true);
                    }
                }

                mDollar.clear();


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
i     drawObject moves and draws the objects.
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
        int menu_end_index = object.getNearestPointToCursor(mInitPos, mCurrentPos);

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
               Log.v(TAGf, "if: x < object.getStartPos()");

           } else if (x == object.getStartPos()) {
               Log.v(TAGf, "else if = x == object.getStartPos");
               mFeedbackPath.lineTo(x_pos, y_pos);
               mMenuPath.moveTo(x_pos, y_pos);

               if (x >= points.length - 10) { // finger tip is near to the end of path
                   object.setExecute(true);
                   mSelectedObject = object;
               } else {
                   object.setExecute(false);
               }

           } else if (x < menu_end_index) {
               // When User didn't touch the screen, it is here
               // Log.v(TAGf, "else if = x < menu_end_index");

               // mMenuPath is drawing the path of the menu
               mMenuPath.lineTo(x_pos, y_pos);
           } else if (x == menu_end_index) {
               Log.v(TAGf, "else if == x == menu_end_index");
               mMenuPath.lineTo(x_pos, y_pos);
               mFeedforwardPath.moveTo(x_pos, y_pos);

               canvas.drawText(object.getName(), x_pos, y_pos, object.getTextPaint());
           } else {
               Log.v(TAGf, "else");
                mFeedforwardPath.lineTo(x_pos, y_pos);
           }
        }


        mPaintGesture.setStrokeWidth(object.getThickness());
        canvas.drawPath(mFeedbackPath, mPaintGesture);

        if (object.getThickness() != 0) {
            // canvas.drawPath(mFeedforwardPath, object.getPathPaint());

           //  canvas.drawPath(mPrefixPath, object.getPrefixPaint());
            canvas.drawPath(mMenuPath, object.getPrefixPaint());
        }
    }

private void clear() {
        String TAG = "MyView: clear";
        Log.v(TAG, "entered");


        // if (mSaveNewPath && mNoviceMode) {
        //     setNewPath();

        // } else {
            for (String objectName : mObjects.keySet()) {
                Object object = mObjects.get(objectName);
                if (object.getExcecute()) { // excecute function of command
                    ((MainActivity) this.getContext()).executeCommand(object.getName());
                    if (object.getName().length() < 10) {
                    } else {
                        String substring = object.getName().substring(0, 10);
                        if (substring.equals("New Path: ")) {

                            // mSaveNewPath = true;
                            // mNewObjectName = object.getName().substring(10, object.getName().length());
                            break;
                        } else {
                        }
                    }
                    mSelectedObject = object;
                    invalidate();
                }
            }
        //}


        mDollar.clear();

        // mTouchUp = true;
        newPath = new ArrayList<>();

        for (String object : mObjects.keySet()) {
            mObjects.get(object).clear();
        }
    }


}
