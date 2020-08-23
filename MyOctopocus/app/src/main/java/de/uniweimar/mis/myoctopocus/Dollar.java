/* -------------------------------------------------------------------------
 *
 *	$1 Java
 *
 * 	This is a Java port of the $1 Gesture Recognizer by
 *	Jacob O. Wobbrock, Andrew D. Wilson, Yang Li.
 * 
 *	"The $1 Unistroke Recognizer is a 2-D single-stroke recognizer designed for 
 *	rapid prototyping of gesture-based user interfaces."
 *	 
 *	http://depts.washington.edu/aimgroup/proj/dollar/
 *
 *	Copyright (C) 2009, Alex Olwal, www.olwal.com
 *
 *	$1 Java free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	$1 Java is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with $1 Java.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  -------------------------------------------------------------------------
 */

package de.uniweimar.mis.myoctopocus;


import android.util.Log;
import android.widget.Toast;

import java.util.Vector;

public class Dollar implements TouchListener
{
	String TAG = "Dollar";

	protected int x, y;
	protected int state;
	protected int _key = -1;
	protected boolean gesture = true;
	protected Vector points = new Vector(1000);
	protected Recognizer recognizer;

	public Result result = new Result("no gesture", 0, -1);
	protected boolean active = true;
	protected DollarListener listener = null;
	public static final int GESTURES_DEFAULT = 1;
	public static final int GESTURES_SIMPLE = 2;
	public static final int GESTURES_CIRCLES = 3;
	protected int gestureSet;
	public Dollar()
	{
		this(GESTURES_SIMPLE);
	}
	
	public Dollar(int gestureSet)
	{
		this.gestureSet = gestureSet;
		recognizer = new Recognizer(gestureSet);
	}
	
	public void setListener(DollarListener listener)
	{
		this.listener = listener;
	}

	public Vector getPoints()
	{
		return points;
	}
	
	public void addPoint(int x, int y)
	{

		if (!active)
			return;
		
		points.addElement(new Point(x, y));
//		System.out.println(x + " " + y + " " + points.size());
	}
	
	public void recognize()
	{
		String TAGf = TAG + " recognize ";
		Log.v(TAGf, "entered");

		if (!active){
			Log.v(TAGf, "Is not active");
			return;
		}

		if (points.size() == 0) //the recognizer will crash if we try to process an empty set of points...
		{
			Log.v(TAGf, "Point size = 0");
			return;
		}
		
		result = recognizer.Recognize(points);
		Log.v(TAGf, "regognized something: " + result);

//        System.out.println("Result " + result.Name);
//		points.removeAllElements();
		
		if (listener != null) {
			listener.dollarDetected(this);
			Log.v(TAGf, "is not null, Dollar should detect something");
		}
	}

	public void setNewPoints(String name, int[] newPoints) {
		recognizer.setNewPath(name, newPoints);
	}

	public Rectangle getBoundingBox()
	{
		return recognizer.boundingBox;
	}
	
	public int[] getBounds()
	{
		return recognizer.bounds;
	}
	
	public Point getPosition()
	{
		return recognizer.centroid;
	}
	
	public String getName()
	{		
		return result.Name;
	}
	
	public double getScore()
	{
		return result.Score;
	}

	public int getIndex()
	{
		return result.Index;
	}

	public void setActive(boolean state)
	{
		active = state;
	}
	
	public boolean getActive()
	{
		return active;
	}	
	
	public void pointerPressed(int x, int y)
	{
		clear();
	}
	
	public void pointerReleased(int x, int y)
	{
		recognize();
	}
	
	public void pointerDragged(int x, int y)
	{
		addPoint(x, y);
	}
	
	public void clear()
	{
		String TAGf = TAG + "cleared Dollar";
		points.removeAllElements();
		result.Name = "";
		result.Score = 0;
		result.Index = -1;
	}
	
}