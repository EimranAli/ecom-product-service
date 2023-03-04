package com.ecom.product.controller;

import com.ecom.product.dto.ProductDTO;
import com.ecom.product.entity.Product;
import com.ecom.product.service.ProductService;
import com.ecom.product.utilities.EntityDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getProducts() {
        List<Product> products = productService.getProducts();

        ModelMapper modelMapper = new ModelMapper();
        List<ProductDTO> productDTOS = products.stream().map(entity -> modelMapper.map(entity, ProductDTO.class)).toList();

        return productDTOS.toString();
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        EntityDTOConverter entityDTOConverter = new EntityDTOConverter();
        return entityDTOConverter.entityToDTO(product).toString();
    }

    @PutMapping("/update")
    public HttpStatus updateProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return HttpStatus.OK;
    }

    @PostMapping("/post")
    public HttpStatus addProduct(@RequestBody ProductDTO productDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(productDTO, Product.class);
        productService.addProduct(product);
        return HttpStatus.CREATED;
    }

    @DeleteMapping("/delete/{productId}")
    public HttpStatus deleteProductById(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return HttpStatus.OK;
    }
}
