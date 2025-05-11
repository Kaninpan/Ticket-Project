package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ticket.backend.entity.UserEntity;
import ticket.backend.service.UserinfoService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserinfoController {

    @Autowired
    private UserinfoService userinfoService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserEntity> allUsers = userinfoService.getAllUsers();

        List<UserEntity> filteredUsers = allUsers.stream()
                .filter(user -> !"admin".equalsIgnoreCase(user.getUsername()))
                .sorted(Comparator.comparing(UserEntity::getLastLogin).reversed())
                .collect(Collectors.toList());

        model.addAttribute("users", filteredUsers);
        return "home/userinfo";
    }


    @GetMapping("/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserEntity user = userinfoService.getUserById(id);
        model.addAttribute("user", user);
        return "home/edit-user";
    }

    @PostMapping("/update-user")
    public String updateUser(UserEntity updatedUser, RedirectAttributes redirectAttributes) {
        if (userinfoService.isEmailDuplicate(updatedUser.getEmail(), updatedUser.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "ไม่สามารถดำเนินการได้ เนื่องจากมีอีเมลนี้ใช้งานอยู่แล้ว");
            return "redirect:/edit-user/" + updatedUser.getUserId();
        }

        userinfoService.updateUser(updatedUser);
        redirectAttributes.addFlashAttribute("successMessage", "ทำการเปลี่ยนแปลงแก้ไขข้อมูลสำเร็จ !");
        return "redirect:/users";
    }



    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportUsersToExcel() throws IOException {
        byte[] excelData = userinfoService.exportUsersToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }
}