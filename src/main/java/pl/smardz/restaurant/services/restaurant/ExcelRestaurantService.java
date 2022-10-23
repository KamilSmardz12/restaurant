package pl.smardz.restaurant.services.restaurant;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.enums.CsvHeaders;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

@Service
@Slf4j
public class ExcelRestaurantService {
    private static final int HEADERS_FONT_HEIGHT = 16;
    private static final int ROWS_FONT_HEIGHT = 16;
    private static final String SHEET_NAME = "Restaurants";

    private final RestaurantService restaurantService;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public ExcelRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(SHEET_NAME);
    }

    public final HttpServletResponse export(HttpServletResponse response, RestaurantRequest restaurantRequest, String fileName) {
        try {
            response.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; fileName=" + fileName + ".xlsx";
            response.setHeader(headerKey, headerValue);

            export(response, restaurantRequest);

            log.info("The file " + fileName + "was successfully exported");

            return response;
        } catch (IOException e) {
            log.error("Error while writing xlsx ", e);
        }

        return response;
    }

    private void export(HttpServletResponse response, RestaurantRequest restaurantRequest) throws IOException {
        writeHeaderLine();
        writeDataLines(restaurantRequest);

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void writeHeaderLine() {
        final Row row = sheet.createRow(0);
        final CellStyle style = configureStyleHeaders();
        final CsvHeaders[] headers = CsvHeaders.values();

        for (int i = 0; i < headers.length; i++) {
            createCell(sheet, row, i, headers[i].name(), style);
        }
    }

    private CellStyle configureStyleHeaders() {
        CellStyle style = workbook.createCellStyle();
        style.setFont(configureFontHeaders());

        return style;
    }

    private XSSFFont configureFontHeaders() {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(HEADERS_FONT_HEIGHT);

        return font;
    }

    private void createCell(XSSFSheet xssfSheet, Row row, int columnCount, Object value, CellStyle style) {
        xssfSheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        cell.setCellStyle(style);

        if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Set<?>) {
            cell.setCellValue(value.toString());
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void writeDataLines(RestaurantRequest restaurantRequest) {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(ROWS_FONT_HEIGHT);
        style.setFont(font);

        int rowCount = 1;
        for (RestaurantData restaurant : restaurantService.findRestaurants(restaurantRequest)) {
            final Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            createCell(sheet, row, columnCount++, restaurant.getName(), style);
            createCell(sheet, row, columnCount++, restaurant.getRestaurantPoint().getX(), style);
            createCell(sheet, row, columnCount++, restaurant.getRestaurantPoint().getY(), style);
            createCell(sheet, row, columnCount++, restaurant.getFoodType(), style);
            createCell(sheet, row, columnCount++, restaurant.getDistance().getDistance(), style);
            createCell(sheet, row, columnCount, restaurant.getDistance().getUnit().getUnitShortcut(), style);
        }
    }

}