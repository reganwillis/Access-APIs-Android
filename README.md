# Access-APIs-Android

The HTTPSRequest class makes HTTPS GET requests to whatever URL you pass in. From MainActivity, you can pass an API query with specified parameters and a key that matches the JSON returned from the API. The HTTPSRequest class will run in a new thread to get the value that matches your key. For a deeper explanation of why the code was built this way, [click here]().

## Usage

To use this code in your application, you need the HTTPSRequest class and the queryAPI function from MainActivity. Copy the HTTPSRequest class into you Android application in the same directory as your MainActivity class. Be sure to add the package line at the top of the file. Finally, copy the queryAPI function into your MainActivity class. From that class, you can call the function like this:

    JSONObject response = queryAPI("https://example.com")

### Add Permissions

You'll also need to add some permissions to your AndroidManifest.xml file. Add them in above the application tag.

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

## Resources

Some code was used from an old repo of mine: [HTTP-Get-Requests](https://github.com/reganwillis/HTTP-GET-Requests).
