package com.example.authentication.repository;

import com.example.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select x1 from Role x1 \n" +
            " where x1.name in :roles")
    List<Role> listRoleByNames(List<String> roles);
}
