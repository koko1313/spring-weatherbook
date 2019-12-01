package uni.fmi.masters.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uni.fmi.masters.beans.RoleBean;

@Repository
public interface RoleRepo extends JpaRepository<RoleBean, Integer>{

}
