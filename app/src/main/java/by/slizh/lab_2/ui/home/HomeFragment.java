package by.slizh.lab_2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import by.slizh.lab_2.R;
import by.slizh.lab_2.databinding.FragmentHomeBinding;
import by.slizh.lab_2.ui.pager.StandingsAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StandingsAdapter standingsAdapter;
    private ViewPager2 viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Home create");
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        standingsAdapter = new StandingsAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(standingsAdapter);
        TabLayout tabLayout = view.findViewById(R.id.competitionsTabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getCompetitionCode(position))
        ).attach();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}