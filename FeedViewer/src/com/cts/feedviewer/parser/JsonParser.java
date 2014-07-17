
package com.cts.feedviewer.parser;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cts.feedviewer.model.Feed;
import com.cts.feedviewer.model.FeedCollection;

/**
 * Utilty class to parse the Json strings in an Async task, create Feeds and
 * store them to FeedCollection. As soon as data will be parsed,JsonParserListener will be notified.
 * 
 * @author 330016
 */
public class JsonParser {

    private JsonParserListener listener;

    private FeedCollection feedCollection;

    private Context context;

    public static final String jsonPath = "facts.json";

    private static final String TAG = JsonParser.class.getSimpleName();

    public JsonParser(Context context, JsonParserListener listener) {
        this.context = context;
        this.listener = listener;
        feedCollection = FeedCollection.getFeedCollection();
        feedCollection.clearAll();
    }

    public void parseData() {
        new AsyncParser().execute();
    }

    /**
     * This class will take Json String from a asset folder file, parse Json, create Model Feed class
     *  and populate to FeedCollection in a background thread and notify the listener in UI thread.  
     * @author 330016
     *
     */
    class AsyncParser extends AsyncTask<String, String, String> {
        private JSONObject jsonObjectInput;

        @Override
        protected String doInBackground(String... arg0) {
            try {
                this.jsonObjectInput = new JSONObject(loadJSONFromAsset());
                if (jsonObjectInput != null) {
                    String pageTitle = jsonObjectInput.getString("title");
                    feedCollection.setPageTitle(pageTitle);
                    JSONArray rows = jsonObjectInput.getJSONArray("rows");
                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject row = rows.getJSONObject(i);
                        String title = row.getString("title");
                        String description = row.getString("description");
                        String imageRef = row.getString("imageHref");
                        Feed feed = new Feed();
                        feedCollection.addFeed(feed.setTitle(title).setDescription(description)
                                .setImageUrl(imageRef));
                    }
                }
            } catch (Exception e) {
                Log.i(TAG, "Exception during parsing " + e.toString());
            }
            return null;
        }

        /**
         * Done with the parsing, so I will notify my listener on this.
         */
        protected void onPostExecute(String file_url) {
            listener.onParsingCompleteNotify();
        }

        /**
         * Below method refers file from asset folder and converts as String 
         * @return Json string
         */
        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = context.getAssets().open(jsonPath);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;

        }

    }

}
