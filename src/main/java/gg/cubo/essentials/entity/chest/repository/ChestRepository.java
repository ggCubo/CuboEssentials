package gg.cubo.essentials.entity.chest.repository;

import com.spigonate.model.annotation.Repository;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.entity.PrivateChest;

import java.util.Optional;

@Repository
public interface ChestRepository extends SpigonateRepository<String, PrivateChest> {

    Optional<PrivateChest> findByName(String name);

}
