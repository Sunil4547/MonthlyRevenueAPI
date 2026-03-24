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
class RevenueIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BASE_URL = "/orders";

    @Test
    @Order(1)
    void shouldCalculateRevenue_withDiscountApplied() throws Exception {

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

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(premiumOrder));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(regularOrder));

        mockMvc.perform(get(BASE_URL + "/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['2026-03']").value(1400));
        // 1000 -> 900 + 500 = 1400
    }

    @Test
    @Order(2)
    void shouldIgnoreInvalidOrders_inRevenue() throws Exception {

        String invalidOrder = """
            {
              "amount": -200,
              "customerType": "REGULAR",
              "orderDate": "2026-03-20"
            }
        """;

        String validOrder = """
            {
              "amount": 500,
              "customerType": "REGULAR",
              "orderDate": "2026-03-20"
            }
        """;

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validOrder));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidOrder));

        mockMvc.perform(get(BASE_URL + "/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['2026-03']").value(500));
    }
}