package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.User;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category findById(Long id);

    List<Category> findByUserId(Long id);

    List<Category> findByUserName(String username);

    Category create(Category category, String username);

    Category update(Category category, Long idCategory, User user);

    void delete(Long id);

    Category findByNameAndUsername(String name, String username);
}
