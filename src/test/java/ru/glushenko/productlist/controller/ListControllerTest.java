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
import ru.glushenko.productlist.api.request.AddToListRequest;
import ru.glushenko.productlist.model.dto.ListDto;
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
public class ListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String URL_PREFIX = "/api/v1/lists/";

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getListById() throws Exception {
        mockMvc.perform(get(URL_PREFIX + 10))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test list"))
                .andExpect(jsonPath("$.products.size()").value(2))
                .andExpect(jsonPath("$.products[0].id").value(10))
                .andExpect(jsonPath("$.products[0].name").value("test product"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getListByNonExistentId() throws Exception {
        mockMvc.perform(get(URL_PREFIX + 5))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Record Not Found"))
                .andExpect(jsonPath("$.details[0]").value("List with id= 5 not found"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllProduct() throws Exception {
        mockMvc.perform(get(URL_PREFIX))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("test list"))
                .andExpect(jsonPath("$[0].amount_kcal").value(300))
                .andExpect(jsonPath("$[0].products.size()").value(2))
                .andExpect(jsonPath("$[0].products[0].id").value(10))
                .andExpect(jsonPath("$[0].products[0].name").value("test product"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addList() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ListDto(null, "test list 2"))))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(12))
                .andExpect(jsonPath("$.name").value("test list 2"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addListWithEmptyName() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ListDto(null, ""))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.details[0]").value("name must not be empty"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addProductToList() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "addtolist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddToListRequest(11L, 11L))))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("test list 1"))
                .andExpect(jsonPath("$.amount_kcal").value(300))
                .andExpect(jsonPath("$.products.size()").value(2))
                .andExpect(jsonPath("$.products[1].id").value(11))
                .andExpect(jsonPath("$.products[1].name").value("test product 1"));
    }

    @Test
    @Sql(value = "/sql/list/addList.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/list/deleteList.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addDuplicateProductToList() throws Exception {
        mockMvc.perform(post(URL_PREFIX + "addtolist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddToListRequest(10L, 10L))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Duplicate element"))
                .andExpect(jsonPath("$.details[0]").value("Product already added"));
    }
}
