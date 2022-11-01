package ru.glushenko.productlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.glushenko.productlist.model.dto.ProductDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties =
        {"spring.datasource.url=jdbc:postgresql://localhost:5432/producttest?currentSchema=public"})
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL_PREFIX = "/api/v1/products/";

    @Test
    @Sql(value = "/sql/product/addProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/deleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getProductById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + 10))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test product"))
                .andExpect(jsonPath("$.description").value("description product 1"))
                .andExpect(jsonPath("$.kcal").value(100));
    }

    @Test
    @Sql(value = "/sql/product/addProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/deleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getProductByNonExistentId() throws Exception {
        mockMvc.perform(get(URL_PREFIX + 5))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Record Not Found"))
                .andExpect(jsonPath("$.details[0]").value("Product with id= 5 not found"));
    }

    @Test
    @Sql(value = "/sql/product/addProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/deleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllProduct() throws Exception {
        mockMvc.perform(get(URL_PREFIX))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[1].name").value("test product 1"))
                .andExpect(jsonPath("$[1].description").value("description product 2"))
                .andExpect(jsonPath("$[1].kcal").value(200));
    }

    @Test
    @Sql(value = "/sql/product/addProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/deleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addProduct() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductDto(null, "test product 2", "description product 2", 300))))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.name").value("test product 2"))
                .andExpect(jsonPath("$.description").value("description product 2"))
                .andExpect(jsonPath("$.kcal").value(300));
    }

    @Test
    @Sql(value = "/sql/product/addProduct.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/product/deleteProduct.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addProductWithEmptyName() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductDto(null, "", "description product 2", 300))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.details[0]").value("name must not be empty"));
    }


}
