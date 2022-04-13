package by.slizh.lab_2.entity;

import java.util.Objects;

public class Team {

    private int teamId;
    private int competitionId;
    private String competitionName;
    private String competitionCode;
    private String teamName;
    private String logoUrl;
    private int playedGames;
    private int won;
    private int draw;
    private int lost;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int position;

    public Team(int teamId, int competitionId, String competitionName, String competitionCode, String teamName, String logoUrl, int playedGames, int won, int draw, int lost, int points, int goalsFor, int goalsAgainst, int goalDifference, int position) {
        this.teamId = teamId;
        this.competitionId = competitionId;
        this.competitionName = competitionName;
        this.competitionCode = competitionCode;
        this.teamName = teamName;
        this.logoUrl = logoUrl;
        this.playedGames = playedGames;
        this.won = won;
        this.draw = draw;
        this.lost = lost;
        this.points = points;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
        this.position = position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(String competitionCode) {
        this.competitionCode = competitionCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamId == team.teamId && competitionId == team.competitionId && playedGames == team.playedGames && won == team.won && draw == team.draw && lost == team.lost && points == team.points && goalsFor == team.goalsFor && goalsAgainst == team.goalsAgainst && goalDifference == team.goalDifference && position == team.position && competitionName.equals(team.competitionName) && competitionCode.equals(team.competitionCode) && teamName.equals(team.teamName) && logoUrl.equals(team.logoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, competitionId, competitionName, competitionCode, teamName, logoUrl, playedGames, won, draw, lost, points, goalsFor, goalsAgainst, goalDifference, position);
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", competitionId=" + competitionId +
                ", competitionName='" + competitionName + '\'' +
                ", competitionCode='" + competitionCode + '\'' +
                ", teamName='" + teamName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", playedGames=" + playedGames +
                ", won=" + won +
                ", draw=" + draw +
                ", lost=" + lost +
                ", points=" + points +
                ", goalsFor=" + goalsFor +
                ", goalsAgainst=" + goalsAgainst +
                ", goalDifference=" + goalDifference +
                ", position=" + position +
                '}';
    }
}
