package gg.cubo.essentials.util.bukkit;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import gg.cubo.essentials.Essentials;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("UnstableApiUsage")
public class ReflectionUtil {

    private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public static @NotNull Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + version + "." + name);
    }

    public static @NotNull Class<?> getOBClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static <T> List<Class<T>> getClasses(@NotNull Class<T> value, String packageName) {
        try {
            return ClassPath.from(Essentials.getInstance().getClass().getClassLoader()).getTopLevelClasses().stream()
                    .filter(classInfo -> classInfo.getName().toLowerCase().startsWith(packageName))
                    .map(ReflectionUtil::load)
                    .filter(Objects::nonNull)
                    .filter(value::isAssignableFrom)
                    .map(clazz -> (Class<T>) clazz)
                    .collect(Collectors.toList());

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }

    private static @Nullable Class<?> load(ClassPath.ClassInfo info) {
        try {
            return info.load();
        } catch (Throwable t) {
            return null;
        }
    }

}

