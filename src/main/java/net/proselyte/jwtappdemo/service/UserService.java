package net.proselyte.jwtappdemo.service;

import net.proselyte.jwtappdemo.model.User;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


public interface UserService {

    User register(User user);

    List<User> getAll();


    User findByUsername(String username);

    User findById(Long id);

    User findByEmail(String email);

    void delete(Long id);

    Boolean checkingAccessRights(Long id, Map<String,String> headers) throws UnsupportedEncodingException, JSONException;
}
