package jessica.com;

import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatBot {

    private List<ChatIntent> intents;

    public ChatBot(InputStream jsonStream) {
        // Load intents from JSON
        String jsonString = loadJSONFromAsset(jsonStream);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray intentsArray = jsonObject.getJSONArray("intents");

            intents = new ArrayList<>();

            for (int i = 0; i < intentsArray.length(); i++) {
                JSONObject intentObject = intentsArray.getJSONObject(i);
                ChatIntent intent = new ChatIntent(intentObject);
                intents.add(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getResponse(String userMessage) {
        Log.d("MyApp", "getResponse called with message: " + userMessage);
        String bestMatchIntentTag = null;
        int bestMatchLength = 0;

        // Match user message with intent patterns
        for (ChatIntent intent : intents) {
            System.out.println("Intent Tag: " + intent.getTag());

            List<String> patterns = intent.getPatterns();
            for (String pattern : patterns) {
                System.out.println("Pattern: " + pattern);

                boolean match = userMessage.toLowerCase().contains(pattern.toLowerCase());
                System.out.println("Match: " + match);

                if (match && pattern.length() > bestMatchLength) {
                    bestMatchLength = pattern.length();
                    bestMatchIntentTag = intent.getTag();
                }
            }
        }


        if (bestMatchIntentTag != null) {
            System.out.println("Best Match Intent Tag: " + bestMatchIntentTag);

            // Return the first response of the best matching intent
            for (ChatIntent intent : intents) {
                if (intent.getTag().equals(bestMatchIntentTag)) {
                    List<String> responses = intent.getResponses();
                    System.out.println("Bot Response: " + responses.get(0));
                    return responses.get(0);
                }
            }
        }

        // If no match found, return a default response
        System.out.println("Default Response: I'm sorry, I didn't understand that.");
        return "I'm sorry, I didn't understand that.";
    }


    private String loadJSONFromAsset(InputStream jsonStream) {
        // Read the JSON file content into a string
        try {
            byte[] buffer = new byte[jsonStream.available()];
            jsonStream.read(buffer);
            jsonStream.close();
            return new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

