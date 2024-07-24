package devsu.msvcaccount.repositories;

import devsu.msvcaccount.models.entity.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementTypeRepository extends JpaRepository<MovementType, Long> {

    MovementType findByName(String name);
}
