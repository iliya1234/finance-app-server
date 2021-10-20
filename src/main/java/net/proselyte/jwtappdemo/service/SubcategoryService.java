package net.proselyte.jwtappdemo.service;


import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.model.User;

import java.util.List;

public interface SubcategoryService {
    List<Subcategory> getAll();

    Subcategory findById(Long id);

    List<Subcategory> findByUserId(Long id);

    Subcategory create(Subcategory subcategory, String username);

    Subcategory update(Subcategory subcategory, Long idSubcategory, User user);

    void delete(Long id);

    List<Subcategory> findByUserUsername(String username);

    Subcategory findByNameAndUsername(String name, String username);
}
