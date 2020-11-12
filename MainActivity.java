import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calls the HTTPSRequest class to query an API with a GET request.
     * @param URL - URL of API starting with "https"
     * @return JSONObject holding API response | null
     */
    private JSONObject queryAPI(String URL) {

        try {
            HTTPSRequest httpsRequest = new HTTPSRequest(URL);
            Thread httpsThread = new Thread(httpsRequest);
            httpsThread.start();
            httpsThread.join();

            return httpsRequest.getResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}