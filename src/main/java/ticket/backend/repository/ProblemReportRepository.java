package ticket.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.backend.entity.ProblemEntity;

public interface ProblemReportRepository extends JpaRepository<ProblemEntity, Long> {

    long countByStatusProblem(Integer statusProblem);
}
