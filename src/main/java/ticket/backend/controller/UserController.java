package ticket.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ticket.backend.entity.UserEntity;
import ticket.backend.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("error", ""); // เริ่มต้นด้วยข้อความข้อผิดพลาดเป็นค่าว่าง
        return "home/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserEntity userEntity, Model model) {
        try {
            userService.saveUser(userEntity);
            return "redirect:/officer";
        } catch (IllegalArgumentException e) {
            model.addAttribute("user", userEntity); // ส่งค่าที่กรอกไปด้วยเพื่อไม่ให้ข้อมูลหาย
            model.addAttribute("error", e.getMessage()); // ส่งข้อผิดพลาดไปที่ view
            return "home/register"; // ส่งผู้ใช้กลับไปที่ฟอร์มการลงทะเบียน
        }
    }

    @GetMapping("/officer")
    public String login() {
        return "home/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

}
