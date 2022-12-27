package gg.cubo.essentials.command.locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.locales.Locales;

import java.util.Arrays;
import java.util.Locale;

@RequiredArgsConstructor
@Getter
public enum MessagesLocale {

    ENGLISH("en_US", Locales.ENGLISH),
    PORTUGUESE("pt_BR", Locales.PORTUGUESE);

    @NotNull
    private final String fileName;

    @NotNull
    private final Locale locale;

    public static @Nullable MessagesLocale findByName(@NotNull String name) {
        return Arrays.stream(values())
                .filter(value -> value.getFileName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
