package ticket.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ticket.backend.entity.ProblemEntity;
import ticket.backend.repository.ProblemRepository;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public ProblemEntity saveIssueReport(ProblemEntity problemEntity) {

        return problemRepository.save(problemEntity);
    }
    public long getCountByStatusProblem(Integer statusProblem) {
        return problemRepository.countByStatusProblem(statusProblem);
    }
}