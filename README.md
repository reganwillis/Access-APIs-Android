# Access-APIs-Android

The HTTPSRequest class makes HTTPS GET requests to whatever URL you pass in. From MainActivity, you can pass an API query with specified parameters and a key that matches the JSON returned from the API. The HTTPSRequest class will run in a new thread to get the value that matches your key.

# link to article

# Usage

To use this code in your application, you need the HTTPSRequest class and the queryAPI function from MainActivity. Copy the HTTPSRequest class into you Android application in the same directory as your MainActivity class. Be sure to add the package line at the top of the file. Finally, copy the queryAPI function into your MainActivity class. From that class, you can call the function like this:

    JSONObject response = queryAPI("https://example.com")

### permissions in android manifest

# link to other http repo