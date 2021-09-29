package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.Category;
import net.proselyte.jwtappdemo.model.User;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category findById(Long id);

    List<Category> findByUserId(Long id);

    Category create(Category category, Long id);

    Category update(Category category, Long id, User user);

    void delete(Long id);

    Category findByName(String name,Long idUser);
}
