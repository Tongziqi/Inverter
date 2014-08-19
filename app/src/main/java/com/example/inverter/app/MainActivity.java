package com.example.inverter.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;


import static android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {


    public MainActivity() {
        mainActivity = this;   //从外界访问MainActivity
    } //构造函数

    private static MainActivity mainActivity = null;

    static MainActivity getMainActivity() {
        return mainActivity;
    }

    private Button Restart, NewGame, Instructions;
    private TextView CurrentLevel;
    TextView CurrentClicks = null;
    TextView BestLevel = null;
    int currentlevel = 0; //目前的等级，按照等级添加方块
    int screenWidth;//屏幕宽度（现在认为默认宽度小于长度）
    int clicks = 0;//点击屏幕的个数
    BestLevel bestLevel;
    public Card[][] CardMap = new Card[10][10];//生成一个二维数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Restart = (Button) findViewById(R.id.Restart);
        NewGame = (Button) findViewById(R.id.NewGame);
        Instructions = (Button) findViewById(R.id.Instructions);
        CurrentLevel = (TextView) findViewById(R.id.CurrentLevel);
        BestLevel = (TextView) findViewById(R.id.BestLevel);
        CurrentClicks = (TextView) findViewById(R.id.CurrentClicks);
        Restart.setOnClickListener(this);
        NewGame.setOnClickListener(this);
        Instructions.setOnClickListener(this);
        bestLevel = new BestLevel(this);   //切记初始化啊！！！！！！！！！！！！！！！
        showlevel();    //在主界面显示当前的Current Level
        addlevel();//在主界面显示当前的Best Level
        showClick();//显示当前的点击数
        show_Bestlevel();
    }

    /**
     * 由于是正方形，计算得到Spacing 是长宽也是每一个的间隔
     *
     * @return 长=宽=间隔
     */
    public int Count() {
        /*获得屏幕的宽和高*/
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        return screenWidth; //这个是宽度
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Restart:
                Restart_Game();
                break;
            case R.id.NewGame:
                new AlertDialog.Builder(this).setTitle("确定重新开始么？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.Instructions:
                new AlertDialog.Builder(this).setTitle("帮助").setMessage("怎么赢：把全部方块变成蓝色。\n" +
                        "怎么玩：单击一个小方块，改变它的颜色，所有的小方块共享边界。").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }


    /**
     * addlevel与用来比较currentlevel和bestLevel，得到bestlevel
     */

    public void addlevel() {
        if (currentlevel > bestLevel.getBestLevel()) {
            bestLevel.setBestLevel(currentlevel);
            //BestLevel.setText(bestLevel.getBestLevel() + "");
        }
    }

    public void show_Bestlevel() {
        BestLevel.setText(bestLevel.getBestLevel() + "");
    }

    /**
     * 得到的是当前的level
     *
     * @return currentlevel
     */
    public int CountLevel() {
        currentlevel += 1;
        return currentlevel;
    }

    public void showlevel() {
        CurrentLevel.setText(currentlevel + "");
    }


    public int addClick() {
        clicks += 1;
        return clicks;
    }

    public int desClick() {
        clicks -= 1;
        return clicks;
    }

    public void showClick() {
        CurrentClicks.setText(clicks + "");
    }

    public void Restart_Game() {
        for (int x = 0; x < currentlevel; x++) {
            for (int y = 0; y < currentlevel; y++) {
                CardMap[x][y].setNum(0);
                CardMap[x][y].setBackgroundColor(Color.parseColor("#ED945C"));
            }
        }
    }

}


