package net.proselyte.jwtappdemo.repository;

import net.proselyte.jwtappdemo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long id);

    List<Category> findByUserUsername(String name);

    Category findByNameAndUserUsername(String name, String username);
}
