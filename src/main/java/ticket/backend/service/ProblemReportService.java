package ticket.backend.service;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ticket.backend.entity.ProblemEntity;
import ticket.backend.repository.ProblemReportRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemReportService {

    @Autowired
    private ProblemReportRepository problemReportRepository;

    public List<ProblemEntity> getAllProblems() {
        return problemReportRepository.findAll();
    }
    public long getCountByStatusProblem(Integer statusProblem) {
        return problemReportRepository.countByStatusProblem(statusProblem);
    }
    public Optional<ProblemEntity> findById(Long id) {
        return problemReportRepository.findById(id);
    }

    public void save(ProblemEntity problemEntity) {
        problemReportRepository.save(problemEntity);
    }
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ProblemEntity> problems = problemReportRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("รายงานการแจ้งปัญหาการใช้งาน");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ลำดับ", "ชื่อ","อีเมล","เบอรโทรที่ติดต่อ","ประเภทปัญหา", "รายละเอียด", "สถานะ", "วันที่แจ้ง"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        sheet.setColumnWidth(0, 10 * 256);  // ลำดับ
        sheet.setColumnWidth(1, 25 * 256);  // ชื่อ
        sheet.setColumnWidth(2, 25 * 256);  // อีเมล
        sheet.setColumnWidth(3, 25 * 256);  // เบอร์โทร
        sheet.setColumnWidth(4, 30 * 256);  // ประเภทปัญหา
        sheet.setColumnWidth(5, 40 * 256);  // รายละเอียด
        sheet.setColumnWidth(6, 20 * 256);  // สถานะ
        sheet.setColumnWidth(7, 25 * 256);  // วันที่แจ้ง

        int rowNum = 1;
        for (ProblemEntity problem : problems) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(problem.getFirstName() + " " + problem.getLastName());
            row.createCell(2).setCellValue(problem.getEmail());
            row.createCell(3).setCellValue(problem.getPhone());
            row.createCell(4).setCellValue(problem.getIssueType());
            row.createCell(5).setCellValue(problem.getDescription());
            row.createCell(6).setCellValue(getStatusText(problem.getStatusProblem()));
            row.createCell(7).setCellValue(problem.getCreatedAt().toString());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "รายงานการแจ้งปัญหาการใช้งาน.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public static String getStatusText(int status) {
        return switch (status) {
            case 1 -> "รอการแก้ไข";
            case 2 -> "กำลังดำเนินการ";
            case 3 -> "แก้ไขเสร็จสิ้น";
            case 9 -> "ยกเลิก";
            default -> "ไม่ทราบสถานะ";
        };
    }
}
