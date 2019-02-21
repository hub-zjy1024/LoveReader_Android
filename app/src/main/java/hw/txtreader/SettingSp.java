package hw.txtreader;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hw.txtreader.entity.Book;
import hw.txtreader.entity.ReadSetting;

/**
 Created by 张建宇 on 2019/2/1. */
public class SettingSp {
    public static String sp_Books = "recentBooks";
    public static String read_Setting = "read_setting";

    public static class ReadOptions{
        public static int def_fontsize = 40;
    }

    public static void clearBooks(Context mcontext) {
        SharedPreferences spBooks = mcontext.getSharedPreferences(sp_Books, Context.MODE_PRIVATE);
        spBooks.edit().clear();
    }

    public static void saveReadSetting(Context mcontext, ReadSetting setting) {
        SharedPreferences spSetting = mcontext.getSharedPreferences(read_Setting, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spSetting.edit();
        edit.putInt("fontSize", setting.textSize);
        edit.apply();
    }

    public static ReadSetting loadReadingSetting(Context mcontext) {
        ReadSetting setting = new ReadSetting();
        SharedPreferences spSetting = mcontext.getSharedPreferences(read_Setting, Context.MODE_PRIVATE);
        int fontSize = spSetting.getInt("fontSize", ReadOptions.def_fontsize);
        setting.textSize = fontSize;
        return setting;
    }

    public static List<Book> loadRecentList(Context mcontext) {

        List<Book> mBooks = new ArrayList<>();
        SharedPreferences spBooks = mcontext.getSharedPreferences(sp_Books, Context.MODE_PRIVATE);
        String json = "";
        String saveJson = spBooks.getString("books", "[]");
        try {
            Log.e("zjy", "SettingSp->loadRecentList(): saveJson==" + saveJson);
            JSONArray array = new JSONArray(saveJson);
            boolean hasExists = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Book b = new Book();
                b.filePath = obj.getString("filepath");
                mBooks.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBooks;
    }

    public static void saveBooks(Context mcontext, String path) {
        SharedPreferences spBooks = mcontext.getSharedPreferences(sp_Books, Context.MODE_PRIVATE);
        String json = "";
        Log.e("zjy", "SettingSp->saveBooks(): Saved==" + path);

        String saveJson = spBooks.getString("books", "[]");
        try {
            JSONArray array = new JSONArray(saveJson);
            boolean hasExists = false;
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String tpath = obj.getString("filepath");
                if (path != null && path.equals(tpath)) {
                    hasExists = true;
                }
            }
            Log.e("zjy", "SettingSp->saveBooks(): Saved==" + path);
            if (!hasExists) {
                JSONObject obj = new JSONObject();
                obj.put("filepath", path);
                array.put(obj);
                json = array.toString();
                spBooks.edit().putString("books", json).apply();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
