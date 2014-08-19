package com.example.inverter.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by Nino on 2014/7/25.
 */
public class GameView extends GridLayout {
    Card card;
    int currentlevel = MainActivity.getMainActivity().CountLevel();
    //private Card[][] CardMap = new Card[10][10];//生成一个二维数组
    Card[][] CardMap = MainActivity.getMainActivity().CardMap;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame();
    }


    public void initGame() {

        setColumnCount(currentlevel);
        addCard(MainActivity.getMainActivity().Count() / currentlevel, MainActivity.getMainActivity().Count() / currentlevel);
    }

    private void addCard(int cardwidth, int cardheight) {

        for (int y = 0; y < currentlevel; y++) {
            for (int x = 0; x < currentlevel; x++) {
                card = new Card(getContext());
                card.setNum(0);
                addView(card, cardwidth, cardheight);
                CardMap[x][y] = card;  //把n*n张卡片写入二维数组
            }
        }
    }


    public void nextLevel() {
        currentlevel = currentlevel + 1;
        MainActivity.getMainActivity().CountLevel();
        MainActivity.getMainActivity().showlevel();
        MainActivity.getMainActivity().addlevel();
        MainActivity.getMainActivity().show_Bestlevel();//进入下一关后，bestlevel+1
        removeAllViews();
        setColumnCount(currentlevel);
        addCard(MainActivity.getMainActivity().Count() / currentlevel, MainActivity.getMainActivity().Count() / currentlevel);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                click(event);
//                MainActivity.getMainActivity().addClick();  不能在这里面算点击数，因为有可能点击到外部边界。
//                MainActivity.getMainActivity().showClick();
                Check_Complete();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void click(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Measure(x, y);
        Change_Colour();
    }

    /**
     * 根据点击屏幕的xy坐标来计算具体的位置
     * 再根据位置来改变点击card附近的颜色
     *
     * @param x 屏幕X的坐标
     * @param y 屏幕Y的坐标
     */
    public void Measure(int x, int y) {

        if (y <= MainActivity.getMainActivity().screenWidth) {
            int x_i = x / (MainActivity.getMainActivity().screenWidth / currentlevel);//点击点的X坐标
            int y_i = y / (MainActivity.getMainActivity().screenWidth / currentlevel);//点击点的y坐标
            Log.i("X坐标", String.valueOf(x_i));
            Log.i("Y坐标", String.valueOf(y_i));
            /**
             * 仅有（0，0）点，即平面是一个1*1的正方形 这时候不能根据点（0，0）来判断，只能根据等级来判断
             */
            if (currentlevel == 1) {
                CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                // Change_Colour(x_i, y_i);
            } else {
                /**
                 * （0，0）点
                 */
                if (x_i == 0 && y_i == 0) {
                    CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                    // Change_Colour(x_i, y_i);
                    CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                    // Change_Colour(x_i + 1, y_i);
                    CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                    // Change_Colour(x_i, y_i + 1);
                }
                /**
                 * （n，0）点 去掉（0，0）点 第一种情况为（最大,0）第二种为（任意，0）
                 *
                 * */
                if (y_i == 0) {
                    if (x_i == currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                        //  Change_Colour(x_i, y_i + 1);
                        CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                        //  Change_Colour(x_i - 1, y_i);
                    } else if (x_i != 0 && x_i != currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                        //  Change_Colour(x_i, y_i + 1);
                        CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                        //  Change_Colour(x_i - 1, y_i);
                        CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                        // Change_Colour(x_i + 1, y_i);
                    }
                }
                /**
                 * （0，n）点  同上
                 */
                if (x_i == 0) {
                    if (y_i == currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                        // Change_Colour(x_i + 1, y_i);
                        CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                        //  Change_Colour(x_i, y_i - 1);
                    } else if (y_i != 0 && y_i != currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        //  Change_Colour(x_i, y_i);
                        CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                        // Change_Colour(x_i + 1, y_i);
                        CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                        // Change_Colour(x_i, y_i - 1);
                        CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                        // Change_Colour(x_i, y_i + 1);
                    }
                }
                /**
                 * （n，n）点  （最大，最大）点和（最大，任意）点 出去以上特殊情况
                 *
                 * */
                if (x_i == currentlevel - 1) {
                    if (y_i == currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        //int m = CardMap[x_i][y_i].getNum();
                        // Log.i("M的值", String.valueOf(m));
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                        // Change_Colour(x_i - 1, y_i);
                        CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                        // Change_Colour(x_i, y_i - 1);
                    } else if (y_i != 0 && y_i != currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                        // Change_Colour(x_i - 1, y_i);
                        CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                        //   Change_Colour(x_i, y_i - 1);
                        CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                        // Change_Colour(x_i, y_i + 1);
                    }
                }
                /**
                 * 此处加上注释是因为不能再加1了，再加1后面颜色就判断不了了。
                 */
                if (y_i == currentlevel - 1) {
                    if (x_i == currentlevel - 1) {
//                    Change_Colour(x_i, y_i);
//                    Change_Colour(x_i - 1, y_i);
//                    Change_Colour(x_i, y_i - 1);
                    } else if (x_i != 0 && x_i != currentlevel - 1) {
                        CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                        // Change_Colour(x_i, y_i);
                        CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                        // Change_Colour(x_i - 1, y_i);
                        CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                        //Change_Colour(x_i, y_i - 1);
                        CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                        //Change_Colour(x_i + 1, y_i);
                    }
                }
                /**
                 * 此处为出去以上所有特殊点的任意点
                 */
                if (x_i != 0 && x_i != (currentlevel - 1) && y_i != 0 && y_i != (currentlevel - 1)) {
                    CardMap[x_i][y_i].setNum(CardMap[x_i][y_i].getNum() + 1);
                    // Change_Colour(x_i, y_i);
                    CardMap[x_i - 1][y_i].setNum(CardMap[x_i - 1][y_i].getNum() + 1);
                    //Change_Colour(x_i - 1, y_i);
                    CardMap[x_i][y_i - 1].setNum(CardMap[x_i][y_i - 1].getNum() + 1);
                    // Change_Colour(x_i, y_i - 1);
                    CardMap[x_i + 1][y_i].setNum(CardMap[x_i + 1][y_i].getNum() + 1);
                    //Change_Colour(x_i + 1, y_i);
                    CardMap[x_i][y_i + 1].setNum(CardMap[x_i][y_i + 1].getNum() + 1);
                    //Change_Colour(x_i, y_i + 1);
                }
            }
            MainActivity.getMainActivity().addClick();  //每一次有效点击增加一次点击数
            MainActivity.getMainActivity().showClick(); //显示点击数
        } else {
            Toast.makeText(MainActivity.getMainActivity(), "超过边界", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 这是最笨的方法 每一条后面后加一个检测 是否变色 可以加一个方法 遍历所有[X][Y]，再判断变色然后加到 click(event)后面。已经改过来！
     */
    public void Change_Colour() {
        for (int x = 0; x < currentlevel; x++) {
            for (int y = 0; y < currentlevel; y++) {
                if (CardMap[x][y].getNum() % 2 == 1) {
                    CardMap[x][y].setBackgroundColor(Color.parseColor("#5C90FF"));
                } else {
                    CardMap[x][y].setBackgroundColor(Color.parseColor("#ED945C"));
                }
            }
        }
    }

    public void Check_Complete() {
        boolean complete = true;
        for (int x = 0; x < currentlevel; x++) {
            for (int y = 0; y < currentlevel; y++) {
                if (CardMap[x][y].getNum() % 2 == 0) {
                    Log.i("未完成x", String.valueOf(x));
                    Log.i("未完成y", String.valueOf(y));
                    Log.i("空行", "***********************************************");
                    complete = false;
                    // break;   //跳出所有循环
                }
            }
        }
        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("过关啦").setMessage("您用了-->" + (MainActivity.getMainActivity().addClick() - 1) + " 步通过了第" + currentlevel + "关！继续努力！").setPositiveButton("下一关", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nextLevel();
                }
            }).show();
            MainActivity.getMainActivity().desClick();  //减去一次点击数 nextLevel()这个方法会增加一次点击
        }
    }
}

