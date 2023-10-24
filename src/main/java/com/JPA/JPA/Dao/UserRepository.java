package com.JPA.JPA.Dao;

import com.JPA.JPA.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query(value = "SELECT * FROM user a WHERE JSON_EXTRACT(a.address, '$.houseNumber') = ?1",
            nativeQuery = true)
    public List<User> getUserByHouseNumber(int houseNumber);
}
