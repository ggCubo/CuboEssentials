package gg.cubo.essentials.entity.mappers;

import com.spigonate.entity.ColumnMapper;
import com.spigonate.mapping.Mapper;
import gg.cubo.essentials.util.bukkit.LocationUtil;
import org.bukkit.Location;

@ColumnMapper("LONGTEXT")
public class LocationMapper implements Mapper<Location> {

    @Override
    public Object map(Location location) {
        return LocationUtil.serialize(location);
    }

    @Override
    public Location retrieve(Object o) {
        return LocationUtil.deserialize((String) o);
    }
}
