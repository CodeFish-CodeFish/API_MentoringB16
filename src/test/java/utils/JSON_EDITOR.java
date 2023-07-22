package utils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JSON_EDITOR {


    public static void JSON_EDIT(File file,String realID1, String realID2){

        try {
            String jsonString = new String(Files.readAllBytes(file.toPath()));
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject.put("from", realID1);
            jsonObject.put("to", realID2);
            Files.write(file.toPath(), jsonObject.toString(2).getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }


    }


    public static void CLEAN_JSON(File file){
        try {
            String jsonString = new String(Files.readAllBytes(file.toPath()));
            JSONObject jsonObject = new JSONObject(jsonString);

            if (jsonObject.has("from") && jsonObject.has("to")) {
                jsonObject.put("from", JSONObject.NULL);
                jsonObject.put("to", JSONObject.NULL);
                Files.write(file.toPath(), jsonObject.toString(2).getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
