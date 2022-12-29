package gg.cubo.essentials.entity.mappers;

import com.spigonate.entity.ColumnMapper;
import com.spigonate.mapping.Mapper;
import org.bukkit.Material;

@ColumnMapper("TEXT")
public class MaterialMapper implements Mapper<Material> {

    @Override
    public Object map(Material material) {
        return material.name();
    }

    @Override
    public Material retrieve(Object o) {
        return Material.valueOf((String) o);
    }
}
