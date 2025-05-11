package ticket.backend.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.backend.entity.UserEntity;
import ticket.backend.repository.UserinfoRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserinfoService {

    @Autowired
    private UserinfoRepository userinfoRepository;

    public List<UserEntity> getAllUsers() {
        return userinfoRepository.findAll();
    }


    public UserEntity getUserById(Long id) {
        return userinfoRepository.findById(id).orElse(null);
    }

    public void updateUser(UserEntity updatedUser) {
        UserEntity existingUser = userinfoRepository.findById(updatedUser.getUserId()).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setStatusId(updatedUser.getStatusId());
            existingUser.setUpdateDate(LocalDateTime.now());
            userinfoRepository.save(existingUser);
        }
    }
    public boolean isEmailDuplicate(String email, Long userId) {
        return userinfoRepository.findAll().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email) && !user.getUserId().equals(userId));
    }


    public byte[] exportUsersToExcel() throws IOException {
        List<UserEntity> users = userinfoRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);

        CellStyle dataStyle = workbook.createCellStyle();

        Row headerRow = sheet.createRow(0);
        String[] columns = {"ลำดับ", "ชื่อ-นามสกุล", "อีเมล", "เบอร์โทร", "ชื่อผู้ใช้งาน", "สิทธิ์เข้าใช้งาน", "เข้าใช้งานล่าสุด", "สถานะ"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        sheet.setColumnWidth(0, 4000); // ลำดับ
        sheet.setColumnWidth(1, 8000); // ชื่อ-นามสกุล
        sheet.setColumnWidth(2, 10000); // อีเมล
        sheet.setColumnWidth(3, 5000); // เบอร์โทร
        sheet.setColumnWidth(4, 8000); // ชื่อผู้ใช้งาน
        sheet.setColumnWidth(5, 6000); // สิทธิ์เข้าใช้งาน
        sheet.setColumnWidth(6, 6000); // เข้าใช้งานล่าสุด
        sheet.setColumnWidth(7, 5000); // สถานะ

        int rowIndex = 1;
        for (UserEntity user : users) {
            // ตรวจสอบว่า username ไม่ใช่ "admin"
            if ("admin".equals(user.getUsername())) {
                continue; // ข้ามผู้ใช้งานที่มีชื่อว่า admin
            }

            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(user.getUserId());
            row.createCell(1).setCellValue(user.getFirstName() + " " + user.getLastName());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getPhoneNumber());
            row.createCell(4).setCellValue(user.getUsername());
            row.createCell(5).setCellValue(user.getRole());
            row.createCell(6).setCellValue(user.getLastLogin() != null ? user.getLastLogin().toString() : "N/A");
            row.createCell(7).setCellValue(user.getStatusId() == 1 ? "ใช้งาน" : "ปิดบัญชี");

            for (int i = 0; i < columns.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        headerRow.setHeightInPoints(25);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

}
