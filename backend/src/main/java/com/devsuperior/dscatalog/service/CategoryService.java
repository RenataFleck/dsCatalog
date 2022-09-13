package com.devsuperior.dscatalog.service;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entity.Category;
import com.devsuperior.dscatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = categoryRepository.findAll();

        //Tranforma minha "list" em uma stream (forma mais fácil de trabalhar com coleções)
        //Percorre cada elemento do "list" e instancia um objeto do tipo "categoryDTO" com base no item que está sendo mapeado do "list"
        //Ao invés de usar expressão lamba usei method reference pois estou chamando apenas um método
        //Transforma novamente em lista
        return list.stream().map(CategoryDTO::new).toList();
    }
}
