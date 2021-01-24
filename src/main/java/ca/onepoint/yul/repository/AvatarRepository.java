package ca.onepoint.yul.repository;

import ca.onepoint.yul.entity.Avatar;
import ca.onepoint.yul.repository.custom.AvatarRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvatarRepository extends CrudRepository<Avatar, Long>, AvatarRepositoryCustom {

    // @Query("FROM Author WHERE firstName = ?1")
    // @Query(value = "SELECT * FROM author WHERE first_name = :firstName", nativeQuery = true)
    @Query(value = "SELECT * FROM avatar WHERE name = :name", nativeQuery = true)
    Avatar findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO avatar(name, type, image, waiting, main, x, y, xdest, ydest) values(:name, 3, '../assets/images/pieton.png', 1, 0, :x, :y, :xdest, :ydest)", nativeQuery = true)
    void addPedestrian(@Param("name") String name, @Param("x") Integer x, @Param("y") Integer y, @Param("xdest") Integer xdest, @Param("ydest") Integer ydest);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM avatar WHERE name LIKE 'pieton%' OR type = 3", nativeQuery = true)
    void removePedestrians();

}
