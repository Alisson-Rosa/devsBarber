package project.devsbarber.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Cut;
import project.devsbarber.model.enums.TypeCut;

@Repository
@Transactional
public interface CutRepository extends JpaRepository<Cut, Long> {

    @Query(value = "SELECT c FROM Cut c order by c.id")
    Page<Cut> findAllBarbersWhitPagination(Pageable pageable);

    Cut findByTypeCut(TypeCut typeCut);
}