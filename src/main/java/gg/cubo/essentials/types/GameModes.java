package gg.cubo.essentials.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum GameModes {

    SURVIVAL(0, GameMode.SURVIVAL),
    CREATIVE(1, GameMode.CREATIVE),
    ADVENTURE(2, GameMode.ADVENTURE),
    SPECTATOR(3, GameMode.SPECTATOR);

    private final int id;
    private final GameMode mode;

    private static final GameModes[] MODES = values();

    public static @Nullable GameModes findById(int id) {
        return Arrays.stream(MODES)
                .filter(mode -> mode.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
