package gg.cubo.essentials.placeholder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Class used to replace placeholders from an array.
 *
 * @param <T> The type of the entity used to replace placeholders.
 * @author Async
 */
public interface PlaceholderReplacer<T> {

    /**
     * Replaces placeholders from the used array.
     *
     * @param data object data
     * @return the replaced array.
     */
    List<String> replace(T data);

    default String[] getPlaceholders() {
        Replaceable manifest = getClass().getAnnotation(Replaceable.class);

        if (manifest == null) {
            throw new IllegalStateException("Replaceable annotation not found.");
        }

        return manifest.value();
    }

    default String replace(String text, T data) {
        return StringUtils.replaceEach(text, getPlaceholders(), replace(data).toArray(new String[0]));
    }
}
