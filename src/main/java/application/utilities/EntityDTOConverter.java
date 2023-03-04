package com.ecommerce.application.utilities;

import com.ecommerce.application.dto.ProductDTO;
import com.ecommerce.application.entity.Product;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EntityDTOConverter {

    public Object entityToDTO(Object object) {
        ModelMapper mapper = new ModelMapper();

        ProductDTO productDTO = mapper.map((Product) object, ProductDTO.class);
        return productDTO;
    }

    public List<Object> entityToDTO(List<Product> productList) {
        return productList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }
}
