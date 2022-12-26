package gg.cubo.essentials.entity.chest.mapper;

import com.spigonate.entity.ColumnMapper;
import com.spigonate.mapping.Mapper;
import gg.cubo.essentials.util.bukkit.InventorySerializer;
import org.bukkit.inventory.Inventory;

@ColumnMapper("LONGTEXT")
public class InventoryMapper implements Mapper<Inventory> {

    @Override
    public Object map(Inventory inventory) {
        return InventorySerializer.toBase64(inventory);
    }

    @Override
    public Inventory retrieve(Object o) {
        return InventorySerializer.fromBase64((String) o);
    }
}
