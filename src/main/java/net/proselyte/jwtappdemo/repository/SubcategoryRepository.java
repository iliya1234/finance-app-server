package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByUserId(Long id);

    List<Subcategory> findByName(String name);

    List<Subcategory> findByUserUsername(String username);

    Subcategory findByNameAndUserUsername(String name, String username);
}
