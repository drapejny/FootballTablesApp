package by.slizh.lab_2.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import by.slizh.lab_2.entity.Team;

public final class JsonTeamsParser {

    public static List<Team> parse(String jsonString) throws JSONException {
        JSONObject object = new JSONObject(jsonString);
        JSONObject competition = object.getJSONObject("competition");
        JSONArray table = object.getJSONArray("standings").getJSONObject(0).getJSONArray("table");

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < table.length(); i++) {
            JSONObject tableObject = table.getJSONObject(i);
            Team team = new Team(
                    tableObject.getJSONObject("team").getInt("id"),
                    competition.getInt("id"),
                    competition.getString("name"),
                    competition.getString("code"),
                    tableObject.getJSONObject("team").getString("name"),
                    tableObject.getJSONObject("team").getString("crestUrl"),
                    tableObject.getInt("playedGames"),
                    tableObject.getInt("won"),
                    tableObject.getInt("draw"),
                    tableObject.getInt("lost"),
                    tableObject.getInt("points"),
                    tableObject.getInt("goalsFor"),
                    tableObject.getInt("goalsAgainst"),
                    tableObject.getInt("goalDifference"),
                    tableObject.getInt("position"));
            teams.add(team);
        }
        return teams;
    }
}
