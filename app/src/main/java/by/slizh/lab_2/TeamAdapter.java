package by.slizh.lab_2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.List;

import by.slizh.lab_2.entity.Team;

public class TeamAdapter extends ArrayAdapter<Team> {

    private LayoutInflater inflater;
    private int layout;
    private List<Team> teams;

    public TeamAdapter(@NonNull Context context, int resource, List<Team> teams) {
        super(context, resource, teams);
        this.teams = teams;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(this.layout, parent, false);

        TextView positionTextView = (TextView) rowView.findViewById(R.id.positionTextView);
        ImageView teamLogoImageView = (ImageView) rowView.findViewById(R.id.teamLogoImageView);
        TextView teamNameTextView = (TextView) rowView.findViewById(R.id.teamNameTextView);
        TextView pointsTextView = (TextView) rowView.findViewById(R.id.pointsTextView);

        Team team = teams.get(position);

        positionTextView.setText(Integer.toString(team.getPosition()));

        Picasso
                .with(getContext())
                .load(team.getLogoUrl())
                .error(R.drawable.sad_cat)
                .into(teamLogoImageView);
//        GlideToVectorYou
//                .init()
//                .with(getContext())
//                .setPlaceHolder(R.drawable.sad_cat, R.drawable.sad_cat)
//                .load(Uri.parse(team.getLogoUrl()), teamLogoImageView);
        teamNameTextView.setText(team.getTeamName());
        pointsTextView.setText(Integer.toString(team.getPoints()));

        return rowView;
    }
}
