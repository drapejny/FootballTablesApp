package by.slizh.lab_2.ui.explore;

import by.slizh.lab_2.R;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import by.slizh.lab_2.TeamAdapter;
import by.slizh.lab_2.databinding.FragmentExploreBinding;
import by.slizh.lab_2.db.TeamDbHelper;
import by.slizh.lab_2.entity.Team;

public class ExploreFragment extends Fragment {

    ListView listView;
    TextInputEditText textInput;
    TextView labelText;

    TeamDbHelper dbHelper;

    TeamAdapter adapter;


    private FragmentExploreBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dbHelper = new TeamDbHelper(getContext());
        listView = view.findViewById(R.id.exploreListView);
        textInput = view.findViewById(R.id.searchInputEditText);
        labelText = view.findViewById(R.id.exploreTextView);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        List<Team> favoriteTeams = findFavoriteTeams(account.getId());

        adapter = new TeamAdapter(getContext(), R.layout.team_row_item, favoriteTeams);
        listView.setAdapter(adapter);

        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchString = charSequence.toString();

                if (searchString.equals("")) {
                    labelText.setText(getResources().getString(R.string.title_favorites));
                    List<Team> favoriteTeams = findFavoriteTeams(account.getId());
                    adapter = new TeamAdapter(getContext(), R.layout.team_row_item, favoriteTeams);
                } else {
                    labelText.setText(getResources().getString(R.string.title_searched));
                    List<Team> searchedTeams = findTeamsByNamePart(searchString);
                    adapter = new TeamAdapter(getContext(), R.layout.team_row_item, searchedTeams);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("Range")
    public List<Team> findTeamsByNamePart(String namePart) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                TeamDbHelper.TEAMS_TABLE_NAME,
                null,
                TeamDbHelper.TEAM_NAME_KEY + " LIKE '%" + namePart + "%'",
                null, null,
                null,
                TeamDbHelper.TEAM_NAME_KEY + " ASC"
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
        return teams;
    }

    @SuppressLint("Range")
    public List<Team> findFavoriteTeams(String userId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                TeamDbHelper.FAVORITES_TABLE_NAME,
                null,
                TeamDbHelper.USER_ID_KEY + " = '" + userId + "'",
                null, null,
                null, null
        );

        List<Integer> favoriteIds = new ArrayList<>();

        while (cursor.moveToNext()) {
            favoriteIds.add(cursor.getInt(cursor.getColumnIndex(TeamDbHelper.FAVORITE_TEAM_ID_KEY)));
        }

        cursor.close();

        List<Team> favoriteTeams = new ArrayList<>();

        for (Integer teamId : favoriteIds) {
            cursor = database.query(
                    TeamDbHelper.TEAMS_TABLE_NAME,
                    null,
                    TeamDbHelper.TEAM_ID_KEY + " = " + teamId,
                    null, null,
                    null, null
            );

            while (cursor.moveToNext()) {
                favoriteTeams.add(new Team(
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
        }
        cursor.close();
        return favoriteTeams;
    }
}