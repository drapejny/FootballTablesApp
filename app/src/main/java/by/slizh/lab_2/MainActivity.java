package by.slizh.lab_2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import by.slizh.lab_2.databinding.ActivityMainBinding;
import by.slizh.lab_2.db.TeamDbHelper;
import by.slizh.lab_2.entity.Team;
import by.slizh.lab_2.utils.JsonTeamsLoader;
import by.slizh.lab_2.utils.JsonTeamsParser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private Context context;

    public static boolean isFirstTimeFlag = true;

    public static List<Team> teams = new ArrayList<>();

    public static final String[] COMPETITIONS_CODES = {"PL", "SA", "BL1", "PD"};

    private static final String URL_STANDINGS_PREFIX = "http://api.football-data.org/v2/competitions/";
    private static final String URL_STANDINGS_POSTFIX = "/standings";

    private TeamDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Main create");
        context = this;
        dbHelper = new TeamDbHelper(this);

//        LoadJsonAsync loadJsonAsync = null;
//        if (isFirstTimeFlag) {
//            loadJsonAsync = new LoadJsonAsync();
//            loadJsonAsync.execute();
//        }

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

//        if (isFirstTimeFlag) {
//            try {
//                loadJsonAsync.get(1000, TimeUnit.MILLISECONDS);
//            } catch (ExecutionException | InterruptedException | TimeoutException e) {
//                Toast.makeText(context, "Can't update data", Toast.LENGTH_LONG).show();
//            }
//            isFirstTimeFlag = false;
//        }


//        try {
//            teams = loadJsonAsync.get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private class LoadJsonAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("In background async");
            if (isNetworkConnected()) {
                List<Team> teams = new ArrayList<>();
                for (String code : COMPETITIONS_CODES) {
                    String url = URL_STANDINGS_PREFIX + code + URL_STANDINGS_POSTFIX;
                    try {
                        String jsonString = JsonTeamsLoader.load(url);
                        List<Team> loadedTeams = JsonTeamsParser.parse(jsonString);
                        teams.addAll(loadedTeams);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        teams = loadTeamsFromDb();
                    }
                }
                updateTeamsInDb(teams);
            } else {
                Toast.makeText(context, "Can't establish network connection", Toast.LENGTH_LONG).show();
            }
            System.out.println("Async end");
            return null;
        }
    }

    private List<Team> loadTeams() {
        System.out.println(1);
        List<Team> teams = new ArrayList<>();
        if (isNetworkConnected()) {
            System.out.println(2);
            for (String code : COMPETITIONS_CODES) {
                String url = URL_STANDINGS_PREFIX + code + URL_STANDINGS_POSTFIX;
                try {
                    String jsonString = JsonTeamsLoader.load(url);
                    List<Team> loadedTeams = JsonTeamsParser.parse(jsonString);
                    teams.addAll(loadedTeams);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Can't update data", Toast.LENGTH_LONG).show();
                    teams = loadTeamsFromDb();
                }
            }
        } else {
            System.out.println(4);
            teams = loadTeamsFromDb();
        }
        return teams;
    }

    @SuppressLint("Range")
    private List<Team> loadTeamsFromDb() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                TeamDbHelper.TEAMS_TABLE_NAME,
                null, null,
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
        return teams;
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}