package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entity.Category;
import com.devsuperior.dscatalog.entity.Product;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import com.devsuperior.dscatalog.repository.ProductRepository;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        //Page é uma stream
        Page<Product> list = productRepository.findAll(pageRequest);
        return list.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        //Optional serve para não trabalhar com valor nulo
        Optional<Product> obj = productRepository.findById(id);
        //a função orElseThrow lança uma exceção caso não encontrar uma entidade com esse id
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO productDTO) {
        Product entity = new Product();
        copyDtoToEntity(productDTO, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        try{
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(productDTO, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    //Método delete não precisa do método transacional
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ProductDTO productDTO, Product entity) {
        entity.setName(productDTO.getName());
        entity.setDescription(productDTO.getDescription());
        entity.setDate(productDTO.getDate());
        entity.setImgUrl(productDTO.getImgUrl());
        entity.setPrice(productDTO.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO categoryDto: productDTO.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDto.getId());
            entity.getCategories().add(category);
        }
    }
}