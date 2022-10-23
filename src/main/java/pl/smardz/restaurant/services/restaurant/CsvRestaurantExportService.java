package pl.smardz.restaurant.services.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.enums.FileHeaders;
import pl.smardz.restaurant.exceptions.ExportErrorException;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class CsvRestaurantExportService {
    private final RestaurantService service;

    public final HttpServletResponse export(HttpServletResponse response, String fileName, RestaurantRequest restaurantRequest) {
        try {
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", prepareHttpServletResponseHeader(fileName));
            export(response.getWriter(), restaurantRequest);

            log.info("The file " + fileName + "was successfully exported");

            return response;
        } catch (IOException e) {
            log.error("Error exporting CSV file", e);
            throw new ExportErrorException("Error exporting CSV file");
        }
    }

    private String prepareHttpServletResponseHeader(String fileName) {
        return "attachment; filename=\"" + fileName + ".csv\"";
    }

    private void export(Writer writer, RestaurantRequest restaurantRequest) {
        final CSVFormat skipFormat = CSVFormat.DEFAULT
                .withHeader(prepareHeaders());

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, skipFormat)) {
            for (RestaurantData restaurant : service.findRestaurants(restaurantRequest)) {
                csvPrinter.printRecord(
                        restaurant.getName(),
                        restaurant.getRestaurantPoint().getX(),
                        restaurant.getRestaurantPoint().getY(),
                        restaurant.getFoodType(),
                        restaurant.getDistance().getDistance(),
                        restaurant.getDistance().getUnit()
                );
            }
        } catch (IOException e) {
            log.error("Error exporting CVS file", e);
            throw new ExportErrorException("Error exporting CVS file");
        }
    }

    private String[] prepareHeaders() {
        return Stream.of(FileHeaders.values())
                .map(FileHeaders::name)
                .toArray(String[]::new);
    }


}
