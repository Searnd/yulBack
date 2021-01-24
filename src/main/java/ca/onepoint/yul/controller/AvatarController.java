package ca.onepoint.yul.controller;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Coord;
import ca.onepoint.yul.entity.LightCoord;
import ca.onepoint.yul.event.TrafficLightEvent;
import ca.onepoint.yul.service.IAvatarService;
import ca.onepoint.yul.service.IMapService;
import ca.onepoint.yul.util.MoveUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Resource
    private IAvatarService iAvatarService;
    @Resource
    private IMapService iMapService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    @Operation(summary = "Get an avatar by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the avatar",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvatarDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Avatar not found",
                    content = @Content)})
    @CrossOrigin
    @GetMapping("/{id}")
    public AvatarDto findById(@PathVariable Integer id) {
        return iAvatarService.getAvatarById(id);
    }

    @CrossOrigin
    @GetMapping("/")
    public List<AvatarDto> findAllAvatars() {
        MoveUtil.resetCoronaDestinations();
        return iAvatarService.getAllAvatars();
    }

    @CrossOrigin
    @PutMapping("/reset-avatars")
    public ResponseEntity resetLights(@RequestBody ArrayList<LightCoord> coords) {
        if (coords.size() > 0) {
            iAvatarService.resetAvatars(coords);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @CrossOrigin
    @GetMapping("/{type}")
    public List<AvatarDto> findAvatarsByType(@PathVariable Integer type) {
        return iAvatarService.getAvatarsByType(type);
    }

    @CrossOrigin
    @PostMapping("/move-avatars")
    public void moveAvatars(@RequestBody List<AvatarDto> listAvatar) {
        if (MoveUtil.avatarList == null && listAvatar.size() > 0) {
            MoveUtil.avatarList = listAvatar;
        }
        MoveUtil.avatarList.forEach(avatarDto -> {
            try {
                MoveUtil.move(avatarDto, iMapService.
                        getAllMap());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (org.springframework.boot.configurationprocessor.json.JSONException e) {
                e.printStackTrace();
            }
        });
        messagingTemplate.convertAndSend("/topic/progress", MoveUtil.avatarList);
    }
}
