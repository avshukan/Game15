package com.avshukan.game15;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.R.attr.id;
import static android.R.attr.layout_height;
import static android.R.attr.onClick;
import static android.R.id.background;
import static com.avshukan.game15.R.id.text;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private B[] board;
    private Button emptyButton;
    private Button tempButton;
    private B emptyB;
    private B tempB;
    private int emptyN;

    private TextView tt;

    private void boardInit() {

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_id);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 16; i++) {
            Button button = new Button(this);
//            android:text="@string/N01"
//            android:id="@+id/buttonN01"
//            android:onClick="onButtonNClick"
//            android:layout_height="wrap_content"
//            android:layout_margin="@dimen/dpOne" />
            switch (i) {
                case 0:
                    button.setId(R.id.buttonN01);
                    break;
                case 1:
                    button.setId(R.id.buttonN02);
                    break;
                case 2:
                    button.setId(R.id.buttonN03);
                    break;
                case 3:
                    button.setId(R.id.buttonN04);
                    break;
                case 4:
                    button.setId(R.id.buttonN05);
                    break;
                case 5:
                    button.setId(R.id.buttonN06);
                    break;
                case 6:
                    button.setId(R.id.buttonN07);
                    break;
                case 7:
                    button.setId(R.id.buttonN08);
                    break;
                case 8:
                    button.setId(R.id.buttonN09);
                    break;
                case 9:
                    button.setId(R.id.buttonN10);
                    break;
                case 10:
                    button.setId(R.id.buttonN11);
                    break;
                case 11:
                    button.setId(R.id.buttonN12);
                    break;
                case 12:
                    button.setId(R.id.buttonN13);
                    break;
                case 13:
                    button.setId(R.id.buttonN14);
                    break;
                case 14:
                    button.setId(R.id.buttonN15);
                    break;
                case 15:
                    button.setId(R.id.buttonN00);
                    break;
            }
            button.setLayoutParams(layoutParams);
            button.setBackgroundResource(R.drawable.button_black);
            button.setOnClickListener(OCL);
            gridLayout.addView(button, layoutParams);
            board[i] = new B();
            board[i].button = button;
            board[i].topId = i - 4;
            board[i].bottomId = i + 4;
            board[i].leftId = i - 1;
            board[i].rightId = i + 1;
            if (i == 15) {
                board[i].setOriginN(0);
            } else {
                board[i].setOriginN(i + 1);
            }
        }
        emptyN = 15;
        board[emptyN].erase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        board = new B[16];
        boardInit();
        mixing();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void onExitClick(View view) {
        this.finishAffinity();
    }

    public void onNewClick(View view) {
        mixing();
    }

    public void mixing() {

        for (int i = 0; i < 16; i++) {
            board[i].setCurrentN(board[i].getOriginN());
            board[i].button.setVisibility(board[i].button.VISIBLE);
        }
        emptyN = 15;
        board[emptyN].erase();
        int newCell = -1;
        int direction; // направление
        for (int i = 0; i < 128; i++) {
            direction = 0;
            switch (emptyN) {
                // угловые кнопки (2 варианта направлений)
                case 0:
                    direction = 2 * (new Random().nextInt(2)) + 1;
                    break;
                case 3:
                    direction = (new Random().nextInt(2)) + 1;
                    break;
                case 12:
                    direction = 3 * (new Random().nextInt(2));
                    break;
                case 15:
                    direction = 2 * (new Random().nextInt(2));
                    break;
                // боковые кнопки (3 варианта направлений)
                case 1:
                case 2:
                    direction = (new Random().nextInt(3)) + 1;
                    break;
                case 4:
                case 8:
                    direction = (new Random().nextInt(3));
                    if (direction == 2) {
                        direction = 3;
                    }
                    ;
                    break;
                case 7:
                case 11:
                    direction = (new Random().nextInt(3));
                    break;
                case 13:
                case 14:
                    direction = (new Random().nextInt(3));
                    if (direction == 1) {
                        direction = 3;
                    }
                    ;
                    break;
// центральные клетки (4 варианта направлений)
                case 5:
                case 6:
                case 9:
                case 10:
                    direction = (new Random().nextInt(4));

            }
            switch (direction) {
                case 0:
                    newCell = board[emptyN].topId;
                    break;
                case 1:
                    newCell = board[emptyN].bottomId;
                    break;
                case 2:
                    newCell = board[emptyN].leftId;
                    break;
                case 3:
                    newCell = board[emptyN].rightId;
                    break;
            }
            if (newCell > -1) {
                board[newCell].move();
            }
        }
    }

    public OnClickListener OCL = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            int N;
            N = 0;
            for (int i = 0; i < 16; i++) {
                if (Integer.valueOf(id).equals(board[i].button.getId())) {
                    N = i;
                    break;
                }
            }
//        tt.setText(board[N].getOriginN() + " - " + board[N].getCurrentN());
            board[N].move();
            boolean checkWin = true;
            for (int i = 0; i < 16; i++) {
                if (board[i].getOriginN() != board[i].getCurrentN()) {
                    checkWin = false;
                }
            }
            if (checkWin) {
//            tt.setText("Win!");
                showWin();
            }
        }
    };

    public void showWin() {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getApplicationContext(),
                "Победа!",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    class B {
        int leftId;
        int rightId;
        int topId;
        int bottomId;
        private int originN;
        private int currentN;
        private Button button;

        void erase() {
            this.setCurrentN(0);
//            this.button.setText(getResources().getString(R.string.N00));
            this.button.setVisibility(this.button.GONE);
            for (int i = 0; i < 16; i++) {
                boolean checkMoveable = (this.topId == i || this.bottomId == i || this.leftId == i || this.rightId == i);
                board[i].button.setEnabled(checkMoveable);
            }
        }

        void move() {
            board[emptyN].setCurrentN(this.getCurrentN());
            board[emptyN].button.setVisibility(board[emptyN].button.VISIBLE);
            board[emptyN].button.setText(this.button.getText());
            if (this.getOriginN() == 0) {
                emptyN = 15;
            } else {
                emptyN = this.getOriginN() - 1;
            }
            this.erase();
        }


        int getCurrentN() {
            return currentN;
        }

        void setCurrentN(int currentN) {

            this.currentN = currentN;
            switch (currentN) {
                case 0:
                    this.button.setText(R.string.N00);
                    break;
                case 1:
                    this.button.setText(R.string.N01);
                    break;
                case 2:
                    this.button.setText(R.string.N02);
                    break;
                case 3:
                    this.button.setText(R.string.N03);
                    break;
                case 4:
                    this.button.setText(R.string.N04);
                    break;
                case 5:
                    this.button.setText(R.string.N05);
                    break;
                case 6:
                    this.button.setText(R.string.N06);
                    break;
                case 7:
                    this.button.setText(R.string.N07);
                    break;
                case 8:
                    this.button.setText(R.string.N08);
                    break;
                case 9:
                    this.button.setText(R.string.N09);
                    break;
                case 10:
                    this.button.setText(R.string.N10);
                    break;
                case 11:
                    this.button.setText(R.string.N11);
                    break;
                case 12:
                    this.button.setText(R.string.N12);
                    break;
                case 13:
                    this.button.setText(R.string.N13);
                    break;
                case 14:
                    this.button.setText(R.string.N14);
                    break;
                case 15:
                    this.button.setText(R.string.N15);
                    break;
            }
            if (this.currentN == this.originN) {
//                this.button.setBackgroundResource(R.color.colorGreen);
                this.button.setBackgroundResource(R.drawable.button_black);
                this.button.setTextColor(R.color.colorGreen);
            } else {
//                this.button.setBackgroundResource(R.color.colorRed);
                this.button.setBackgroundResource(R.drawable.button_red);
                this.button.setTextColor(R.color.colorRed);
            }
        }

        public int getOriginN() {
            return originN;
        }

        void setOriginN(int originN) {
            this.originN = originN;
            this.setCurrentN(originN);
        }
    }
}
