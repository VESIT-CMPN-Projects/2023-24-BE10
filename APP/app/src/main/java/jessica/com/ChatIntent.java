package jessica.com;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatIntent {

    private List<String> patterns;
    private List<String> responses;
    private String tag;

    public ChatIntent(JSONObject intentObject) {
        try {
            tag = intentObject.getString("tag");
            patterns = jsonArrayToList(intentObject.getJSONArray("patterns"));
            responses = jsonArrayToList(intentObject.getJSONArray("responses"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public List<String> getResponses() {
        return responses;  // Fix: Changed from responses.get(0) to responses
    }

    public String getTag() {
        return tag;
    }
}
