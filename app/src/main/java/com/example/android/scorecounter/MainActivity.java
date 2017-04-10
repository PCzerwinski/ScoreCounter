package com.example.android.scorecounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int scoreTeamA;
    int scoreTeamB;

    private List <Integer> scoreTeamAContainer;
    private List <Integer> scoreTeamBContainer;

    private static int DISTANCE = 150;

    private List <ScoreTeamContainer> scoreMatchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTeamA = 0;
        scoreTeamB = 0;

        scoreTeamAContainer = new ArrayList<>();
        scoreTeamBContainer = new ArrayList<>();
        scoreMatchContainer = new ArrayList<>();

       Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);



        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                scoreTeamA = 0;
                scoreTeamB = 0;

                scoreTeamAContainer.clear();
                scoreTeamBContainer.clear();

                setScoreTeamA(scoreTeamA);
                setScoreTeamB(scoreTeamB);

                TextView teamA = (TextView) findViewById(R.id.round_team_a_text_view);
                TextView teamB = (TextView) findViewById(R.id.round_team_b_text_view);

                teamB.setText("0");
                teamA.setText("0");

                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x1 = 0;
        float x2 = 0;
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > DISTANCE && deltaX < 0)
                {
                    Intent intent = new Intent (this, ScoreBoardActivity.class);
                    intent.putExtra("list", (Serializable) scoreMatchContainer);
                    startActivity(intent);
                }
                if (Math.abs(deltaX) > DISTANCE && deltaX > 0)
                {

                }

                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_score_board)
        {
            Intent intent = new Intent (this, ScoreBoardActivity.class);
            intent.putExtra("list", (Serializable) scoreMatchContainer);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void  displayScoreTeamA(View view) {

        String name = getResources().getResourceName(view.getId());
        name = name.split("/")[1];
        switch (name) {
            case "team_a_score_1_point_button":
                scoreTeamA += 1;

                scoreTeamAContainer.add(1);

                setScoreTeamA(scoreTeamA);
                break;
            case "team_a_score_2_point_button":
                scoreTeamA += 2;
                setScoreTeamA(scoreTeamA);

                scoreTeamAContainer.add(2);

                break;
            case "team_a_score_3_point_button":
                scoreTeamA += 3;
                setScoreTeamA(scoreTeamA);

                scoreTeamAContainer.add(3);

                break;
            default:
                break;

        }
    }

    public void undoScore(View view)
    {
      String name= getResources().getResourceName(view.getId());
      name = name.split("/")[1];

        switch (name)
        {
            case "undo_team_a_button":
                if (scoreTeamA>0)
                {
                    scoreTeamA = scoreTeamA - scoreTeamAContainer.get(scoreTeamAContainer.size()-1);
                    scoreTeamAContainer.remove(scoreTeamAContainer.size()-1);
                    setScoreTeamA(scoreTeamA);
                }


                break;
            case "undo_team_b_button":
                if (scoreTeamB>0)
                {
                    scoreTeamB = scoreTeamB - scoreTeamBContainer.get(scoreTeamBContainer.size()-1);
                    scoreTeamBContainer.remove(scoreTeamBContainer.size()-1);
                    setScoreTeamB(scoreTeamB);
                }


        }
    }

    public void  displayScoreTeamB(View view)
    {
        String name=getResources().getResourceName(view.getId());
        name = name.split("/")[1];
        switch (name)
        {
            case "team_b_score_1_point_button":
                scoreTeamB += 1;
                scoreTeamBContainer.add(1);
                setScoreTeamB(scoreTeamB);
                break;
            case "team_b_score_2_point_button":
                scoreTeamB += 2;
                setScoreTeamB(scoreTeamB);
                scoreTeamBContainer.add(2);
                break;
            case "team_b_score_3_point_button":
                scoreTeamB += 3;
                setScoreTeamB(scoreTeamB);
                scoreTeamBContainer.add(3);
                break;
            default:
                break;

        }
    }

    public void newRound(View view)
    {
        TextView teamA = (TextView) findViewById(R.id.round_team_a_text_view);
        TextView teamB = (TextView) findViewById(R.id.round_team_b_text_view);
        int intTeamA = 0;
        int intTeamB = 0;

        try {
            intTeamA = Integer.parseInt(teamA.getText().toString());
            intTeamB = Integer.parseInt(teamB.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        if (scoreTeamA>scoreTeamB)
        {     intTeamA+=1;
              teamA.setText(Integer.toString(intTeamA));
              teamB.setText(Integer.toString(intTeamB));
            scoreMatchContainer.add(new ScoreTeamContainer("team A", "team B", scoreTeamA, scoreTeamB));
        }
        if (scoreTeamA<scoreTeamB)
        {
            intTeamB+=1;
            teamA.setText(Integer.toString(intTeamA));
            teamB.setText(Integer.toString(intTeamB));
            scoreMatchContainer.add(new ScoreTeamContainer("team A", "team B", scoreTeamA, scoreTeamB));
        }

        if (scoreTeamA==scoreTeamB)
        {

        }
        resetScores(view);


    }
    public void resetScores (View view)
    {
        scoreTeamA = 0;
        scoreTeamB =  0;
        setScoreTeamA(scoreTeamA);
        setScoreTeamB(scoreTeamB);
    }
    private void setScoreTeamA (int i)
    {
        TextView view= (TextView) findViewById(R.id.team_a_score_display_view);
        view.setText(Integer.toString(i));
    }

    private void setScoreTeamB (int i)
    {
        TextView view= (TextView) findViewById(R.id.team_b_score_display_view);
        view.setText(Integer.toString(i));
    }


}
