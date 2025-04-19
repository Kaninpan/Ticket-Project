package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ticket.backend.entity.UserEntity;
import ticket.backend.service.AddusersService;

@Controller // เพิ่ม @Controller ที่นี่
public class AdduserController {

    private final AddusersService addusersService;

    @Autowired
    public AdduserController(AddusersService addusersService) {
        this.addusersService = addusersService;
    }

    @GetMapping("/insert-info-us")
    public String showRegistrationForm(@ModelAttribute("user") UserEntity userEntity) {
        return "home/insert-info-us";
    }
    @PostMapping("/insert-info-us")
    public String registerUser(@ModelAttribute("user") UserEntity userEntity, RedirectAttributes redirectAttributes) {
        try {
            addusersService.saveUser(userEntity);
            redirectAttributes.addFlashAttribute("success", "สมัครสมาชิกสำเร็จ !");
            return "redirect:/dashboard";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("user", userEntity);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/insert-info-us";
        }
    }
}
