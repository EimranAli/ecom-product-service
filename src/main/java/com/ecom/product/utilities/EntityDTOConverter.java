package com.ecom.product.utilities;

import org.modelmapper.ModelMapper;
import com.ecom.product.entity.Product;
import com.ecom.product.dto.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDTOConverter {

    public ProductDTO entityToDTO(Product product) {
        ModelMapper mapper = new ModelMapper();

        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        return productDTO;
    }

    public List<ProductDTO> entityToDTO(List<Product> productList) {
        return productList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }
}
