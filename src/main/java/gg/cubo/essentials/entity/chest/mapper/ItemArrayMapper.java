package gg.cubo.essentials.entity.chest.mapper;

import com.spigonate.entity.ColumnMapper;
import com.spigonate.mapping.Mapper;
import gg.cubo.essentials.util.bukkit.ItemSerializer;
import org.bukkit.inventory.ItemStack;

@ColumnMapper("LONGTEXT")
public class ItemArrayMapper implements Mapper<ItemStack[]> {

    @Override
    public Object map(ItemStack[] itemStacks) {
        return ItemSerializer.toBase64List(itemStacks);
    }

    @Override
    public ItemStack[] retrieve(Object o) {
        return ItemSerializer.fromBase64List((String) o);
    }
}
