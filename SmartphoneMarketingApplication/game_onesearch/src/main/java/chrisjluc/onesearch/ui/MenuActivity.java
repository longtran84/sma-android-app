package chrisjluc.onesearch.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import chrisjluc.onesearch.R;
import chrisjluc.onesearch.base.BaseActivity;
import chrisjluc.onesearch.framework.WordSearchManager;
import chrisjluc.onesearch.models.GameDifficulty;
import chrisjluc.onesearch.models.GameMode;
import chrisjluc.onesearch.models.GameType;
import chrisjluc.onesearch.ui.gameplay.WordSearchActivity;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    private final static String MENU_PREF_NAME = "menu_prefs";
    private final static String FIRST_TIME = "first_time";

    private final static long ROUND_TIME_IN_MS = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = R.string.ga_menu_screen;
        // Check if first time opening app, show splash screen
        SharedPreferences prefs = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME, true);
//        if (isFirstTime) {
//            SharedPreferences.Editor editor = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE).edit();
//            editor.putBoolean(FIRST_TIME, false);
//            editor.apply();
//
//            Intent i = new Intent(getApplicationContext(), SplashActivity.class);
//            startActivity(i);
//        }

        setContentView(R.layout.activity_menu);

        findViewById(R.id.bMenuEasy).setOnClickListener(this);
        findViewById(R.id.bMenuMedium).setOnClickListener(this);
        findViewById(R.id.bMenuHard).setOnClickListener(this);
        //TODO: Reimplement advanced after more efficient way of drawing out the grid
//        findViewById(R.id.bMenuAdvanced).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String gd = null;
        int ga_button_id = -1;
        if (view.getId() == R.id.bMenuEasy) {
            gd = GameDifficulty.Easy;
            ga_button_id = R.string.ga_click_easy;
        } else if (view.getId() == R.id.bMenuMedium) {
            gd = GameDifficulty.Medium;
            ga_button_id = R.string.ga_click_medium;
        } else if (view.getId() == R.id.bMenuHard) {
            gd = GameDifficulty.Hard;
            ga_button_id = R.string.ga_click_hard;
        }
        analyticsTrackEvent(ga_button_id);
        WordSearchManager wsm = WordSearchManager.getInstance();
        wsm.Initialize(new GameMode(GameType.Timed, gd, ROUND_TIME_IN_MS), getApplicationContext());
        wsm.buildWordSearches();
        Intent i = new Intent(getApplicationContext(), WordSearchActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTrackScreen(getString(categoryId));
        WordSearchManager.nullify();
    }

}
