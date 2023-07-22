package com.bases;

import org.junit.After;
import utils.JSON_EDITOR;

import static locations.JSON_Locations.airPortIDS;
import static utils.JSON_EDITOR.CLEAN_JSON;

public class Airport_base {

    @After
    public void CleanJSON(){
        CLEAN_JSON(airPortIDS);
    }
}
