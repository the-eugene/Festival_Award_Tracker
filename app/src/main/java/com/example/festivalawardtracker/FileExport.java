package com.example.festivalawardtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;

import java.io.File;

public class FileExport {

//    public void writeFile(String filename, Context context) {
//
//        File directoryDownload = context.getExternalFilesDir()
//        File logDir = new File(directoryDownload, "bpReader"); //Creates a new folder in DOWNLOAD directory
//        logDir.mkdirs();
//        File file = new File(logDir, filename);
//
//        FileOutputStream outputStream = null;
//        try {
//            outputStream = new FileOutputStream(file, true);
//            //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            for (int i = 0; i < uAverage.size(); i += 3) {
//                outputStream.write((getValues.get(i) + ",").getBytes());
//                outputStream.write((getValues.get(i + 1) + ",").getBytes());
//                outputStream.write((getValues.get(i + 2) + "\n").getBytes());
//
//            }
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
