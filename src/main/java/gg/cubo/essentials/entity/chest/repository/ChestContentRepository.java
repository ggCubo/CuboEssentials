package gg.cubo.essentials.entity.chest.repository;

import com.spigonate.model.annotation.Repository;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.entity.chest.ChestContent;

/**
 * Chest content repository used to handle some custom methods handled
 * by an invoke handler proxy.
 *
 * @author Async
 */
@Repository
public interface ChestContentRepository extends SpigonateRepository<Long, ChestContent> {
}
