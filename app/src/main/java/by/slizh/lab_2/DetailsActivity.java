package by.slizh.lab_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import by.slizh.lab_2.db.TeamDbHelper;
import by.slizh.lab_2.entity.Team;

public class DetailsActivity extends AppCompatActivity {

    private TeamDbHelper dbHelper;

    private boolean isFavoriteFlag;

    Button favoriteButton;

    int teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        System.out.println("Create Detail");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dbHelper = new TeamDbHelper(this);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        teamId = getIntent().getIntExtra("teamId", 0);

        System.out.println(teamId);

        isFavoriteFlag = isFavoriteTeam(account.getId(), teamId);

        System.out.println(isFavoriteFlag);

        favoriteButton = (Button) findViewById(R.id.favoriteButton);

        Picasso
                .with(this)
                .load(getIntent().getStringExtra("teamLogo"))
                .error(R.drawable.sad_cat)
                .into((ImageView) findViewById(R.id.teamLogoDetail));
        ((TextView) findViewById(R.id.teamNameDetail)).setText(getIntent().getStringExtra("teamName"));
        ((TextView) findViewById(R.id.positionDetail)).setText(Integer.toString(getIntent().getIntExtra("position", 0)));
        ((TextView) findViewById(R.id.pointsDetail)).setText(Integer.toString(getIntent().getIntExtra("points", 0)));
        ((TextView) findViewById(R.id.playedGamesDetail)).setText(Integer.toString(getIntent().getIntExtra("playedGames", 0)));
        ((TextView) findViewById(R.id.wonDetail)).setText(Integer.toString(getIntent().getIntExtra("won", 0)));
        ((TextView) findViewById(R.id.drawDetail)).setText(Integer.toString(getIntent().getIntExtra("draw", 0)));
        ((TextView) findViewById(R.id.lostDetail)).setText(Integer.toString(getIntent().getIntExtra("lost", 0)));
        ((TextView) findViewById(R.id.goalsForDetail)).setText(Integer.toString(getIntent().getIntExtra("goalsFor", 0)));
        ((TextView) findViewById(R.id.goalsAgainstDetail)).setText(Integer.toString(getIntent().getIntExtra("goalsAgainst", 0)));
        ((TextView) findViewById(R.id.goalsDifferenceDetail)).setText(Integer.toString(getIntent().getIntExtra("goalsDifference", 0)));

        if (isFavoriteFlag) {
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.red));
            favoriteButton.setText(R.string.title_remove_from_favorite);
        } else {
            favoriteButton.setBackgroundColor(getResources().getColor(R.color.orange));
            favoriteButton.setText(R.string.title_add_to_favorite);
        }

        favoriteButton.setOnClickListener(view -> {
            if (isFavoriteFlag) {
                removeFavoriteTeam(account.getId(), teamId);
                favoriteButton.setBackgroundColor(getResources().getColor(R.color.orange));
                favoriteButton.setText(R.string.title_add_to_favorite);
                isFavoriteFlag = false;
            } else {
                addToFavorite(account.getId(), teamId);
                favoriteButton.setBackgroundColor(getResources().getColor(R.color.red));
                favoriteButton.setText(R.string.title_remove_from_favorite);
                isFavoriteFlag = true;
            }
        });

        dbHelper.close();
    }

    public boolean isFavoriteTeam(String userId, int teamId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                TeamDbHelper.FAVORITES_TABLE_NAME,
                null,
                TeamDbHelper.USER_ID_KEY + " = '" + userId + "' AND " + TeamDbHelper.FAVORITE_TEAM_ID_KEY + " = " + teamId,
                null, null,
                null, null
        );
        if (cursor.getCount() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void removeFavoriteTeam(String userId, int teamId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        database.delete(
                TeamDbHelper.FAVORITES_TABLE_NAME,
                TeamDbHelper.USER_ID_KEY + " = '" + userId + "' AND " + TeamDbHelper.FAVORITE_TEAM_ID_KEY + " = " + teamId,
                null
        );
    }



    public void addToFavorite(String userId, int teamId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TeamDbHelper.USER_ID_KEY, userId);
        values.put(TeamDbHelper.FAVORITE_TEAM_ID_KEY, teamId);

        database.insert(
                TeamDbHelper.FAVORITES_TABLE_NAME,
                null,
                values
        );
    }
}