package com.autoai.android.toolkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangyanchao on 2017/6/8.
 */

public class EasySP {

    private static EasySP sEasySP;

    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;

    private static final String DEFAULT_SP_NAME = "SharedData";
    private static final int DEFAULT_INT = 0;
    private static final float DEFAULT_FLOAT = 0.0f;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final Set<String> DEFAULT_STRING_SET = new HashSet<>(0);

    private static String mCurSPName = DEFAULT_SP_NAME;
    private static Context mContext;

    private EasySP(Context context) {
        this(context, DEFAULT_SP_NAME);
    }

    private EasySP(Context context, String spName) {
        mContext = context.getApplicationContext();
        sSharedPreferences = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
        sEditor = sSharedPreferences.edit();
        mCurSPName = spName;
    }

    public static EasySP init(Context context) {
        if (sEasySP == null || !mCurSPName.equals(DEFAULT_SP_NAME)) {
            sEasySP = new EasySP(context);
        }
        return sEasySP;
    }

    public static EasySP init(Context context, String spName) {
        if (sEasySP == null) {
            sEasySP = new EasySP(context, spName);
        } else if (!spName.equals(mCurSPName)) {
            sEasySP = new EasySP(context, spName);
        }
        return sEasySP;
    }

    public EasySP put(int key, Object value) {
        return put(mContext.getString(key), value);
    }

    public EasySP put(String key, Object value) {

        if (value instanceof String) {
            sEditor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            sEditor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            sEditor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            sEditor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            sEditor.putLong(key, (Long) value);
        } else {
            sEditor.putString(key, value.toString());
        }
        return sEasySP;
    }

    public Object get(int key, Object defaultObject) {
        return get(mContext.getString(key), defaultObject);
    }

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sSharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sSharedPreferences.getInt(key, (int) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sSharedPreferences.getBoolean(key, (boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sSharedPreferences.getFloat(key, (float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sSharedPreferences.getLong(key, (long) defaultObject);
        }
        return null;
    }

    public EasySP putInt(String key, int value) {
        sEditor.putInt(key, value);
        return this;
    }

    public EasySP putInt(int key, int value) {
        return putInt(mContext.getString(key), value);
    }

    public int getInt(int key) {
        return getInt(mContext.getString(key));
    }

    public int getInt(int key, int defValue) {
        return getInt(mContext.getString(key), defValue);
    }

    public int getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }


    public int getInt(String key, int defValue) {
        return sSharedPreferences.getInt(key, defValue);
    }

    public EasySP putFloat(int key, float value) {
        return putFloat(mContext.getString(key), value);
    }

    public EasySP putFloat(String key, float value) {
        sEditor.putFloat(key, value);
        return sEasySP;
    }

    public float getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    public float getFloat(String key, float defValue) {
        return sSharedPreferences.getFloat(key, defValue);
    }

    public float getFloat(int key) {
        return getFloat(mContext.getString(key));
    }

    public float getFloat(int key, float defValue) {
        return getFloat(mContext.getString(key), defValue);
    }

    public EasySP putLong(int key, long value) {
        return putLong(mContext.getString(key), value);
    }

    public EasySP putLong(String key, long value) {
        sEditor.putLong(key, value);
        return sEasySP;
    }

    public long getLong(String key) {
        return getLong(key, DEFAULT_INT);
    }

    public long getLong(String key, long defValue) {
        return sSharedPreferences.getLong(key, defValue);
    }

    public long getLong(int key) {
        return getLong(mContext.getString(key));
    }

    public long getLong(int key, long defValue) {
        return getLong(mContext.getString(key), defValue);
    }

    public EasySP putString(int key, String value) {
        return putString(mContext.getString(key), value);
    }

    public EasySP putString(String key, String value) {
        sEditor.putString(key, value);
        return sEasySP;
    }

    public String getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public String getString(String key, String defValue) {
        return sSharedPreferences.getString(key, defValue);
    }

    public String getString(int key) {
        return getString(mContext.getString(key), DEFAULT_STRING);
    }

    public String getString(int key, String defValue) {
        return getString(mContext.getString(key), defValue);
    }

    public EasySP putBoolean(int key, boolean value) {
        return putBoolean(mContext.getString(key), value);
    }

    public EasySP putBoolean(String key, boolean value) {
        sEditor.putBoolean(key, value);
        return sEasySP;
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sSharedPreferences.getBoolean(key, defValue);
    }

    public boolean getBoolean(int key) {
        return getBoolean(mContext.getString(key));
    }

    public boolean getBoolean(int key, boolean defValue) {
        return getBoolean(mContext.getString(key), defValue);
    }

    public EasySP putStringSet(int key, Set<String> value) {
        return putStringSet(mContext.getString(key), value);
    }

    public EasySP putStringSet(String key, Set<String> value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            sEditor.putStringSet(key, value);
        }
        return sEasySP;
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, DEFAULT_STRING_SET);
    }


    public Set<String> getStringSet(String key, Set<String> defValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return sSharedPreferences.getStringSet(key, defValue);
        } else {
            return DEFAULT_STRING_SET;
        }
    }

    public Set<String> getStringSet(int key) {
        return getStringSet(mContext.getString(key));
    }

    public Set<String> getStringSet(int key, Set<String> defValue) {
        return getStringSet(mContext.getString(key), defValue);
    }


    public boolean contains(String key) {
        return sSharedPreferences.contains(key);
    }

    public boolean contains(int key) {
        return contains(mContext.getString(key));
    }

    public Map<String, ?> getAll() {
        return sSharedPreferences.getAll();
    }

    public EasySP remove(int key) {
        return remove(mContext.getString(key));
    }

    public EasySP remove(String key) {
        sEditor.remove(key);
        return sEasySP;
    }

    public EasySP clear() {
        sEditor.clear();
        return sEasySP;
    }

    public void commit() {
        sEditor.commit();
    }

    public SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

}
