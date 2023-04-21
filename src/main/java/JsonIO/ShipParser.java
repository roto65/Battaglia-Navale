package JsonIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.Nave;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ShipParser {

    // JSON --> Ship
    public static ArrayList<Nave> getShips(String fileName) {

        String path = "src/main/resources/json/" + fileName;

        Gson gson = new Gson();

        ArrayList<Nave> navi = null;

        try {
            FileReader fileReader = new FileReader(path);

            navi = gson.fromJson(fileReader, new TypeToken<ArrayList<Nave>>() {
            }.getType());
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return navi;
    }

    //Ship --> JSON
    public static void saveShips(ArrayList<Nave> navi, String fileName) {

        String path = "src/main/resources/json/" + fileName;

        try {
            if (!new File(path).exists()) {
                new File(path).createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        try {
            FileWriter fileWriter = new FileWriter(path);

            gson.toJson(navi, fileWriter);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

// riferimento: https://github.com/google/gson/blob/master/UserGuide.md
