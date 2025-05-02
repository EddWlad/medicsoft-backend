package com.uisrael.medical_service.repositories;

import com.uisrael.medical_service.entities.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface IMenuRepository extends IGenericRepository<Menu, Long> {

    @Query(value = """           
            select m.* from menu_role mr
            inner join user_role ur on ur.id_role = mr.id_role
            inner join menu m on m.id_menu = mr.id_menu
            inner join "user" u  on u.id = ur.id_user
            where u.username = :username           
            """, nativeQuery = true)
    List<Menu> getMenusByUsername(@PathVariable("username") String username);
}