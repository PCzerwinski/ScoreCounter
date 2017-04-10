package com.example.android.scorecounter;

import java.io.Serializable;

/**
 * Created by SethQ on 2017-04-09.
 */

public class ScoreTeamContainer implements Serializable {

    private String teamNameA;
    private String teamNameB;

    private int teamScoreA;
    private int teamScoreB;


    ScoreTeamContainer (String teamNameA, String teamNameB, int teamScoreA, int teamScoreB)
    {
        this.teamNameA= teamNameA;
        this.teamNameB = teamNameB;

        this.teamScoreA = teamScoreA;
        this.teamScoreB = teamScoreB;



    }

    public String getTeamNameA(){return teamNameA;}
    public String getTeamNameB(){return teamNameB;}

    public int getTeamScoreA() {return  teamScoreA;}
    public int getTeamScoreB() {return  teamScoreB;}


}
