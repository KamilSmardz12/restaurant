package pl.smardz.restaurant.services.calculators;

import org.springframework.stereotype.Service;
import pl.smardz.restaurant.payload.request.RestaurantRequest;

@Service
public class OffsetCalculator {
    public int calculateOffset(RestaurantRequest restaurantRequest, int pageSize) {
        Integer page = calculatePage(restaurantRequest.getPage());
        if (page.equals(0))
            return page;

        return pageSize * page;
    }

    private Integer calculatePage(Integer page) {
        return page != null && page > 0 ? page : 0;
    }

}
