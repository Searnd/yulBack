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
    public static Coord getRandomCoord() {
        int x = (int) Math.round(Math.random()*29);
        int y = (int) Math.round(Math.random()*29);
        return new Coord(x, y);
    }

    public static void addPietons(AvatarRepository repo, Integer numberToAdd) {
        for (int i = 0; i < numberToAdd; i++) {
            String pietonName = "pieton" + i;
            Coord startCoords = PlacementUtil.getRandomCoord();
            Coord destCoords = PlacementUtil.getRandomCoord();
            repo.addPedestrian(pietonName, startCoords.x, startCoords.y, destCoords.x, destCoords.y);
        }
    }

    public static void addLights(AvatarRepository repo) {

    }
}
