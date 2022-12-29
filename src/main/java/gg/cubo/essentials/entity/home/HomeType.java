package gg.cubo.essentials.entity.home;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HomeType {

    PRIVATE("Particular"),
    PUBLIC("Pública");

    @Getter
    private final String translated;

}
