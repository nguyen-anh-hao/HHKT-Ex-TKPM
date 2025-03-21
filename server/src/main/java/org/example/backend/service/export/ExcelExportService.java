package org.example.backend.service.export;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.backend.dto.response.DiaChiResponse;
import org.example.backend.dto.response.GiayToResponse;
import org.example.backend.dto.response.SinhVienResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service
public class ExcelExportService implements ExportService {

    @RequiredArgsConstructor
    public static class Column<T> {
        @Getter
        private final String header;
        private final Function<T, String> valueExtractor;

        public String extractValue(T item) {
            return valueExtractor.apply(item);
        }
    }

    @Override
    public byte[] exportData(List<?> data) throws IOException {
        if (data.isEmpty()) {
            Workbook workbook = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            return out.toByteArray();
        }

        if (data.get(0) instanceof SinhVienResponse) {
            List<Column<SinhVienResponse>> columns = defaultSinhVienResponseColumns();
            return exportData((List<SinhVienResponse>) data, columns);
        }
        throw new IllegalArgumentException("Unsupported data type");
    }

    public <T> byte[] exportData(List<T> data, List<Column<T>> columns) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data Export");

        // Create header row dynamically
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            headerRow.createCell(i).setCellValue(columns.get(i).getHeader());
        }

        // Populate the sheet with data rows
        int rowIndex = 1;
        for (T item : data) {
            Row row = sheet.createRow(rowIndex++);
            for (int colIndex = 0; colIndex < columns.size(); colIndex++) {
                String cellValue = columns.get(colIndex).extractValue(item);
                row.createCell(colIndex).setCellValue(cellValue);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    private List<Column<SinhVienResponse>> defaultSinhVienResponseColumns() {
        return List.of(
                new Column<>("MSSV", SinhVienResponse::getMssv),
                new Column<>("Họ và tên", SinhVienResponse::getHoTen),
                new Column<>("Ngày sinh", response -> response.getNgaySinh().toString()),
                new Column<>("Giới tính", SinhVienResponse::getGioiTinh),
                new Column<>("Số điện thoại", SinhVienResponse::getSoDienThoai),
                new Column<>("Email", SinhVienResponse::getEmail),
                new Column<>("Khoa", SinhVienResponse::getKhoa),
                new Column<>("Khóa", SinhVienResponse::getKhoaHoc),
                new Column<>("Chương trình", SinhVienResponse::getChuongTrinh),
                new Column<>("Tình trạng", SinhVienResponse::getTinhTrang),
                new Column<>("Địa chỉ thường trú", response -> formatDiaChi(response.getDiaChis(), "Thường Trú")),
                new Column<>("Địa chỉ tạm trú", response -> formatDiaChi(response.getDiaChis(), "Tạm Trú")),
                new Column<>("Địa chỉ nhận thư", response -> formatDiaChi(response.getDiaChis(), "Nhận Thư")),
                new Column<>("CMND", response -> formatGiayTo(response.getGiayTos(), "CMND")),
                new Column<>("CCCD", response -> formatGiayTo(response.getGiayTos(), "CCCD")),
                new Column<>("Hộ chiếu", response -> formatGiayTo(response.getGiayTos(), "Hộ Chiếu"))
        );
    }

    private String formatDiaChi(List<DiaChiResponse> diaChiResponses, String type) {
        return diaChiResponses.stream()
                .filter(diaChiResponse -> diaChiResponse.getLoaiDiaChi().equals(type))
                .map(diaChiResponse -> diaChiResponse.getSoNhaTenDuong() + ", " + diaChiResponse.getPhuongXa() + ", " + diaChiResponse.getQuanHuyen() + ", " + diaChiResponse.getTinhThanhPho() + ", " + diaChiResponse.getQuocGia())
                .findFirst()
                .orElse("");
    }

    private String formatGiayTo(List<GiayToResponse> giayToResponses, String type) {
        return giayToResponses.stream()
                .filter(giayToResponse -> giayToResponse.getLoaiGiayTo().equals(type))
                .map(giayToResponse -> giayToResponse.getSoGiayTo() + ", " + giayToResponse.getNgayCap() + ", " + giayToResponse.getNoiCap())
                .findFirst()
                .orElse("");

    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    }
}
