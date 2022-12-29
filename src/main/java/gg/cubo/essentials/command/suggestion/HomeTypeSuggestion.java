package gg.cubo.essentials.command.suggestion;

import gg.cubo.essentials.entity.home.HomeType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProviderFactory;
import revxrsal.commands.command.CommandParameter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HomeTypeSuggestion implements SuggestionProviderFactory {

    private final HomeType[] HOME_TYPE = HomeType.values();

    @Override
    public @Nullable SuggestionProvider createSuggestionProvider(@NotNull CommandParameter commandParameter) {
        if (!HomeType.class.equals(commandParameter.getType())) return null;

        return (list, commandActor, executableCommand) -> Arrays.stream(HOME_TYPE)
                .map(type -> type.name().toLowerCase())
                .collect(Collectors.toList());
    }
}
