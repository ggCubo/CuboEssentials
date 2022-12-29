package gg.cubo.essentials.entity.home.repository;

import com.spigonate.entity.interceptor.Eq;
import com.spigonate.entity.interceptor.OrderBy;
import com.spigonate.entity.interceptor.order.OrderType;
import com.spigonate.model.annotation.Repository;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.entity.home.Home;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface HomeRepository extends SpigonateRepository<Long, Home> {

    /**
     * Find all player homes based on his name
     * @param owner name that will be searched
     * @return collection with all homes
     */
    @OrderBy(value = "id", type = OrderType.ASC)
    @Nullable Collection<Home> findAllByOwner(@Eq String owner);

    /**
     * Search for a home based on its name
     *
     * @param name name that will be searched
     * @return optional with the home object
     */
    Optional<Home> findByName(String name);
}
