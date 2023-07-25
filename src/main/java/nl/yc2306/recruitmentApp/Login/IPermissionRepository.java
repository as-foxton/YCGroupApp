package nl.yc2306.recruitmentApp.Login;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IPermissionRepository extends CrudRepository<Permission,Long> {
    List<Permission> findAllByRol(String rol);
    List<Permission> findAllByPage(String page);
}
