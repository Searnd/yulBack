package ca.onepoint.yul.repository;

import ca.onepoint.yul.entity.Avatar;
import ca.onepoint.yul.entity.Map;
import ca.onepoint.yul.repository.custom.AvatarRepositoryCustom;
import ca.onepoint.yul.repository.custom.MapRepositoryCustom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends CrudRepository<Map, Long>, MapRepositoryCustom {

    // @Query("FROM Author WHERE firstName = ?1")
    // @Query(value = "SELECT * FROM author WHERE first_name = :firstName", nativeQuery = true)
    Avatar findByName(String name);

    @Query(value = "SELECT * FROM map WHERE type = :type", nativeQuery = true)
    List<Map> findByType(int type);

}
