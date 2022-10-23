package pl.smardz.restaurant.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantPostControllerTest {
    private static final String EXPECTED_JSON = "[" +
            "{\"name\":\"MaÅ\u0082a Cafe\",\"restaurantPoint\":{\"x\":50.680172,\"y\":21.750402},\"distance\":{\"distance\":38.14897267219214,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\"]}" +
            ",{\"name\":\"Restauracja Å\u009AwiÄ\u0099tokrzyska\",\"restaurantPoint\":{\"x\":50.679908,\"y\":21.750197},\"distance\":{\"distance\":38.15675247799248,\"unit\":\"KILOMETERS\"},\"foodType\":[\"italian\"]}" +
            ",{\"name\":\"Bistro Podwale\",\"restaurantPoint\":{\"x\":50.678544,\"y\":21.745999},\"distance\":{\"distance\":38.41358486765367,\"unit\":\"KILOMETERS\"},\"foodType\":[\"cafÃ©\"]}" +
            ",{\"name\":\"NOBO restauracja\",\"restaurantPoint\":{\"x\":50.019529,\"y\":21.995636},\"distance\":{\"distance\":68.11243866488182,\"unit\":\"KILOMETERS\"},\"foodType\":[\"cafe\",\"italian\",\"winery\"]}" +
            ",{\"name\":\"NOBO restauracja\",\"restaurantPoint\":{\"x\":50.019529,\"y\":21.995636},\"distance\":{\"distance\":68.11243866488182,\"unit\":\"KILOMETERS\"},\"foodType\":[\"cafe\",\"italian\",\"winery\"]}" +
            ",{\"name\":\"NOBO restauracja\",\"restaurantPoint\":{\"x\":50.019529,\"y\":21.995636},\"distance\":{\"distance\":68.11243866488182,\"unit\":\"KILOMETERS\"},\"foodType\":[\"cafe\",\"italian\",\"winery\"]}" +
            ",{\"name\":\"Kocia Kawiarnia Kociarnia\",\"restaurantPoint\":{\"x\":50.064453,\"y\":19.945300},\"distance\":{\"distance\":176.13458528019413,\"unit\":\"KILOMETERS\"},\"foodType\":[\"brazilian\",\"cafÃ©\"]}" +
            ",{\"name\":\"Kocia Kawiarnia Kociarnia\",\"restaurantPoint\":{\"x\":50.064453,\"y\":19.945300},\"distance\":{\"distance\":176.13458528019413,\"unit\":\"KILOMETERS\"},\"foodType\":[\"brazilian\",\"cafÃ©\"]}" +
            ",{\"name\":\"Restauracja Alto\",\"restaurantPoint\":{\"x\":50.263808,\"y\":19.029422},\"distance\":{\"distance\":233.17921792725608,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\"]}" +
            ",{\"name\":\"LÃ¥dan\",\"restaurantPoint\":{\"x\":59.340133,\"y\":18.060096},\"distance\":{\"distance\":1007.3745126329355,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\",\"cafe\",\"winery\"]}" +
            ",{\"name\":\"LÃ¥dan\",\"restaurantPoint\":{\"x\":59.340133,\"y\":18.060096},\"distance\":{\"distance\":1007.3745126329355,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\",\"cafe\",\"winery\"]}" +
            ",{\"name\":\"LÃ¥dan\",\"restaurantPoint\":{\"x\":59.340133,\"y\":18.060096},\"distance\":{\"distance\":1007.3745126329355,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\",\"cafe\",\"winery\"]}" +
            ",{\"name\":\"Fogo de ChÃ£o Brazilian Steakhouse\",\"restaurantPoint\":{\"x\":42.348255,\"y\":-71.077444},\"distance\":{\"distance\":6721.862211612129,\"unit\":\"KILOMETERS\"},\"foodType\":[\"italian\"]}" +
            ",{\"name\":\"Cafe Polonia\",\"restaurantPoint\":{\"x\":42.328680,\"y\":-71.057120},\"distance\":{\"distance\":6722.137222399973,\"unit\":\"KILOMETERS\"},\"foodType\":[\"cafe\"]}" +
            ",{\"name\":\"RezdÃ´ra\",\"restaurantPoint\":{\"x\":40.738971,\"y\":-73.988399},\"distance\":{\"distance\":7020.299649672443,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\",\"fastfood\"]}" +
            ",{\"name\":\"RezdÃ´ra\",\"restaurantPoint\":{\"x\":40.738971,\"y\":-73.988399},\"distance\":{\"distance\":7020.299649672443,\"unit\":\"KILOMETERS\"},\"foodType\":[\"bar\",\"fastfood\"]}]";

    private static final String BODY_TO_RESTAURANTS_ENDPOINT = "{" +
            "    \"x\": 50.604918," +
            "    \"y\": 22.278260," +
            "    \"unit\": \"KILOMETERS\"" +
            "}";

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnTenRestauransWithAttributesTest() throws Exception {
       final String json = mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BODY_TO_RESTAURANTS_ENDPOINT))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals(EXPECTED_JSON, json);
    }


}
