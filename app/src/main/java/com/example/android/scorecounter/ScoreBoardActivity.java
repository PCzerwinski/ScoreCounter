package com.example.android.scorecounter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by SethQ on 2017-04-09.
 */

public class ScoreBoardActivity extends AppCompatActivity {

    private static int DISTANCE =150;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.score_board_activity);
        ArrayList<ScoreTeamContainer>  dataList = ( ArrayList<ScoreTeamContainer>) getIntent().getSerializableExtra("list");

        //Setting toolbar and back button
        Toolbar toolbar = (Toolbar) findViewById(R.id.score_board_toolbar);
        toolbar.setTitle("Score board");
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else
        {

            clearAllItems();
        }
        if (dataList.size()>0) loadData(dataList);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x1, x2;
        x1 = x2 =0;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case  MotionEvent.ACTION_UP:
                x2 = event.getX();
                float delta = x1 - x2;
                if (Math.abs(delta)>= DISTANCE && delta < 0)
                {
                    finish();
                }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(ArrayList<ScoreTeamContainer> data)
    {
        int matchScoreTeamA = 0;
        int matchScoreTeamB = 0;

        String teamAName = data.get(0).getTeamNameA();
        String teamBName = data.get(0).getTeamNameB();

        //Setting name of the colums
        TextView view = (TextView) findViewById(R.id.round_score_text_view);
        view.setText("Round");

        view = (TextView) findViewById(R.id.score_board_team_name_1);
        view.setText(teamAName);

        view = (TextView) findViewById(R.id.score_board_team_name_2);
        view.setText(teamBName);

        //Filling next rows with data

            for (int i = 0; i < data.size(); i++) {


                int scoreTeamA = data.get(i).getTeamScoreA();
                int scoreTeamB = data.get(i).getTeamScoreB();

                Log.i("Team A score", Integer.toString(scoreTeamA));
                Log.i("Team B score", Integer.toString(scoreTeamB));

                if (scoreTeamA > scoreTeamB) matchScoreTeamA += 1;
                if (scoreTeamA < scoreTeamB) matchScoreTeamB += 1;


                String formatTeamA = "(" + scoreTeamA + ") " + matchScoreTeamA;
                String formatTeamB = matchScoreTeamB + " (" + scoreTeamB + ")";
                createFields(Integer.toString(i + 1) + ".", formatTeamA, formatTeamB);


        }
    }

    private void createFields (String arg_1, String arg_2, String arg_3)
    {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.score_board_main_linear_layout);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //setting up layout fields
        TextView roundTextView = new TextView(this);
        roundTextView.setText(arg_1);
        roundTextView.setTextSize(16);
        roundTextView.setPadding(0, 0, 16, 8);
        roundTextView.setGravity(Gravity.RIGHT);
        // last parametr is an layout weight
        roundTextView.setLayoutParams(new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f));

        TextView teamATextView = new TextView(this);
        teamATextView.setText(arg_2);
        teamATextView.setTextSize(16);
        teamATextView.setPadding(0, 0, 20, 8);
        teamATextView.setGravity(Gravity.RIGHT);
        // last parametr is an layout weight
        teamATextView.setLayoutParams(new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView teamBTextView = new TextView(this);
        teamBTextView.setText(arg_3);
        teamBTextView.setTextSize(16);
        teamBTextView.setPadding(20, 0, 0, 8);
        teamBTextView.setGravity(Gravity.LEFT);
        // last parametr is an layout weight
        teamBTextView.setLayoutParams(new LinearLayout.LayoutParams (LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        imageParams.setMargins(16, 0, 16, 0);
        image.setLayoutParams(imageParams);

        image.setBackgroundColor(Color.parseColor("#a9a9a9"));

        layout.addView(roundTextView);
        layout.addView(teamATextView);
        layout.addView(teamBTextView);

        mainLayout.addView(image);
        mainLayout.addView(layout);

    }

    private void clearAllItems()
    {
        LinearLayout layout = (LinearLayout)  findViewById(R.id.score_board_main_linear_layout);
        layout.removeAllViews();
    }

}
