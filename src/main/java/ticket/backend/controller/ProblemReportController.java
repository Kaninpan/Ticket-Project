package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ticket.backend.entity.ProblemEntity;
import ticket.backend.service.ProblemReportService;

import java.util.Optional;

@Controller
public class ProblemReportController {

    @Autowired
    private ProblemReportService problemReportService;

    @GetMapping("/problem-report")
    public String showProblemReportPage(Model model) {
        model.addAttribute("problems", problemReportService.getAllProblems());
        return "home/problem-report";
    }
    @PostMapping("/problem-report")
    public String updateProblemStatus(@RequestParam Long id, @RequestParam Integer statusProblem, RedirectAttributes redirectAttributes) {
        Optional<ProblemEntity> optionalProblem = problemReportService.findById(id);

        if (optionalProblem.isPresent()) {
            ProblemEntity problem = optionalProblem.get();
            problem.setStatusProblem(statusProblem);
            problemReportService.save(problem);
            redirectAttributes.addFlashAttribute("success", "อัพเดตสถานะเรียบร้อยแล้ว");
        } else {
            redirectAttributes.addFlashAttribute("error", "ไม่พบรายการปัญหานี้");
        }

        return "redirect:/problem-report";
    }
}

