package com.vn.vsii.reponsitory;

import com.vn.vsii.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReponsitory extends PagingAndSortingRepository<Role,Long> {

    Role findByName(String role);
}
