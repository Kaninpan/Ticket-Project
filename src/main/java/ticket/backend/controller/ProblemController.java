package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ticket.backend.entity.ProblemEntity;
import ticket.backend.entity.UserEntity;
import ticket.backend.repository.ProblemRepository;
import ticket.backend.repository.UserRepository;
import ticket.backend.service.ProblemService;
import ticket.backend.service.UserDetailsImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Controller
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @GetMapping("/problem")
    public String showProblemForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {


        UserEntity username = userRepository.findByUsername(userDetails.getUsername());

        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setFirstName(username.getFirstName());
        problemEntity.setLastName(username.getLastName());
        problemEntity.setEmail(username.getEmail());
        problemEntity.setPhone(username.getPhoneNumber());

        model.addAttribute("report", problemEntity);
        model.addAttribute("issueTypes", List.of("ปัญหาการใช้งานอินเตอร์เน็ต", "เข้าใช้งานโปรแกรมไม่ได้","คอมพิวเตอร์เปิดไม่ติด","เครื่องปริ้นเตอร์มีปัญหา","ปัญหาอื่นๆ")); // ตัวอย่างประเภทปัญหา

        return "home/problem";
    }

    @PostMapping("/problem")
    public String submitProblemForm(@ModelAttribute("report") ProblemEntity report,
                                    @RequestParam("imageFile") MultipartFile imageFile,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (!imageFile.isEmpty()) {
                String firstName = report.getFirstName();
                String originalFilename = imageFile.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String filename = firstName + "_" + timestamp + extension;
                String uploadDir = Paths.get("uploads").toAbsolutePath().normalize().toString();
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                File file = new File(uploadDir + File.separator + filename);
                imageFile.transferTo(file);
                report.setProfileImage(filename);
            }
            report.setStatusProblem(1);
            report.setCreatedAt(LocalDateTime.now());
            problemService.saveIssueReport(report);

            redirectAttributes.addFlashAttribute("success", "ส่งข้อมูลเรียบร้อยแล้ว!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "เกิดข้อผิดพลาดในการส่งข้อมูล: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "เกิดข้อผิดพลาด: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/problem";
    }

    @GetMapping("/report-problem")
    public String showProblemList(Model model, Principal principal) {
        String username = principal.getName();
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            return "error/404";
        }
        String email = user.getEmail();
        List<ProblemEntity> problems = problemRepository.findByEmail(email);

        model.addAttribute("problems", problems);
        return "home/report-problem";
    }


    @GetMapping("/home")
    public String showHomePage(Model model) {
        long pendingCount = problemService.getCountByStatusProblem(1);
        long inProgressCount = problemService.getCountByStatusProblem(2);
        long resolvedCount = problemService.getCountByStatusProblem(3);
        long totalCount = pendingCount + inProgressCount + resolvedCount;


//        String successMessage = "เข้าสู่ระบบสำเร็จ";

        LocalDate today = LocalDate.now();
        Locale thaiLocale = new Locale("th", "TH");
        String formattedDate = today.getDayOfMonth() + " " +
                today.getMonth().getDisplayName(TextStyle.FULL, thaiLocale) + " " +
                (today.getYear() + 543);

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("resolvedCount", resolvedCount);
        model.addAttribute("totalCount", totalCount);
//        model.addAttribute("success", successMessage);
        model.addAttribute("updateDate", formattedDate);

        return "home/home";
    }


}



