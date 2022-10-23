package pl.smardz.restaurant.services.restaurant;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.smardz.restaurant.enums.CsvHeaders;
import pl.smardz.restaurant.payload.request.RestaurantRequest;
import pl.smardz.restaurant.payload.response.RestaurantData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CsvExportService {
    private static final Logger logger = LoggerFactory.getLogger(CsvExportService.class);

    private final RestaurantService service;

    public void writeRestaurantsToCsv(HttpServletResponse servletResponse, String fileName, RestaurantRequest restaurantRequest) {
        try {
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition", prepareHttpServletResponseHeader(fileName));
            writeRestaurantsToCsv(servletResponse.getWriter(), restaurantRequest);
        } catch (IOException e) {
            logger.error("Error while writing CSV ", e);
        }
    }

    private String prepareHttpServletResponseHeader(String fileName) {
        return "attachment; filename=\"" + fileName + ".csv\"";
    }

    private void writeRestaurantsToCsv(Writer writer, RestaurantRequest restaurantRequest) {
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
            logger.error("Error while writing CSV ", e);
        }
    }

    private String[] prepareHeaders() {
        return Stream.of(CsvHeaders.values())
                .map(CsvHeaders::name)
                .toArray(String[]::new);
    }


}
