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

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("error", "");
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


    @GetMapping("/officer")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return "redirect:/home";
        }
        return "home/login";
    }


    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

}
