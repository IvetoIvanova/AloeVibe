package com.example.aloevibe.web;

import com.example.aloevibe.model.dto.CategoryDTO;
import com.example.aloevibe.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void index_shouldReturnCategoriesInView() throws Exception {
        List<CategoryDTO> categories = List.of(
                new CategoryDTO(UUID.randomUUID(), "Category1", "Description1", "url1"),
                new CategoryDTO(UUID.randomUUID(), "Category2", "Description2", "url2")
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", categories))
                .andExpect(view().name("index"));
    }
}
