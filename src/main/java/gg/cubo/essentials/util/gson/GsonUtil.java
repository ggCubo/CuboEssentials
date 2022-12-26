package gg.cubo.essentials.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

public class GsonUtil {

    @Getter
    private static final Gson gson = new GsonBuilder()
            .create();

}
