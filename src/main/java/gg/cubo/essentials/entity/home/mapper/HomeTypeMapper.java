package gg.cubo.essentials.entity.home.mapper;

import com.spigonate.entity.ColumnMapper;
import com.spigonate.mapping.Mapper;
import gg.cubo.essentials.entity.home.HomeType;

@ColumnMapper("VARCHAR(8)")
public class HomeTypeMapper implements Mapper<HomeType> {

    @Override
    public Object map(HomeType type) {
        return type.name();
    }

    @Override
    public HomeType retrieve(Object o) {
        return HomeType.valueOf((String) o);
    }
}
