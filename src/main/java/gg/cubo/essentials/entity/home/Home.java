package gg.cubo.essentials.entity.home;

import br.com.blecaute.inventory.property.InventoryProperty;
import br.com.blecaute.inventory.type.InventoryItem;
import com.spigonate.entity.Column;
import com.spigonate.entity.Entity;
import com.spigonate.entity.Id;
import com.spigonate.entity.Mapped;
import gg.cubo.essentials.Essentials;
import gg.cubo.essentials.placeholder.PlaceholderReplacer;
import gg.cubo.essentials.placeholder.Replaceable;
import gg.cubo.essentials.util.bukkit.LocationUtil;
import gg.cubo.essentials.util.bukkit.item.ItemBuilder;
import gg.cubo.essentials.util.bukkit.item.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity(tableName = "essentials_home")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Replaceable({
   "{id}", "{name}", "{owner}", "{location}",
   "{type}"
})
public class Home implements PlaceholderReplacer<Home>, InventoryItem {

    @Id
    @Column
    private long id;

    @Column
    private String name;

    @Column
    private String owner;

    @Mapped
    @Column
    private Location location;

    @Mapped
    @Column
    private HomeType type;

    @Mapped
    @Column
    @Nullable
    private Material icon;

    @Override
    public @Nullable ItemStack getItem(@NotNull Inventory inventory, @NotNull InventoryProperty inventoryProperty) {
        ItemBuilder builder = new ItemBuilder(ItemUtil.toItem(Essentials.getInstance(), "homes-icon"));
        builder.setMaterial(icon);

        return builder.setMaterial(icon)
                .setName(replace(builder.getName(), this))
                .setLore(builder.getLore().stream()
                        .map(line -> replace(line, this))
                        .collect(Collectors.toList())
        ).toItemStack();
    }

    @Override
    public List<String> replace(Home data) {
        return Arrays.asList(
                String.valueOf(data.getId()),
                data.getName(),
                data.getOwner(),
                LocationUtil.beauty(location, ChatColor.AQUA, ChatColor.WHITE),
                type.getTranslated()
        );
    }
}
