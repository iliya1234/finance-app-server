package net.proselyte.jwtappdemo.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.proselyte.jwtappdemo.model.Subcategory;
import net.proselyte.jwtappdemo.model.User;
import net.proselyte.jwtappdemo.repository.SubcategoryRepository;
import net.proselyte.jwtappdemo.repository.UserRepository;
import net.proselyte.jwtappdemo.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, UserRepository userRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Subcategory> getAll() {
        List<Subcategory> result = subcategoryRepository.findAll();
        log.info("IN getAll - {} subcategories found", result.size());
        return result;
    }

    @Override
    public Subcategory findById(Long id) {
        Subcategory result = subcategoryRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN findById - no subcategory found by id: {}", id);
            return null;
        }
        log.info("IN findById - operation: {} found by id: {}", result, id);
        return result;

    }

    @Override
    public List<Subcategory> findByUserId(Long id) {
        List<Subcategory> result = subcategoryRepository.findByUserId(id);
        log.info("IN getByUserId - {} subcategory found with id: {}", result.size(), id);
        return result;
    }

    @Override
    public Subcategory create(Subcategory subcategory, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("IN findById - no user found by username: {}", username);
            return null;
        }
        subcategory.setUser(user);
        subcategoryRepository.save(subcategory);
        log.info("IN create - subcategory: {} successfully created", subcategory);
        return subcategory;
    }

    @Override
    public Subcategory update(Subcategory subcategory, Long idSubcategory, User user) {
        List<Subcategory> subcategoryList = subcategoryRepository.findByUserUsername(user.getUsername());
        long errorCount = subcategoryList
                .stream()
                .filter(x -> x.getName().equals(subcategory.getName()))
                .filter(x -> !Objects.equals(x.getId(), idSubcategory))
                .count();
        if (errorCount > 0) {
            return null;
        }
        subcategory.setId(idSubcategory);
        subcategory.setUser(user);
        subcategoryRepository.save(subcategory);
        log.info("IN update - subcategory: {} successfully updated", subcategory);
        return subcategory;
    }

    @Override
    public void delete(Long id) {
        subcategoryRepository.deleteById(id);
    }

    @Override
    public List<Subcategory> findByUserUsername(String username) {
        List<Subcategory> result = subcategoryRepository.findByUserUsername(username);
        log.info("IN findByUserUsername - {} subcategory found", result.size());
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Override
    public Subcategory findByNameAndUsername(String name, String username) {
        Subcategory result = subcategoryRepository.findByNameAndUserUsername(name, username);
        if (result == null) {
            log.warn("IN findByNameAndUsername - no subcategory found by name: {}", name);
        }
        return result;
    }
}
