package access_apis;
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
     * Queries an API and gets state code that matches zipcode.
     * API - ZipCodeAPI.com - https://www.zipcodeapi.com/API#zipToLoc
     * @param zipcode - zipcode to query API with
     * @return String holding state code | null
     */
    private String getStateCode(int zipcode) {
        String key = "vz0njxQ785pz6YTqDCJcjKESlYrJIB16nlb8qINFOqlNKM3zyKEtM8BhiFL2Tb4n";
        String URL = "https://www.zipcodeapi.com/rest/" + key + "/info.json/" + zipcode + "/degrees";
        String resFile = zipcode + "_loc_info.JSON";
        JSONObject response = queryAPI(URL, resFile);
        String stateCode = null;
        String errorCode = null;

        try {
            stateCode = response.getString("state");

            return stateCode;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            errorCode = response.getString("error_code");
            String errorMSG = "\nProvided value (" + zipcode + ") for zipcode does not exist. Error code: " + errorCode + "\n";

            System.out.println(errorMSG);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Queries an API and gets value that corresponds with a key.
     * API - The COVID Tracking Project - https://covidtracking.com/data/api
     * @param zipcode - zipcode to query API with
     * @param key - JSON key to get value for (ex. "positive")
     * @return String holding value of key | null
     */
    private String accessCOVID19API(int zipcode, String key) {
        String domainName = "https://api.covidtracking.com";
        String stateCode = getStateCode(zipcode);

        if (stateCode != null) {
            stateCode = stateCode.toLowerCase();
            String params = "/v1/states/" + stateCode + "/current.json";
            String URL = domainName + params;
            String resFile = stateCode + "_COVID19_info.JSON";
            JSONObject response = queryAPI(URL, resFile);
            String value = null;

            try {
                value = response.get(key).toString();

                return value;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: getStateCode() returned null");
        }

        return null;
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