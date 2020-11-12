import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calls the HTTPRequest class to query an API with HTTPS
     * and Calls the IOHandler class to write to and read file.
     * @param URL - URL of API starting with "https"
     * @param resFile - name of file API response will be saved to
     * @return JSONObject holding API response | null
     */
    private JSONObject queryAPI(String URL, String resFile) {

        try {
            HTTPRequest httpRequest = new HTTPRequest(URL);
            Thread httpThread = new Thread(httpRequest);
            httpThread.start();
            httpThread.join();

            IOHandler io = new IOHandler(httpRequest.getResponse(), resFile, this);
            Thread ioThread = new Thread(io);
            ioThread.start();
            ioThread.join();

            return io.getResponse();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}