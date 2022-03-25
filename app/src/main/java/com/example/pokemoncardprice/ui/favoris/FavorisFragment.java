package com.example.pokemoncardprice.ui.favoris;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemoncardprice.R;
import com.example.pokemoncardprice.databinding.FragmentFavorisBinding;
import com.example.pokemoncardprice.models.CardItem;
import com.example.pokemoncardprice.models.VerticalSpacingDecoration;
import com.example.pokemoncardprice.ui.card_search.CardSearchFragment;
import com.example.pokemoncardprice.ui.dashboard.DashboardViewModel;

public class FavorisFragment extends Fragment {

    private FavorisViewModel favorisViewModel;
    private DashboardViewModel dashboardViewModel;
    private FragmentFavorisBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 24;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        favorisViewModel =
                new ViewModelProvider(requireActivity()).get(FavorisViewModel.class);

        dashboardViewModel =
                new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        binding = FragmentFavorisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));

        favorisViewModel.getCardInfo().observe(getViewLifecycleOwner(), cardListItems -> {
            FavorisListItemAdapter adapter = new FavorisListItemAdapter(cardListItems);
            adapter.setClickListener((view, position) -> {
                CardItem selectedCardItem = cardListItems.get(position);

                dashboardViewModel.getCardInfo(selectedCardItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    if (cardItem != null) {
                        Navigation.findNavController(root).navigate(R.id.action_favorisFragment2_to_navigation_dashboard);
                    } else {
                        Toast.makeText(getContext(), "Une erreur est parvenue pendant la recherche de la carte", Toast.LENGTH_LONG).show();
                    }
                });
            });
            recyclerView.setAdapter(adapter);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}