package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entity.Category;
import com.devsuperior.dscatalog.exception.EntityNotFoundException;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = categoryRepository.findAll();

        //Tranforma minha "list" em uma stream (forma mais fácil de trabalhar com coleções)
        //Percorre cada elemento do "list" e instancia um objeto do tipo "categoryDTO" com base no item que está sendo mapeado do "list"
        //Ao invés de usar expressão lamba usei method reference pois estou chamando apenas um método
        //Transforma novamente em lista
        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        //Optional serve para não trabalhar com valor nulo
        Optional<Category> obj = categoryRepository.findById(id);
        //a função orElseThrow lança uma exceção caso não encontrar uma entidade com esse id
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }
}