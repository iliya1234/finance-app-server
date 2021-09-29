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

@Service
@Slf4j
public class SubcategoryServiceImpl implements SubcategoryService {
    private SubcategoryRepository subcategoryRepository;
    private UserRepository userRepository;

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
        if (result == null){
            log.warn("IN findById - no subcategory found by id: {}", id);
            return null;
        }
        log.info("IN findById - operation: {} found by id: {}", result);
        return result;

    }

    @Override
    public List<Subcategory> findByUserId(Long id) {
        List<Subcategory> result = subcategoryRepository.findByUserId(id);
        log.info("IN getByUserId - {} subcategory found with id: {}", result.size(),id);
        return result;
    }

    @Override
    public Subcategory create(Subcategory subcategory,Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        if(user == null){
            log.warn("IN findById - no user found by id: {}", idUser);
            return null;
        }
        subcategory.setUser(user);
        subcategoryRepository.save(subcategory);
        log.info("IN create - subcategory: {} successfully created", subcategory);
        return subcategory;
    }

    @Override
    public Subcategory update(Subcategory subcategory, Long id,User user) {
        subcategory.setId(id);
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
    public Subcategory findByName(String name,Long idUser) {
        List<Subcategory> subcategoryList = subcategoryRepository.findByName(name);
        if (subcategoryList == null){
            log.warn("IN findByName - no subcategory found by name: {}", name);
            return null;
        }
        Subcategory result = null;
        for (Subcategory subcategory : subcategoryList){
            if(subcategory.getUser().getId()==idUser){
                result=subcategory;
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
