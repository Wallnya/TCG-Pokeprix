package com.pcp.pokemoncardprice.ui.parameters;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pcp.pokemoncardprice.databinding.FragmentParametersBinding;

public class ParametersFragment extends Fragment {

    private FragmentParametersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParametersViewModel parametersViewModel =
                new ViewModelProvider(this).get(ParametersViewModel.class);

        binding = FragmentParametersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button4.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://discord.gg/9MvhV3maqU");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button3.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://fr.linkedin.com/in/ludivine-ducamp-9bbb00179");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button5.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://github.com/Wallnya");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button6.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/martin-hauw/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button7.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://hmartin.portfoliobox.net/home");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button8.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.linkedin.com/in/andrewbackes/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        binding.button9.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://pokemontcg.io/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);

        });
        return root;
    }
}
