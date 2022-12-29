package gg.cubo.essentials.loader.impl;

import com.spigonate.Spigonate;
import gg.cubo.essentials.command.ChestCommand;
import gg.cubo.essentials.command.GeneralCommands;
import gg.cubo.essentials.command.annotation.NamedCommand;
import gg.cubo.essentials.command.locale.JsonLocaleReader;
import gg.cubo.essentials.command.locale.MessagesLocale;
import gg.cubo.essentials.command.path.CommandNameReplacer;
import gg.cubo.essentials.command.replacer.FileCommandReplacer;
import gg.cubo.essentials.command.suggestion.HomeTypeSuggestion;
import gg.cubo.essentials.loader.InMemoryLoader;
import gg.cubo.essentials.util.Log;
import gg.cubo.essentials.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.bukkit.core.BukkitActor;
import revxrsal.commands.locales.Locales;

import java.io.File;

@RequiredArgsConstructor
public class CommandInMemoryLoader implements InMemoryLoader {

    private final Spigonate spigonate;

    @Override
    public void load(@NotNull Plugin plugin) {
        FileUtil.createConfig(plugin, "commands.yml");
        File file = FileUtil.getFile(plugin, "commands.yml");

        BukkitCommandHandler handler = BukkitCommandHandler.create(plugin);
        FileCommandReplacer replacer = new FileCommandReplacer(file, plugin);

        MessagesLocale locale = MessagesLocale.findByName(plugin.getConfig().getString("language"));

        if (locale == null) {
            Log.error("Não foi possível encontrar a linguagem para as mensagens");
            return;
        }

        FileUtil.createConfig(plugin, locale.getFileName() + ".json", "languages");
        File localeFile = FileUtil.getFile(plugin, locale.getFileName() + ".json", "languages");

        handler.getTranslator().add(new JsonLocaleReader(localeFile, Locales.PORTUGUESE));
        handler.setLocale(locale.getLocale());

        handler.registerAnnotationReplacer(NamedCommand.class, new CommandNameReplacer(replacer));
        handler.disableStackTraceSanitizing();

        handler.getAutoCompleter().registerSuggestionFactory(new HomeTypeSuggestion());

        handler.register(new ChestCommand(spigonate));
        handler.register(new GeneralCommands(spigonate));

        replacer.invalidate();

        Log.info("Todos os comandos foram registrados");
    }
}
