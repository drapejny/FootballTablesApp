package by.slizh.lab_2.ui.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StandingsAdapter extends FragmentStateAdapter {

    public StandingsAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new StandingFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putString(StandingFragment.ARG_OBJECT, getCompetitionCode(position));
        fragment.setArguments(args);
        return fragment;
    }

    private String getCompetitionCode(int position) {
        switch (position) {
            case 0:
                return "PL";
            case 1:
                return "SA";
            case 2:
                return "BL1";
            case 3:
                return "PD";
            default:
                return "PL";
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}