package nl.yc2306.recruitmentApp.Login;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IPermissionRepository extends CrudRepository<Permission,Long> {
    List<Permission> findAllByRol(String rol);
    List<Permission> findAllByPage(String page);
}
