import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HTTPSRequest implements Runnable {
    private String URL;
    private HttpsURLConnection conn;
    private int responseCode;
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
            this.responseCode = conn.getResponseCode();
            Log.d("display", this.responseCode + " " + conn.getResponseCode());  // debug
        } catch (MalformedURLException | ProtocolException e) {
            System.out.println(ERROR_MSG);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(ERROR_MSG);
            e.printStackTrace();
        }
    }

    /**
     * Called from MainActivity after thread is finished running.
     * @return InputStream of API response
     * @throws IOException
     */
    public InputStream getResponse() throws IOException {
        return conn.getInputStream();
    }
}
