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

public class TemplateData {
    // Used this path from the paper
    // public static int copyPoints[] = {137, 139, 135, 141, 133, 144, 132, 146, 130, 149, 128, 151, 126, 155, 123, 160, 120, 166, 116, 171, 112, 177, 107, 183, 102, 188, 100, 191, 95, 195, 90, 199, 86, 203, 82, 206, 80, 209, 75, 213, 73, 213, 70, 216, 67, 219, 64, 221, 61, 223, 60, 225, 62, 226};
    // public static int pastePoints[] = {91, 185, 93, 185, 95, 185, 97, 185, 100, 188, 102, 189, 104, 190, 106, 193, 108, 195, 110, 198, 112, 201, 114, 204, 115, 207, 117, 210, 118, 212, 120, 214, 121, 217, 122, 219, 123, 222, 124, 224, 126, 226, 127, 229, 129, 231, 130, 233, 129, 231, 129, 228, 129, 226, 129, 224, 129, 221, 129, 218, 129, 212, 129, 208, 130, 198, 132, 189, 134, 182, 137, 173, 143, 164, 147, 157, 151, 151, 155, 144};
    // public static int selectPoints[] = {79, 245, 79, 242, 79, 239, 80, 237, 80, 234, 81, 232, 82, 230, 84, 224, 86, 220, 86, 218, 87, 216, 88, 213, 90, 207, 91, 202, 92, 200, 93, 194, 94, 192, 96, 189, 97, 186, 100, 179, 102, 173, 105, 165, 107, 160, 109, 158, 112, 151, 115, 144, 117, 139, 119, 136, 119, 134, 120, 132};
    // public static int cutPoints[] = {79, 245, 79, 242, 79, 239, 80, 237, 80, 234, 81, 232, 82, 230, 84, 224, 86, 220, 86, 218, 87, 216, 88, 213, 90, 207, 91, 202, 92, 200, 93, 194, 94, 192, 96, 189, 97, 186, 100, 179, 102, 173, 102, 173, 90, 173, 80, 173, 70, 173, 60, 173, 50, 173};
    // public static int NewPathPoints[] = {165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 165, 328, 166, 329, 169, 329, 172, 330, 175, 331, 179, 331, 183, 332, 188, 332, 194, 333, 199, 333, 204, 334, 209, 334, 214, 334, 218, 334, 222, 334, 225, 334, 227, 334, 229, 334, 231, 334, 232, 334, 234, 334, 234, 334, 235, 334, 236, 334, 236, 334, 237, 334, 237, 334, 237, 334, 237, 334, 237, 334, 237, 333, 237, 332, 239, 328, 242, 319, 245, 307, 247, 294, 248, 284, 249, 277, 250, 273, 250, 271, 250, 270, 250, 270, 250, 269, 250, 269, 250, 269, 250, 269};
    public static int copyPoints[] =   {137,139,135,141,133,144,132,146,130,149,128,151,126,155,123,160,120,166,116,171,112,177,107,183,102,188,100,191,95,195,90,199,86,203,82,206,80,209,75,213,73,213,70,216,67,219,64,221,61,223,60,225,62,226};
    public static int pastePoints[] =  {91,185,93,185,95,185,97,185,100,188,102,189,104,190,106,193,108,195,110,198,112,201,114,204,115,207,117,210,118,212,120,214,121,217,122,219,123,222,124,224,126,226,127,229,129,231,130,233,129,231,129,228,129,226,129,224,129,221,129,218,129,212,129,208,130,198,132,189,134,182,137,173,143,164,147,157,151,151,155,144};
    public static int selectPoints[] = {79,245,79,242,79,239,80,237,80,234,81,232,82,230,84,224,86,220,86,218,87,216,88,213,90,207,91,202,92,200,93,194,94,192,96,189,97,186,100,179,102,173,105,165,107,160,109,158,112,151,115,144,117,139,119,136,119,134,120,132};
    public static int cutPoints[] =    {79,245,79,242,79,239,80,237,80,234,81,232,82,230,84,224,86,220,86,218,87,216,88,213,90,207,91,202,92,200,93,194,94,192,96,189,97,186,100,179,102,173,102,173,90,173,80,173,70,173,60,173,50,173};



    public static int newCopyPath[];
    public static int newPastePath[];
    public static int newSelectPath[];
    public static int newCutPath[];


    static void copyMirror(int dst[], int src[]) {
        for (int i = 0; i < src.length / 2; i++) {
            dst[i * 2] = src[src.length - i * 2 - 2];
            dst[i * 2 + 1] = src[src.length - i * 2 + 1 - 2];

        }
    }

    // This is a static block! Haven't seen this before
    // Follow the link for further reading:
    // https://stackoverflow.com/questions/2943556/static-block-in-java

    }
