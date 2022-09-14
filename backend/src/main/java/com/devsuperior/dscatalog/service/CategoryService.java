package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entity.Category;
import com.devsuperior.dscatalog.service.exception.DatabaseException;
import com.devsuperior.dscatalog.service.exception.ResourceNotFoundException;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setName(categoryDTO.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        try{
            Category entity = categoryRepository.getReferenceById(id);
            entity.setName(categoryDTO.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    //Método delete não precisa do método transacional
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found " + id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }
}