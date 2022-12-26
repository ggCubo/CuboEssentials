package gg.cubo.essentials.entity.chest;

import br.com.blecaute.inventory.property.InventoryProperty;
import br.com.blecaute.inventory.type.InventoryItem;
import com.spigonate.entity.Column;
import com.spigonate.entity.Entity;
import com.spigonate.entity.Id;
import com.spigonate.entity.Mapped;
import gg.cubo.essentials.util.bukkit.ItemBuilder;
import lombok.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "essentials_chest_content")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class ChestContent implements InventoryItem {

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
        return new ItemBuilder(icon)
                .setName("§bBaú particular")
                .setLore(Arrays.asList(
                       "§fBotão direito §8- §7troca o ícone do baú particular",
                       "§fBotão esquerdo §8- §7acessa o baú particular",
                       "",
                       "§eItens armazenados: §f" + getItemAmount()
                ))
                .toItemStack();
    }
}
