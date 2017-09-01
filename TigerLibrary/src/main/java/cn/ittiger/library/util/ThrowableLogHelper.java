package cn.ittiger.library.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 17-2-13.
 */
public class ThrowableLogHelper {

    public static void logThrowable(String dir, Throwable throwable) {

        File logDirFile = new File(dir);
        boolean mkSuccess = false;
        if (!logDirFile.isDirectory()) {
            mkSuccess = logDirFile.mkdirs();
            if (!mkSuccess) {
                mkSuccess = logDirFile.mkdirs();
            }
        }

        StringBuffer sb = new StringBuffer();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String time = formatter.format(new Date());
            File logFile = new File(dir, time + ".log");
            if(!logFile.exists()) {
                logFile.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(logFile);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("ThrowableLogHelper", "an error occured while writing file...", e);
        }
    }
}
