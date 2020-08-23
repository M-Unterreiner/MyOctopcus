package de.uniweimar.mis.myoctopocus;

import android.util.Log;

import java.util.Map;

public class Menu {
    String TAG = "Menu";

    TemplateData mObjectData = new TemplateData();
    int menuObjectScale;     // ObjectScale should be dependent on the screen size
    int menuMenuThickness;   // ObjectScale should be dependent on the screen size

    public Menu() {
        menuObjectScale = 1;
        menuMenuThickness = 10;
    }

    /*
    This constructor is used if the scale is fixed
     */
    public Menu(int newScaleOfMenu, int newMaxMenuThickness) {
        setScale(newScaleOfMenu, newMaxMenuThickness);
    }

    /*
     Set the fixed scale of the menu
     TODO: Check if thickness is to small!
      */
    public void setScale(int newScaleOfMenu, int newMaxMenuThickness) {
        String TAGf = TAG + "setScale";
        menuObjectScale = newScaleOfMenu;
        if (newMaxMenuThickness < 10) {
            Log.v(TAGf, "newMaxNewThickness is to small. Set to 10");
            menuMenuThickness = 10;
            } else {
            menuMenuThickness = newMaxMenuThickness;
        }
    }

    /*
    showMenu shows the different path for the user. The Menu should be sized by the screen.
    In the maps are the objects stored, which are drawn later
     */
    public Map addMenu (Map menuObjects){
        String TAGf = TAG + "addMenu";

        Log.v(TAGf, "showMenu opened");
        menuObjects.put("Copy", new Object("Copy", mObjectData.copyPoints, "#ccccff", "#7f7fff", menuObjectScale, menuMenuThickness));
        menuObjects.put("Paste", new Object("Paste", mObjectData.pastePoints, "#8ae32b", "#208a18", menuObjectScale, menuMenuThickness));
        menuObjects.put("Select", new Object("Select", mObjectData.selectPoints, "#FE642E", "#B43104", menuObjectScale, menuMenuThickness));
        menuObjects.put("Cut", new Object("Cut", mObjectData.cutPoints,"#c19465", "#513211", menuObjectScale, menuMenuThickness));

        // menuObjects.put("New Path: Copy", new Object("New Path: Copy", mObjectData.newCopyPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMenuThickness));
        // menuObjects.put("New Path: Paste", new Object("New Path: Paste", mObjectData.newPastePath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMenuThickness));
        // menuObjects.put("New Path: Select", new Object("New Path: Select", mObjectData.newSelectPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMenuThickness));
        // menuObjects.put("New Path: Cut", new Object("New Path: Cut", mObjectData.newCutPath, "#7a7a7a", "#3b3b3b", menuObjectScale, menuMenuThickness));

        return menuObjects;
    }




}
