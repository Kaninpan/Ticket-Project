package ticket.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.backend.entity.ProblemEntity;
import ticket.backend.repository.ProblemReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemReportService {

    @Autowired
    private ProblemReportRepository problemReportRepository;

    public List<ProblemEntity> getAllProblems() {
        return problemReportRepository.findAll();
    }
    public long getCountByStatusProblem(Integer statusProblem) {
        return problemReportRepository.countByStatusProblem(statusProblem);
    }
    public Optional<ProblemEntity> findById(Long id) {
        return problemReportRepository.findById(id);
    }

    public void save(ProblemEntity problemEntity) {
        problemReportRepository.save(problemEntity);
    }
}
