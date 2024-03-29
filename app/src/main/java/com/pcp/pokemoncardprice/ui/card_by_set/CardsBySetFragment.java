package com.pcp.pokemoncardprice.ui.card_by_set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcp.pokemoncardprice.R;
import com.pcp.pokemoncardprice.databinding.FragmentCardsBinding;
import com.pcp.pokemoncardprice.databinding.FragmentCardsbysetBinding;
import com.pcp.pokemoncardprice.models.CardItem;
import com.pcp.pokemoncardprice.models.VerticalSpacingDecoration;
import com.pcp.pokemoncardprice.ui.card.CardsViewModel;
import com.pcp.pokemoncardprice.ui.card_info.CardsInfoViewModel;

import java.util.Collections;

public class CardsBySetFragment extends Fragment {

    private CardsViewModel cardsViewModel;
    private CardsBySetViewModel cardsListViewModel;
    private CardsInfoViewModel cardsInfoViewModel;
    private FragmentCardsbysetBinding binding;
    private static final int VERTICAL_ITEM_SPACE = 24;

    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsViewModel =
                new ViewModelProvider(this).get(CardsViewModel.class);
        cardsListViewModel =
                new ViewModelProvider(this).get(CardsBySetViewModel.class);
        cardsInfoViewModel =
                new ViewModelProvider(requireActivity()).get(CardsInfoViewModel.class);

        binding = FragmentCardsbysetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.rankingListView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.addItemDecoration(new VerticalSpacingDecoration(VERTICAL_ITEM_SPACE));
        cardsListViewModel.getCardbySet(CardsViewModel.setId).observe(getViewLifecycleOwner(), cardListItems -> {
            Collections.sort(cardListItems, CardItem.byDate);
            CardsBySetListItemAdapter adapter = new CardsBySetListItemAdapter(cardListItems);;

            //On cache le texte et l'animation car tout a été chargé
            binding.uppertext.setVisibility(View.GONE);
            binding.loadinganimation.setVisibility(View.GONE);
            adapter.setClickListener((view, position) -> {
                CardItem selectedCardItem = cardListItems.get(position);

                cardsInfoViewModel.getCardInfo(selectedCardItem.getId()).observe(getViewLifecycleOwner(), cardItem -> {
                    if (cardItem != null) {
                        Navigation.findNavController(root).navigate(R.id.action_cardsBySetFragment_to_navigation_notifications);
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
