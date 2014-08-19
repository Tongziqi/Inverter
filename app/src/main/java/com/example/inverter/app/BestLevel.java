package com.example.inverter.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nino on 2014/7/25.
 */
public class BestLevel {
    private SharedPreferences sp;


    public BestLevel(Context context) {
        //读取perference文件，如果没有，则会创建一个名为BestLevel的文件
        sp = context.getSharedPreferences("BestLevel", context.MODE_PRIVATE);
    }

    /**
     * 用于读取最高分
     *
     * @return 最高分
     */
    public int getBestLevel() {
        //对去键“BestLevel”对应的值
        int bestLevel = sp.getInt("BestLevel", 0);
        return bestLevel;
    }

    /**
     * 用于写入最高分
     *
     * @param bestLevel 新的最高分
     */
    public void setBestLevel(int bestLevel) {
        //使用Editor类写入perference文件
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("BestLevel", bestLevel);
        editor.commit();
    }
}

