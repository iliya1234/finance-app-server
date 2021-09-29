package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.model.User;
import java.util.List;

public interface SubcategoryService {
    List<Subcategory> getAll();

    Subcategory findById(Long id);

    List<Subcategory> findByUserId(Long id);

    Subcategory create(Subcategory subcategory, Long idUser);

    Subcategory update(Subcategory subcategory, Long id, User user);

    void delete(Long id);

    Subcategory findByName(String name,Long idUser);
}
