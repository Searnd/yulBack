package ca.onepoint.yul.util;

import ca.onepoint.yul.entity.Avatar;
import ca.onepoint.yul.entity.Coord;
import ca.onepoint.yul.entity.Map;
import ca.onepoint.yul.repository.AvatarRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class PlacementUtil {

    /**
     * Generate random coordinates inside the map
     * */
    public static Coord getRandomCoord() {
        int x = (int) Math.round(Math.random()*29);
        int y = (int) Math.round(Math.random()*29);
        return new Coord(x, y);
    }

    /**
    * Add the specified number of elements
    * @param repo the avatar repository
    * @param numberToAdd the number of pietons to add
    * @param type the type of the element
    * */
    public static void addElement(AvatarRepository repo, Integer numberToAdd, Integer type) {
        for (int i = 0; i < numberToAdd; i++) {
            String name = type.toString() + i;
            Coord startCoords = PlacementUtil.getRandomCoord();
            Coord destCoords = PlacementUtil.getRandomCoord();
            if (3 == type) repo.addPedestrian(name, startCoords.x, startCoords.y, destCoords.x, destCoords.y);
            else repo.addCar(name, startCoords.x, startCoords.y, destCoords.x, destCoords.y);
        }
    }
}
