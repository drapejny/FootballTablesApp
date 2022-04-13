package by.slizh.lab_2.ui.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;

import java.util.List;

import by.slizh.lab_2.R;
import by.slizh.lab_2.TeamAdapter;
import by.slizh.lab_2.entity.Team;
import by.slizh.lab_2.utils.JsonTeamsParser;

// Instances of this class are fragments representing a single
// object in our collection.
public class StandingFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_standing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String jsonString = "{\"filters\":{},\"competition\":{\"id\":2002,\"area\":{\"id\":2088,\"name\":\"Germany\"},\"name\":\"Bundesliga\",\"code\":\"BL1\",\"plan\":\"TIER_ONE\",\"lastUpdated\":\"2022-03-20T08:52:53Z\"},\"season\":{\"id\":742,\"startDate\":\"2021-08-13\",\"endDate\":\"2022-05-14\",\"currentMatchday\":29,\"winner\":null},\"standings\":[{\"stage\":\"REGULAR_SEASON\",\"type\":\"TOTAL\",\"group\":null,\"table\":[{\"position\":1,\"team\":{\"id\":5,\"name\":\"FC Bayern München\",\"crestUrl\":\"https://crests.football-data.org/5.svg\"},\"playedGames\":29,\"form\":null,\"won\":21,\"draw\":4,\"lost\":4,\"points\":67,\"goalsFor\":85,\"goalsAgainst\":29,\"goalDifference\":56},{\"position\":2,\"team\":{\"id\":4,\"name\":\"Borussia Dortmund\",\"crestUrl\":\"https://crests.football-data.org/4.png\"},\"playedGames\":29,\"form\":null,\"won\":19,\"draw\":3,\"lost\":7,\"points\":60,\"goalsFor\":70,\"goalsAgainst\":42,\"goalDifference\":28},{\"position\":3,\"team\":{\"id\":3,\"name\":\"Bayer 04 Leverkusen\",\"crestUrl\":\"https://crests.football-data.org/3.png\"},\"playedGames\":28,\"form\":null,\"won\":15,\"draw\":6,\"lost\":7,\"points\":51,\"goalsFor\":68,\"goalsAgainst\":42,\"goalDifference\":26},{\"position\":4,\"team\":{\"id\":721,\"name\":\"RB Leipzig\",\"crestUrl\":\"https://crests.football-data.org/721.png\"},\"playedGames\":28,\"form\":null,\"won\":14,\"draw\":6,\"lost\":8,\"points\":48,\"goalsFor\":61,\"goalsAgainst\":31,\"goalDifference\":30},{\"position\":5,\"team\":{\"id\":17,\"name\":\"SC Freiburg\",\"crestUrl\":\"https://crests.football-data.org/17.svg\"},\"playedGames\":28,\"form\":null,\"won\":12,\"draw\":9,\"lost\":7,\"points\":45,\"goalsFor\":44,\"goalsAgainst\":33,\"goalDifference\":11},{\"position\":6,\"team\":{\"id\":2,\"name\":\"TSG 1899 Hoffenheim\",\"crestUrl\":\"https://crests.football-data.org/2.png\"},\"playedGames\":28,\"form\":null,\"won\":13,\"draw\":5,\"lost\":10,\"points\":44,\"goalsFor\":50,\"goalsAgainst\":42,\"goalDifference\":8},{\"position\":7,\"team\":{\"id\":15,\"name\":\"1. FSV Mainz 05\",\"crestUrl\":\"https://crests.football-data.org/15.svg\"},\"playedGames\":29,\"form\":null,\"won\":12,\"draw\":5,\"lost\":12,\"points\":41,\"goalsFor\":42,\"goalsAgainst\":33,\"goalDifference\":9},{\"position\":8,\"team\":{\"id\":28,\"name\":\"1. FC Union Berlin\",\"crestUrl\":\"https://crests.football-data.org/28.svg\"},\"playedGames\":28,\"form\":null,\"won\":11,\"draw\":8,\"lost\":9,\"points\":41,\"goalsFor\":34,\"goalsAgainst\":38,\"goalDifference\":-4},{\"position\":9,\"team\":{\"id\":1,\"name\":\"1. FC Köln\",\"crestUrl\":\"https://crests.football-data.org/1.png\"},\"playedGames\":29,\"form\":null,\"won\":10,\"draw\":10,\"lost\":9,\"points\":40,\"goalsFor\":38,\"goalsAgainst\":42,\"goalDifference\":-4},{\"position\":10,\"team\":{\"id\":19,\"name\":\"Eintracht Frankfurt\",\"crestUrl\":\"https://crests.football-data.org/19.svg\"},\"playedGames\":28,\"form\":null,\"won\":10,\"draw\":9,\"lost\":9,\"points\":39,\"goalsFor\":39,\"goalsAgainst\":38,\"goalDifference\":1},{\"position\":11,\"team\":{\"id\":18,\"name\":\"Borussia Mönchengladbach\",\"crestUrl\":\"https://crests.football-data.org/18.svg\"},\"playedGames\":29,\"form\":null,\"won\":10,\"draw\":7,\"lost\":12,\"points\":37,\"goalsFor\":41,\"goalsAgainst\":52,\"goalDifference\":-11},{\"position\":12,\"team\":{\"id\":36,\"name\":\"VfL Bochum 1848\",\"crestUrl\":\"https://crests.football-data.org/36.png\"},\"playedGames\":28,\"form\":null,\"won\":10,\"draw\":5,\"lost\":13,\"points\":35,\"goalsFor\":30,\"goalsAgainst\":40,\"goalDifference\":-10},{\"position\":13,\"team\":{\"id\":11,\"name\":\"VfL Wolfsburg\",\"crestUrl\":\"https://crests.football-data.org/11.svg\"},\"playedGames\":29,\"form\":null,\"won\":10,\"draw\":4,\"lost\":15,\"points\":34,\"goalsFor\":31,\"goalsAgainst\":45,\"goalDifference\":-14},{\"position\":14,\"team\":{\"id\":16,\"name\":\"FC Augsburg\",\"crestUrl\":\"https://crests.football-data.org/16.svg\"},\"playedGames\":29,\"form\":null,\"won\":8,\"draw\":9,\"lost\":12,\"points\":33,\"goalsFor\":34,\"goalsAgainst\":45,\"goalDifference\":-11},{\"position\":15,\"team\":{\"id\":10,\"name\":\"VfB Stuttgart\",\"crestUrl\":\"https://crests.football-data.org/10.svg\"},\"playedGames\":29,\"form\":null,\"won\":6,\"draw\":9,\"lost\":14,\"points\":27,\"goalsFor\":36,\"goalsAgainst\":53,\"goalDifference\":-17},{\"position\":16,\"team\":{\"id\":38,\"name\":\"Arminia Bielefeld\",\"crestUrl\":\"https://crests.football-data.org/38.svg\"},\"playedGames\":29,\"form\":null,\"won\":5,\"draw\":11,\"lost\":13,\"points\":26,\"goalsFor\":23,\"goalsAgainst\":41,\"goalDifference\":-18},{\"position\":17,\"team\":{\"id\":9,\"name\":\"Hertha BSC\",\"crestUrl\":\"https://crests.football-data.org/9.svg\"},\"playedGames\":28,\"form\":null,\"won\":7,\"draw\":5,\"lost\":16,\"points\":26,\"goalsFor\":30,\"goalsAgainst\":62,\"goalDifference\":-32},{\"position\":18,\"team\":{\"id\":21,\"name\":\"SpVgg Greuther Fürth 1903\",\"crestUrl\":\"https://crests.football-data.org/21.svg\"},\"playedGames\":29,\"form\":null,\"won\":3,\"draw\":7,\"lost\":19,\"points\":16,\"goalsFor\":24,\"goalsAgainst\":72,\"goalDifference\":-48}]}]}\n";
        JsonTeamsParser parser = new JsonTeamsParser();
        List<Team> teams = null;
        try {
            teams = parser.parse(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(teams);

        ListView teamsListView = (ListView) view.findViewById(R.id.teamsListView);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,new String[] {"123"});

        TeamAdapter adapter = new TeamAdapter(getContext(), R.layout.team_row_item, teams);

        teamsListView.setAdapter(adapter);
    }
}