package ticket.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportUsersToExcel() throws IOException {
        byte[] excelData = userinfoService.exportUsersToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }
}