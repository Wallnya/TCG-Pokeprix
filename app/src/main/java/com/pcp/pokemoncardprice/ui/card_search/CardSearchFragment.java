package com.pcp.pokemoncardprice.ui.card_search;

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

import com.pcp.pokemoncardprice.R;

import com.pcp.pokemoncardprice.databinding.FragmentCardsearchBinding;
import com.pcp.pokemoncardprice.ui.card.CardsViewModel;
import com.pcp.pokemoncardprice.ui.extension.ExtensionViewModel;


public class CardSearchFragment extends Fragment {
    private CardsViewModel cardsViewModel;
    private CardSearchViewModel cardSearchViewModel;
    private ExtensionViewModel extensionViewModel;

    private FragmentCardsearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cardsViewModel =
                new ViewModelProvider(requireActivity()).get(CardsViewModel.class);

        cardSearchViewModel =
                new ViewModelProvider(requireActivity()).get(CardSearchViewModel.class);

        extensionViewModel =
                new ViewModelProvider(requireActivity()).get(ExtensionViewModel.class);

        binding = FragmentCardsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        binding.researchButton.setOnClickListener(v -> {
            String playerTag = binding.playerTag.getText().toString();
            char[] char_table = playerTag.toCharArray();

            if(char_table.length > 1){
                char_table[0]=Character.toUpperCase(char_table[0]);
                playerTag = new String(char_table);
                cardSearchViewModel.getCardName(playerTag, result -> {
                    cardsViewModel.getCard(result).observe(getViewLifecycleOwner(), cardItem -> {
                        if (!cardItem.isEmpty()) {
                            Navigation.findNavController(root).navigate(R.id.action_cardSearchFragment_to_cardsFragment);
                        } else {
                            Toast.makeText(getContext(), "Pas de carte", Toast.LENGTH_LONG).show();
                        }
                    });
                });
            }
        });

        binding.extensionButton.setOnClickListener(v -> {
            extensionViewModel.getExtension().observe(getViewLifecycleOwner(), cardItem -> {
                    if (!cardItem.isEmpty()) {
                        Navigation.findNavController(root).navigate(R.id.action_cardSearchFragment_to_extensionFragment);
                    } else {
                        Toast.makeText(getContext(), "Pas d'extension trouvée'", Toast.LENGTH_LONG).show();
                    }
                });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}