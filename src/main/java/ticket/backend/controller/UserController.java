package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ticket.backend.entity.UserEntity;
import ticket.backend.service.UserDetailsImpl;
import ticket.backend.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
//        model.addAttribute("error", "");
        return "home/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity userEntity, RedirectAttributes redirectAttributes) {
        try {
            userService.saveUser(userEntity);
            redirectAttributes.addFlashAttribute("success", "สมัครสมาชิกสำเร็จ !");
            return "redirect:/officer";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("user", userEntity);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }


    @GetMapping("/editprofile")
    public String showEditProfileForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserEntity user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("error", "");
        return "home/editprofile";
    }

    @PostMapping("/editprofile")
    public String updateProfile(@ModelAttribute("user") UserEntity userEntity,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        UserEntity existingUser = userService.findByUsername(userDetails.getUsername());

        if (existingUser == null) {
            model.addAttribute("error", "ไม่พบบัญชีผู้ใช้");
            return "home/editprofile";
        }

        try {
            existingUser.setFirstName(userEntity.getFirstName());
            existingUser.setLastName(userEntity.getLastName());
            existingUser.setPhoneNumber(userEntity.getPhoneNumber());

            if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
                existingUser.setPassword(userService.encodePassword(userEntity.getPassword()));
            }

            userService.updateUser(existingUser);
            redirectAttributes.addFlashAttribute("success", "โปรไฟล์ของคุณได้รับการอัปเดตเรียบร้อยแล้ว!");
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            model.addAttribute("user", userEntity);
            model.addAttribute("error", e.getMessage());
            return "home/editprofile";
        }
    }


    @GetMapping("/editprofile-admin")
    public String showEditProfileFormadmin(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserEntity user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("error", "");
        return "home/editprofile-admin";
    }

    @PostMapping("/editprofile-admin")
    public String updateProfileadmin(@ModelAttribute("user") UserEntity userEntity,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        UserEntity existingUser = userService.findByUsername(userDetails.getUsername());

        if (existingUser == null) {
            model.addAttribute("error", "ไม่พบบัญชีผู้ใช้");
            return "home/editprofile-admin";
        }

        try {
            existingUser.setFirstName(userEntity.getFirstName());
            existingUser.setLastName(userEntity.getLastName());
            existingUser.setPhoneNumber(userEntity.getPhoneNumber());

            if (userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) {
                existingUser.setPassword(userService.encodePassword(userEntity.getPassword()));
            }

            userService.updateUser(existingUser);
            redirectAttributes.addFlashAttribute("success", "โปรไฟล์ของคุณได้รับการอัปเดตเรียบร้อยแล้ว!");
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("user", userEntity);
            model.addAttribute("error", e.getMessage());
            return "home/editprofile-admin";
        }
    }


    @GetMapping("/officer")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            String role = userDetails.getUserEntity().getRole();
            if (role != null) {
                if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("ROLE_ADMIN")) {
                    return "redirect:/dashboard";
                } else if (role.equalsIgnoreCase("User") || role.equalsIgnoreCase("ROLE_USER")) {
                    return "redirect:/home";
                }
            }
        }
        return "home/login";
    }

    @GetMapping("/guide")
    public String showGuide() {
        return "home/guide";
    }

    @GetMapping("/faq")
    public String showFaq() {
        return "home/faq";
    }

    @GetMapping("/useaiinwork")
    public String showAi() {
        return "home/useaiinwork";
    }

    @GetMapping("/dashboard")
    public String showHomePage(Model model) {
        long pendingCount = userService.getCountByStatusProblem(1); // ปัญหาที่รอการแก้ไข
        long inProgressCount = userService.getCountByStatusProblem(2); // ปัญหาที่กำลังดำเนินการ
        long resolvedCount = userService.getCountByStatusProblem(3); // ปัญหาที่แก้ไขเสร็จสิ้น
        long canceledCount = userService.getCountByStatusProblem(9); // ปัญหาที่ยกเลิกจากผู้ใช้งาน
        long totalCount = pendingCount + inProgressCount + resolvedCount + canceledCount; // คำนวณรวมทั้งหมด

        LocalDate today = LocalDate.now();
        Locale thaiLocale = new Locale("th", "TH");
        String formattedDate = today.getDayOfMonth() + " " +
                today.getMonth().getDisplayName(TextStyle.FULL, thaiLocale) + " " +
                (today.getYear() + 543);

        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("resolvedCount", resolvedCount);
        model.addAttribute("canceledCount", canceledCount); // เพิ่มข้อมูลนี้
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("updateDate", formattedDate);

        return "home/dashboard";
    }

//    @GetMapping("/home")
//    public String home() {
//        return "home/home";
//    }

}
