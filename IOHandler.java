package com.example.covidtrackerapp;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class IOHandler implements Runnable {
    InputStream stream;
    String responseFile;
    Context context;
    JSONObject response = null;

    /**
     * Constructor creates an instance of the IOHandler.
     * @param stream - InputStream from an HttpsURLConnection
     * @param responseFile - name of file to write to
     * @param context - the context of MainActivity
     */
    public IOHandler(InputStream stream, String responseFile, Context context) {
        this.stream = stream;
        this.responseFile = responseFile;
        this.context = context;
    }

    /**
     * Facilitates writing to and reading from file.
     * Called when the start() function is called
     * on the thread from MainActivity.
     */
    @Override
    public void run() {
        int bufferLength = writeResponseToFile();
        char[] buffer = readResponseFromFile(bufferLength);

        try {
            response = new JSONObject(new String(buffer));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets what was read from the file.
     * @return JSONObject holding what was read | null
     */
    public JSONObject getResponse() {

        if (response != null) {
            return response;
        } else {
            return null;
        }
    }

    /**
     * Writes API response to a file called responseFile.
     * @return length of characters in the file
     */
    private int writeResponseToFile() {
        FileOutputStream fileOut = null;

        try {
            fileOut = context.openFileOutput(responseFile, context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fileOut);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String currLine;
            int bufferLength = 0;

            while ((currLine = br.readLine()) != null) {
                bufferLength += currLine.length();
                osw.write(currLine);
                osw.write("\n");
            }
            osw.flush();
            osw.close();
            fileOut.close();
            br.close();

            return bufferLength;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Reads API response from responseFile.
     * @param bufferLength - length of characters in the file
     * @return the output of the file
     */
    private char[] readResponseFromFile(int bufferLength) {
        FileInputStream fileIn = null;

        try {
            fileIn = context.openFileInput(responseFile);
            InputStreamReader isr = new InputStreamReader(fileIn);
            char[] buffer = new char[bufferLength];
            isr.read(buffer);
            isr.close();
            fileIn.close();

            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
