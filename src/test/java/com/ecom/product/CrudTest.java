package com.ecom.product;

import com.ecom.product.controller.ProductController;
import com.ecom.product.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class CrudTest extends TestData {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductController productController;

    @Test
    public void productTest() throws Exception {
        List<ProductDTO> productList = Arrays.asList(product1, product2, product3);

        given(productController.getProducts()).willReturn((new Gson()).toJson(productList));
        given(productController.getProductById(Mockito.anyLong())).willReturn(new Gson().toJson(productList.get(0)));
        given(productController.updateProduct(Mockito.any())).willReturn(HttpStatus.OK);
        given(productController.addProduct(Mockito.any())).willReturn(HttpStatus.CREATED);
        given(productController.deleteProductById(Mockito.anyLong())).willReturn(HttpStatus.OK);

        String product1Name = product1.getName();

        // GET()
        mvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.*", hasSize(3)));

        // GET("/{id}")
        mvc.perform(get("/products/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.name", is(product1Name)));

        // ObjectMapper is used to convert any object to json format
        ObjectMapper objectMapper = new ObjectMapper();
        String product1Json = objectMapper.writeValueAsString(product1);

        // PUT("/update")
        mvc.perform(put("/products/update").contentType(MediaType.APPLICATION_JSON).content(product1Json)).andExpect(status().isOk());

        // POST("/post")
        // note : By default, status 200 is being returned by post request, but body contains "CREATED" message as specified in mocking addProduct() method
        mvc.perform(post("/products/post").contentType(MediaType.APPLICATION_JSON).content(product1Json)).andExpect(status().isOk());

        // DELETE("/delete/{id}")
        mvc.perform(delete("/products/delete/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
