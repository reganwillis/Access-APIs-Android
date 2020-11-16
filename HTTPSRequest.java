// Written by Regan Willis 2020

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class HTTPSRequest implements Runnable {
    private String URL;
    private HttpsURLConnection conn;
    private String content;
    private JSONObject response;
    private static final String ERROR_MSG = "\nError occured while querying API:\n";
    
    /**
     * Constructor creates an HTTPS Request.
     * @param URL - URL of API starting with "https"
     */
    public HTTPSRequest(String URL) {
        this.URL = URL;
    }

    /**
     * Connects to the API and gets the response (stored
     * in conn). Called when the start() function is called
     * on the thread from MainActivity.
     */
    @Override
    public void run() {
        URL site = null;

        try {
            site = new URL(URL);
            conn = (HttpsURLConnection) site.openConnection();
            conn.setRequestMethod("GET");
            content = readInputStream(conn.getInputStream());

            try {
                response = new JSONObject(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException | ProtocolException e) {
            System.out.println(ERROR_MSG);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(ERROR_MSG);
            e.printStackTrace();
        }
    }

    /**
     * Called from MainActivity after thread is finished running
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
     * Reads InputStream object into a String using
     * a BufferedReader.
     * @param stream - InputStream object returned from GET request
     * @return String of content from get request
     * @throws IOException
     */
    public String readInputStream(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String currLine;
        String insContent = "";

        while ((currLine = br.readLine()) != null) {
            insContent = insContent + currLine + "\n";
        }
        br.close();

        return insContent;
    }
}
