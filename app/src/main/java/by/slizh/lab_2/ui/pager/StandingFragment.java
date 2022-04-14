package by.slizh.lab_2.ui.pager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import by.slizh.lab_2.DetailsActivity;
import by.slizh.lab_2.R;
import by.slizh.lab_2.TeamAdapter;
import by.slizh.lab_2.db.TeamDbHelper;
import by.slizh.lab_2.entity.Team;
import by.slizh.lab_2.utils.JsonTeamsLoader;
import by.slizh.lab_2.utils.JsonTeamsParser;

// Instances of this class are fragments representing a single
// object in our collection.
public class StandingFragment extends Fragment {

    private static final String URL_STANDINGS_PREFIX = "http://api.football-data.org/v2/competitions/";
    private static final String URL_STANDINGS_POSTFIX = "/standings";

    public static final String ARG_OBJECT = "code";

    String competitionCode;

    ListView teamsListView;

    TeamDbHelper dbHelper;

    TeamAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("Standing create");
        return inflater.inflate(R.layout.fragment_standing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();

        competitionCode = args.getString(ARG_OBJECT);
        teamsListView = (ListView) view.findViewById(R.id.teamsListView);
        dbHelper = new TeamDbHelper(getContext());
        initializeCompetition(view, competitionCode);
        new LoadJsonAsync().execute();

    }

    public void initializeCompetition(View view, String competitionCode) {
        ImageView imageView = view.findViewById(R.id.competitionLogo);
        TextView textView = view.findViewById(R.id.competitionName);
        switch (competitionCode) {
            case "PL":
                imageView.setImageResource(R.drawable.pl);
                textView.setText("Premier League");
                break;
            case "SA":
                imageView.setImageResource(R.drawable.sa);
                textView.setText("Seria A");
                break;
            case "BL1":
                imageView.setImageResource(R.drawable.bl);
                textView.setText("Bundeliga");
                break;
            case "PD":
                imageView.setImageResource(R.drawable.pd);
                textView.setText("La Liga");
                break;
        }
    }


    private class LoadJsonAsync extends AsyncTask<Void, Void, List<Team>> {

        @Override
        protected List<Team> doInBackground(Void... voids) {
            System.out.println("In background async");
            List<Team> teams = new ArrayList<>();
            if (isNetworkConnected()) {
                String url = URL_STANDINGS_PREFIX + competitionCode + URL_STANDINGS_POSTFIX;
                try {
                    String jsonString = JsonTeamsLoader.load(url);
                    List<Team> loadedTeams = JsonTeamsParser.parse(jsonString);
                    teams.addAll(loadedTeams);
                    updateTeamsInDb(teams);
                } catch (JSONException | IOException e) {
                    showToast("Can't update data");
                    teams = loadTeamsByCompetitionCode(competitionCode);
                }
            } else {
                showToast("Can't establish network connection");
                teams = loadTeamsByCompetitionCode(competitionCode);
            }
            System.out.println("Async end");
            dbHelper.close();
            return teams;
        }

        @Override
        protected void onPostExecute(List<Team> result) {
            adapter = new TeamAdapter(getContext(), R.layout.team_row_item, result);
            teamsListView.setAdapter(adapter);

            teamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Team team = adapter.getItem(i);
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra("teamId", team.getTeamId());
                    intent.putExtra("teamName", team.getTeamName());
                    intent.putExtra("teamLogo", team.getLogoUrl());
                    intent.putExtra("position", team.getPosition());
                    intent.putExtra("points", team.getPoints());
                    intent.putExtra("playedGames", team.getPlayedGames());
                    intent.putExtra("won", team.getWon());
                    intent.putExtra("draw", team.getDraw());
                    intent.putExtra("lost", team.getLost());
                    intent.putExtra("goalsFor", team.getGoalsFor());
                    intent.putExtra("goalsAgainst", team.getGoalsAgainst());
                    intent.putExtra("goalsDifference", team.getGoalDifference());

                    startActivity(intent);
                }
            });
        }
    }

    private void updateTeamsInDb(List<Team> teams) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        for (Team team : teams) {
            ContentValues values = new ContentValues();
            values.put(TeamDbHelper.TEAM_ID_KEY, team.getTeamId());
            values.put(TeamDbHelper.COMPETITION_ID_KEY, team.getCompetitionId());
            values.put(TeamDbHelper.COMPETITION_NAME_KEY, team.getCompetitionName());
            values.put(TeamDbHelper.COMPETITION_CODE_KEY, team.getCompetitionCode());
            values.put(TeamDbHelper.TEAM_NAME_KEY, team.getTeamName());
            values.put(TeamDbHelper.LOGO_URL_KEY, team.getLogoUrl());
            values.put(TeamDbHelper.PLAYED_GAMES_KEY, team.getPlayedGames());
            values.put(TeamDbHelper.WON_KEY, team.getWon());
            values.put(TeamDbHelper.DRAW_KEY, team.getDraw());
            values.put(TeamDbHelper.LOST_KEY, team.getLost());
            values.put(TeamDbHelper.POINTS_KEY, team.getPoints());
            values.put(TeamDbHelper.GOALS_FOR_KEY, team.getGoalsFor());
            values.put(TeamDbHelper.GOALS_AGAINST_KEY, team.getGoalsAgainst());
            values.put(TeamDbHelper.GOAL_DIFFERENCE_KEY, team.getGoalDifference());
            values.put(TeamDbHelper.POSITION_KEY, team.getPosition());

            int updateCount = database.update(
                    TeamDbHelper.TEAMS_TABLE_NAME,
                    values,
                    TeamDbHelper.TEAM_ID_KEY + " = " + team.getTeamId(),
                    null
            );
            if (updateCount == 0) {
                database.insert(
                        TeamDbHelper.TEAMS_TABLE_NAME,
                        null,
                        values
                );
            }
        }
    }


    @SuppressLint("Range")
    private List<Team> loadTeamsByCompetitionCode(String competitionCode) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                TeamDbHelper.TEAMS_TABLE_NAME,
                null,
                TeamDbHelper.COMPETITION_CODE_KEY + " = '" + competitionCode + "'",
                null, null,
                null, null
        );

        List<Team> teams = new ArrayList<>();

        while (cursor.moveToNext()) {
            teams.add(new Team(
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.TEAM_ID_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.COMPETITION_ID_KEY)),
                    cursor.getString(cursor.getColumnIndex(TeamDbHelper.COMPETITION_NAME_KEY)),
                    cursor.getString(cursor.getColumnIndex(TeamDbHelper.COMPETITION_CODE_KEY)),
                    cursor.getString(cursor.getColumnIndex(TeamDbHelper.TEAM_NAME_KEY)),
                    cursor.getString(cursor.getColumnIndex(TeamDbHelper.LOGO_URL_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.PLAYED_GAMES_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.WON_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.DRAW_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.LOST_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.POINTS_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.GOALS_FOR_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.GOALS_AGAINST_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.GOAL_DIFFERENCE_KEY)),
                    cursor.getInt(cursor.getColumnIndex(TeamDbHelper.POSITION_KEY))
            ));
        }
        cursor.close();
        teams.sort(new Team.TeamPointsComparator().reversed());
        return teams;
    }

    private void showToast(String text) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}