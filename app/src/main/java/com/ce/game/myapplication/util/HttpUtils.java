package com.ce.game.myapplication.util;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    /**
     * Get request，get data
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr) {
        return doGet(urlStr, "");
    }

    /**
     * Get request，get data
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String doGet(String urlStr, String token) {
        HashMap<String, String> map = null;
        if (!TextUtils.isEmpty(token)) {
            map = new HashMap<>();
            map.put("Authorization", "Bearer " + token);
        }
        return doGet(urlStr, map);
    }

    public static String doGet(String urlStr, HashMap<String, String> mapRequestProperty) {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (mapRequestProperty != null && mapRequestProperty.size() > 0) {
                Iterator iterator = mapRequestProperty.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    String key = (String) entry.getKey();
                    String value = (String) entry.getValue();
                    conn.setRequestProperty(key, value);
                }
            }

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EndingU.closeSilently(is, baos);
            if (conn != null) conn.disconnect();
        }
        return null;
    }
}
