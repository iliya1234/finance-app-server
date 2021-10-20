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
import java.util.Objects;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

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
        if (result == null) {
            log.warn("IN findById - no category found by id: {}", id);
            return null;
        }
        log.info("IN findById - category: {} found by id: {}", result, id);
        return result;

    }

    @Override
    public List<Category> findByUserId(Long id) {
        List<Category> result = categoryRepository.findByUserId(id);
        log.info("IN getByUserId - {} category found with id: {}", result.size(), id);
        return result;
    }

    @Override
    public List<Category> findByUserName(String name) {
        List<Category> result = categoryRepository.findByUserUsername(name);
        log.info("IN getByUserName - {} category found with name: {}", result.size(), name);
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public Category create(Category category, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("IN findByUsername - no user found by username: {}", username);
            return null;
        }
        category.setUser(user);
        categoryRepository.save(category);
        log.info("IN create - category: {} successfully created", category);
        return category;
    }

    @Override
    public Category update(Category category, Long idCategory, User user) {
        List<Category> categoryList = categoryRepository.findByUserUsername(user.getUsername());
        long errorCount = categoryList
                .stream()
                .filter(x -> x.getName().equals(category.getName()))
                .filter(x -> !Objects.equals(x.getId(), idCategory))
                .count();
        if (errorCount > 0) {
            return null;
        }
        category.setId(idCategory);
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
    public Category findByNameAndUsername(String name, String username) {
        Category result = categoryRepository.findByNameAndUserUsername(name, username);
        if (result == null) {
            log.warn("IN findByNameAndUsername - no category found by name: {}", name);
        }
        return result;
    }

}
