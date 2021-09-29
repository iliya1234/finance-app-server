package net.proselyte.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.CategoryRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Category> getAll() {
        List<Category> result = categoryRepository.findAll();
        log.info("IN getAll - {} categories found", result.size());
        return result;
    }

    @Override
    public Category findById(Long id) {
        Category result = categoryRepository.findById(id).orElse(null);
        if (result == null){
            log.warn("IN findById - no category found by id: {}", id);
            return null;
        }
        log.info("IN findById - category: {} found by id: {}", result);
        return result;

    }

    @Override
    public List<Category> findByUserId(Long id) {
        List<Category> result = categoryRepository.findByUserId(id);
        log.info("IN getByUserId - {} category found with id: {}", result.size(),id);
        return result;
    }

    @Override
    public Category create(Category category,Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }
        category.setUser(user);
        categoryRepository.save(category);
        log.info("IN create - category: {} successfully created", category);
        return category;
    }

    @Override
    public Category update(Category category, Long id,User user) {
        category.setId(id);
        category.setUser(user);
        categoryRepository.save(category);
        log.info("IN update - category: {} successfully updated", category);
        return category;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findByName(String name,Long idUser) {
        List<Category> categoryList = categoryRepository.findByName(name);
        if (categoryList == null){
            log.warn("IN findByName - no category found by name: {}", name);
            return null;
        }
        Category result = null;
        for (Category category : categoryList){
            if(category.getUser().getId()==idUser){
                result=category;
            }
        }
        if (result == null){
            log.warn("IN findByName - no category found by name: {}", name);
            return null;
        }
        log.info("IN findByName category: {} found by name: {}", result);
        return result;
    }
}
