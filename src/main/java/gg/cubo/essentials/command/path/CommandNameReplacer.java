package gg.cubo.essentials.command.path;

import gg.cubo.essentials.command.annotation.NamedCommand;
import gg.cubo.essentials.command.replacer.FileCommandReplacer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

@RequiredArgsConstructor
public class CommandNameReplacer implements AnnotationReplacer<NamedCommand> {

    private final FileCommandReplacer replacer;

    @Override
    public @Nullable Collection<Annotation> replaceAnnotations(@NotNull AnnotatedElement annotatedElement, @NotNull NamedCommand namedCommand) {
        return replacer.replace("commands.yml", namedCommand.value(), Command.class);
    }
}
