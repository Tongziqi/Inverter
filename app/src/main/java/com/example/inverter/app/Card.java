package com.example.inverter.app;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * Created by Nino on 2014/7/30.
 */
public class Card extends FrameLayout {
    private TextView textView;
    private int num = 0;

    public Card(Context context) {

        super(context);
        setBackgroundColor(Color.parseColor("#ED945C"));
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(5, 5, 5, 5);
        textView = new TextView(getContext());
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(0x33ffffff);//文字背景
        addView(textView, layoutParams);
    }

    public void setNum(int num) {
        this.num = num;
        //textView.setText(num + "");//和空字符串相加 变成字符串 原来只是num资源的ID
    }

    public int getNum() {
        return num;
    }


}
