package gg.cubo.essentials.entity.chest.repository;

import com.spigonate.entity.interceptor.Distinct;
import com.spigonate.entity.interceptor.Gt;
import com.spigonate.entity.interceptor.Limit;
import com.spigonate.entity.interceptor.OrderBy;
import com.spigonate.entity.interceptor.order.OrderType;
import com.spigonate.model.annotation.Repository;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.entity.PrivateChest;

import java.util.Collection;
import java.util.Optional;

/**
 * Chest repository used to handle some custom methods handled
 * by an invoke handler proxy.
 *
 * @author Async
 */
@Repository
public interface ChestRepository extends SpigonateRepository<String, PrivateChest> {

    /**
     * Find a private chest by the player name
     *
     * @param name player name
     * @return optional with the private chest found
     */
    Optional<PrivateChest> findByName(String name);

    /**
     * Search for all private chests.
     * Order the results by descreasing and will limit results up to 100
     *
     * @return 10 sorted values by limit
     */
    @OrderBy(value = "limits", type = OrderType.DESC)
    @Distinct
    Collection<PrivateChest> findAllSorted();

    /**
     * Search for all private chests where the limit is greater than the @param limit
     * also, this will order the results by ascending and will limit results up to 10
     *
     * @param limit param used to determine how much the player needs to be searched for
     * @return 10 sorted values by limit
     */
    @Limit(10)
    @OrderBy(value = "limits", type = OrderType.ASC)
    @Distinct
    Collection<PrivateChest> findAllByLimits(@Gt int limit);


}
