package ca.onepoint.yul.repository;

import ca.onepoint.yul.entity.Avatar;
import ca.onepoint.yul.entity.Map;
import ca.onepoint.yul.repository.custom.AvatarRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AvatarRepository extends CrudRepository<Avatar, Long>, AvatarRepositoryCustom {

    // @Query("FROM Author WHERE firstName = ?1")
    // @Query(value = "SELECT * FROM author WHERE first_name = :firstName", nativeQuery = true)
    @Query(value = "SELECT * FROM avatar WHERE name = :name", nativeQuery = true)
    Avatar findByName(String name);

    @Query(value = "SELECT * FROM avatar WHERE type = :type", nativeQuery = true)
    List<Avatar> findByType(int type);

    @Query(value = "SELECT * FROM map WHERE name IS NOT NULL", nativeQuery = true)
    List<Map> getAllMap();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO avatar(name, type, image, waiting, main, x, y, xdest, ydest) values(:name, 3, '../assets/images/pieton.png', 1, 0, :x, :y, :xdest, :ydest)", nativeQuery = true)
    void addPedestrian(@Param("name") String name, @Param("x") int x, @Param("y") int y, @Param("xdest") int xdest, @Param("ydest") int ydest);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO avatar(name, type, image, waiting, main, x, y, xdest, ydest) values (:name, :type, :image, 0, 0, :x, :y, :x, :y)", nativeQuery = true)
    void addLight(@Param("name") String name, @Param("type") int type, @Param("image") String image, @Param("x") int x, @Param("y") int y);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM avatar WHERE name LIKE 'pieton%' OR type = 3", nativeQuery = true)
    void removePedestrians();

    @Modifying
    @Transactional
    @Query(value ="DELETE FROM avatar WHERE type = 4 OR type = 5", nativeQuery = true)
    void removeLights();

}
