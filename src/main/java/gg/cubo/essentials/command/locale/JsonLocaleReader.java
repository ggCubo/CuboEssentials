package gg.cubo.essentials.command.locale;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import revxrsal.commands.locales.LocaleReader;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.Locale;

public class JsonLocaleReader implements LocaleReader {

    private final JsonObject object;
    private final Locale locale;

    @SneakyThrows
    public JsonLocaleReader(File file, Locale locale) {
        this.locale = locale;
        try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath())) {
            object = (JsonObject) new JsonParser().parse(bufferedReader);
        }
    }

    @Override public boolean containsKey(String key) {
        return object.has(key);
    }

    @Override public String get(String key) {
        return ChatColor.translateAlternateColorCodes('&', object.getAsJsonPrimitive(key).getAsString());
    }

    @Override public Locale getLocale() {
        return locale;
    }
}
