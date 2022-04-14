package by.slizh.lab_2.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import by.slizh.lab_2.R;
import by.slizh.lab_2.SignOutActivity;
import by.slizh.lab_2.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private static boolean isDarkTheme = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button signOutButton = root.findViewById(R.id.signOutButton);
        Button themeButton = root.findViewById(R.id.themeButton);

        if (isDarkTheme) {
            themeButton.setText(getResources().getString(R.string.title_light));
        } else {
            themeButton.setText(getResources().getString(R.string.title_dark));
        }

        signOutButton.setOnClickListener(view -> {
            Context context = getContext();
            startActivity(new Intent(context, SignOutActivity.class));
        });

        themeButton.setOnClickListener(view -> {
            if (isDarkTheme) {
                isDarkTheme = false;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                isDarkTheme = true;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());

        ImageView avatarImageView = root.findViewById(R.id.avatarImageView);
        TextView nameTextView = root.findViewById(R.id.nameTextView);
        TextView emailTextView = root.findViewById(R.id.emailTextView);

        nameTextView.setText(account.getDisplayName().substring(0, account.getDisplayName().indexOf('(')));
        emailTextView.setText(account.getEmail());

        //// TODO: 10.04.2022 убрать индикатор
        Picasso.with(getContext()).setIndicatorsEnabled(true);
        Picasso.with(getContext())
                .load(account.getPhotoUrl())
                .error(R.drawable.sad_cat)
                .into(avatarImageView);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}