import com.jayway.jsonpath.JsonPath;
import org.example.MonthlyRevenueApp;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MonthlyRevenueApp.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static Long orderId1;
    private static Long orderId2;

    private final String BASE_URL = "/orders";

    @Test
    @Order(1)
    void shouldCreateOrders() throws Exception {

        String premiumOrder = """
            {
              "amount": 1000,
              "customerType": "PREMIUM",
              "orderDate": "2026-03-10"
            }
        """;

        String regularOrder = """
            {
              "amount": 500,
              "customerType": "REGULAR",
              "orderDate": "2026-03-15"
            }
        """;

        String res1 = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(premiumOrder))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res2 = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regularOrder))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id1 = JsonPath.read(res1, "$.id");
        Number id2 = JsonPath.read(res2, "$.id");

        orderId1 = id1.longValue();
        orderId2 = id2.longValue();

    }

    @Test
    @Order(2)
    void shouldGetOrderById() throws Exception {

        String request = """
        {
          "amount": 1000,
          "customerType": "PREMIUM",
          "orderDate": "2026-03-10"
        }
    """;

        String response = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number id = JsonPath.read(response, "$.id");
        Long orderId = id.longValue();

        mockMvc.perform(get(BASE_URL + "/" + orderId1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId1));
    }

    @Test
    @Order(3)
    void shouldGetOrdersByMonth() throws Exception {

        mockMvc.perform(get(BASE_URL)
                        .param("month", "2026-03"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(4)
    void shouldReturnBadRequest_forInvalidInput() throws Exception {

        String invalidRequest = """
            {
              "amount": -100,
              "customerType": "PREMIUM"
            }
        """;

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void shouldReturnNotFound_whenOrderMissing() throws Exception {

        mockMvc.perform(get(BASE_URL + "/9999"))
                .andExpect(status().isNotFound());
    }
}