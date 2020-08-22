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
    String TAG = "OctopocusView";

    private Paint mPaintGesture = new Paint();
    // encapsulates compound geometric path
    private Path mFeedbackPath = new Path();
    private Path mFeedforwardPath = new Path(); // I'm at the moment not sure, when to use this
    private Path mPrefixPath = new Path();      //  ""

    private Point mCurrentPos; // On which position is the finger at the momenent
    private Point mInitPos;    // Init position of the first finger tip

    Map<String, Object> mObjects = new HashMap<>();
    TemplateData mObjectData = new TemplateData();

    private Object mSelectedObject = null; // Not sure
    int mOBJECTSCALE = 1;     // ObjectScale should be dependent on the screen size
    int mMAXTHICKNESS = 10;   // ObjectScale should be dependent on the screen size

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
                mFeedbackPath.moveTo(touchX,touchY);
                showMenu();
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.v(TAGf, "MotionEvent ACTION_MOVE");
                mFeedbackPath.lineTo(touchX,touchY);
                Log.v(TAGf, "lineTo: " + touchX + ", " + touchY);

                mCurrentPos = new Point(touchX, touchY);

                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.v(TAGf, "MotionEvent ACTION_UP");
                //Log.v(TAGf, "moveTo: " + touchX + ", " + touchY);

                //
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
    showMenu shows the different path for the user. The Menu should be sized by the screen.
     */
    public void showMenu(){
        String TAGf = TAG + "showMenu";

        Log.v(TAGf, "showMenu opened");
        mObjects.put("Copy", new Object("Copy", mObjectData.copyPoints, "#ccccff", "#7f7fff", mOBJECTSCALE, mMAXTHICKNESS));
        mObjects.put("New Path: Copy", new Object("New Path: Copy", mObjectData.newCopyPath, "#7a7a7a", "#3b3b3b", mOBJECTSCALE, mMAXTHICKNESS));

        mObjects.put("Paste", new Object("Paste", mObjectData.pastePoints, "#8ae32b", "#208a18", mOBJECTSCALE, mMAXTHICKNESS));
        mObjects.put("New Path: Paste", new Object("New Path: Paste", mObjectData.newPastePath, "#7a7a7a", "#3b3b3b", mOBJECTSCALE, mMAXTHICKNESS));

        mObjects.put("Select", new Object("Select", mObjectData.selectPoints, "#FE642E", "#B43104", mOBJECTSCALE, mMAXTHICKNESS));
        mObjects.put("New Path: Select", new Object("New Path: Select", mObjectData.newSelectPath, "#7a7a7a", "#3b3b3b", mOBJECTSCALE, mMAXTHICKNESS));

        mObjects.put("Cut", new Object("Cut", mObjectData.cutPoints,"#c19465", "#513211", mOBJECTSCALE, mMAXTHICKNESS));
        mObjects.put("New Path: Cut", new Object("New Path: Cut", mObjectData.newCutPath, "#7a7a7a", "#3b3b3b", mOBJECTSCALE, mMAXTHICKNESS));
    }

    /*

     */
    private void drawObject(Canvas canvas, Object object) {
        mFeedforwardPath = new Path(); // Ist ein Pfad, wo ich noch nicht weiß, was er macht
        mPrefixPath = new Path();
        mFeedbackPath = new Path();

        // mInitPos ist die initiale Position, die
        // mInitPos gibt an, wo der Finger sich befindet, er wird initial gesetzt um das Menü zu setzen.
        //
        mFeedbackPath.moveTo((int) mInitPos.X, (int) mInitPos.Y);

        int prefix_end_index = object.getNearestPointToCursor(mInitPos, mCurrentPos);

        int[] points = object.getPoints();

        // Was macht diese For-Schleife?
        // Diese For-Schleife mal das Menü auf
        // TODO Weiterhin ist eine infiniti loop vorhanden. Ich vermute das liegt daran, da der Event Listener kein ordentliches Ende anzeigt.
        for (int x = 0; x < points.length; x += 2) {
            float x_pos = points[x] * mOBJECTSCALE + (int) mInitPos.X - points[0] * mOBJECTSCALE; // objects points to local space
            float y_pos = points[x + 1] * mOBJECTSCALE + (int) mInitPos.Y - points[1] * mOBJECTSCALE; // objects points to local space

            // float x_pos = points[x]; // objects points to local space
            // float y_pos = points[x + 1]; // objects points to local space

           if (x < object.getStartPos()) {
               mFeedbackPath.lineTo(x_pos, y_pos);

           } else if (x == object.getStartPos()) {
               mFeedbackPath.lineTo(x_pos, y_pos);
               mPrefixPath.moveTo(x_pos, y_pos);
               if (x >= points.length - 10) { // finger tip is near to the end of path
                   object.setExecute(true) ;
                   mSelectedObject = object;
               } else {
                   object.setExecute(false);
               }

           } else if (x < prefix_end_index) {
               mPrefixPath.lineTo(x_pos, y_pos);

           } else if (x == prefix_end_index) {
               mPrefixPath.lineTo(x_pos, y_pos);
               mFeedforwardPath.moveTo(x_pos, y_pos);
               canvas.drawText(object.getName(), x_pos, y_pos,  object.getTextPaint());

           } else {
               mFeedforwardPath.lineTo(x_pos, y_pos);
           }
        }

        // mFeedbackPaint.setStrokeWidth(object.getThickness());
        // canvas.drawPath(mFeedbackPath, mFeedbackPaint);

        // Auskommentiert dies verändert die Größe des Striches
        // mPaintGesture.setStrokeWidth(object.getThickness());
        // canvas.drawPath(mFeedbackPath, mPaintGesture);

        if (object.getThickness() != 0) {
            canvas.drawPath(mFeedforwardPath, object.getPathPaint());
            canvas.drawPath(mPrefixPath, object.getPrefixPaint());
        }
    }
}
