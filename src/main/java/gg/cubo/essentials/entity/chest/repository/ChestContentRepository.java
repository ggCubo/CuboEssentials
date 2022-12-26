package gg.cubo.essentials.entity.chest.repository;

import com.spigonate.model.annotation.Repository;
import com.spigonate.repository.SpigonateRepository;
import gg.cubo.essentials.entity.chest.ChestContent;

import java.util.Optional;

@Repository
public interface ChestContentRepository extends SpigonateRepository<Long, ChestContent> {

    Optional<ChestContent> findByLine(int line);

}
