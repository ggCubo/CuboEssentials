package gg.cubo.essentials.entity;

import com.google.common.collect.Lists;
import com.spigonate.entity.Column;
import com.spigonate.entity.Entity;
import com.spigonate.entity.Id;
import com.spigonate.entity.column.Nullable;
import com.spigonate.entity.identity.GenerationPolicy;
import com.spigonate.mapping.annotation.OneToMany;
import gg.cubo.essentials.entity.chest.ChestContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

@Entity(tableName = "essentials_chest_account")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrivateChest {

    @Id(policy = GenerationPolicy.MANUAL_INCREMENT)
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private int limits;

    @OneToMany
    @Nullable
    private List<ChestContent> chests;

    public void addChest(ChestContent chest) {
        if (chests == null) {
            chests = Lists.newArrayList(chest);
        } else {
            chests.add(chest);
        }
    }

    public Collection<ChestContent> getSortedChests() {
        return chests.stream()
                .sorted(Comparator.comparingLong(ChestContent::getItemAmount))
                .collect(Collectors.toList());
    }

    public long getLastChestId() {
        return chests.stream()
                .max(Comparator.comparingLong(ChestContent::getId))
                .map(ChestContent::getId)
                .orElse(1L);
    }

    public int getLastChestIndex() {
        return chests.size();
    }

    public List<ChestContent> getContentWithItem() {
        return chests.stream()
                .filter(content -> content.getItemAmount() > 0)
                .collect(Collectors.toList());
    }

    public List<ChestContent> getContentWithoutItem() {
        return chests.stream()
                .filter(content -> content.getItemAmount() <= 0)
                .collect(Collectors.toList());
    }

    public static PrivateChest of(@NotNull Player player, int limit) {
        return new PrivateChest(
                player.getUniqueId().toString(),
                player.getName(),
                limit,
                null);
    }
}
