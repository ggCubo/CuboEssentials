package gg.cubo.essentials.entity.chest;

import br.com.blecaute.inventory.property.InventoryProperty;
import br.com.blecaute.inventory.type.InventoryItem;
import com.spigonate.entity.Column;
import com.spigonate.entity.Entity;
import com.spigonate.entity.Id;
import com.spigonate.entity.Mapped;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.placeholder.PlaceholderReplacer;
import gg.cubo.essentials.placeholder.Replaceable;
import gg.cubo.essentials.util.bukkit.item.ItemBuilder;
import gg.cubo.essentials.util.bukkit.item.ItemTranslates;
import gg.cubo.essentials.util.bukkit.item.ItemUtil;
import lombok.*;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity(tableName = "essentials_chest_content")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(setterPrefix = "with")
@Data
@Replaceable({"{id}", "{items}", "{icon}", "{lines}"})
public class ChestContent implements InventoryItem, PlaceholderReplacer<ChestContent> {

    @Id
    @Column
    private long id;

    @Column
    private int line;

    @Mapped
    @Column
    private Material icon;

    @Mapped
    @Column
    private ItemStack[] content;

    public long getItemAmount() {
        return Arrays.stream(content)
                .filter(Objects::nonNull)
                .filter(item -> item.getType() != Material.AIR)
                .mapToInt(ItemStack::getAmount)
                .sum();
    }

    @Override
    public @Nullable ItemStack getItem(@NotNull Inventory inventory, @NotNull InventoryProperty inventoryProperty) {
        ItemBuilder builder = new ItemBuilder(ItemUtil.toItem(Essentials.getInstance(), "particular-chest-icon"));
        builder.setMaterial(icon);

        return builder.setName(replace(builder.getName(), this))
                .setLore(builder.getLore().stream()
                        .map(line -> replace(line, this))
                        .collect(Collectors.toList()))
                .toItemStack();
    }

    @Override
    public List<String> replace(ChestContent data) {
        return Arrays.asList(
                String.valueOf(data.getId()),
                String.valueOf(data.getItemAmount()),
                ItemTranslates.valueOf(icon, (short) 0).getItemName(),
                String.valueOf(data.getLine())
        );
    }
}
