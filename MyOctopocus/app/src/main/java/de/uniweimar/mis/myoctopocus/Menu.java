package de.uniweimar.mis.myoctopocus;

import android.util.Log;

import java.util.Map;

public class Menu {
    String TAG = "Menu";


    TemplateData mObjectData = new TemplateData();
    int menuObjectScale = 1;     // ObjectScale should be dependent on the screen size
    int menuMaxThickness = 10;   // ObjectScale should be dependent on the screen size


    /*
    showMenu shows the different path for the user. The Menu should be sized by the screen.
    Innerhalb werden der HashMaps werden die Objekte abgelegt, die später gezeichnet werden.
     */
    public Map addMenu (Map menuObjects){
        String TAGf = TAG + "addMenu";

        Log.v(TAGf, "showMenu opened");
        menuObjects.put("Copy", new Object("Copy", mObjectData.copyPoints, "#ccccff", "#7f7fff", menuObjectScale, menuMaxThickness));
        menuObjects.put("New Path: Copy", new Object("New Path: Copy", mObjectData.newCopyPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMaxThickness));

        menuObjects.put("Paste", new Object("Paste", mObjectData.pastePoints, "#8ae32b", "#208a18", menuObjectScale, menuMaxThickness));
        menuObjects.put("New Path: Paste", new Object("New Path: Paste", mObjectData.newPastePath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMaxThickness));

        menuObjects.put("Select", new Object("Select", mObjectData.selectPoints, "#FE642E", "#B43104", menuObjectScale, menuMaxThickness));
        menuObjects.put("New Path: Select", new Object("New Path: Select", mObjectData.newSelectPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMaxThickness));

        menuObjects.put("Cut", new Object("Cut", mObjectData.cutPoints,"#c19465", "#513211", menuObjectScale, menuMaxThickness));
        menuObjects.put("New Path: Cut", new Object("New Path: Cut", mObjectData.newCutPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMaxThickness));

        return menuObjects;
    }




}
