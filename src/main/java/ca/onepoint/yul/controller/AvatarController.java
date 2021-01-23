package ca.onepoint.yul.controller;

import ca.onepoint.yul.dto.AvatarDto;
import ca.onepoint.yul.entity.Coord;
import ca.onepoint.yul.service.IAvatarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    @Resource
    private IAvatarService iAvatarService;

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
        return iAvatarService.getAllAvatars();
    }

    @CrossOrigin
    @GetMapping("/{type}")
    public List<AvatarDto> findAvatarsByType(@PathVariable Integer type) {
        return iAvatarService.getAvatarsByType(type);
    }

    public Coord calcDistance(AvatarDto avatar) {
        Integer diffX = avatar.getXdest() - avatar.getX();
        Integer diffY = avatar.getYdest() - avatar.getY();
        return new Coord(diffX, diffY);
    }

    public Integer getStepFromDistance(Integer dist) {
        if (dist < 0) {
            return -1;
        }
        if (dist > 0) {
            return 1;
        }
        return 0;
    }

    public void move(AvatarDto avatarDto) {
        Coord dist = this.calcDistance(avatarDto);
        dist.x = getStepFromDistance(dist.x);
        dist.y = getStepFromDistance(dist.y);
        if (dist.x > 0 || dist.y > 0) {
            if (dist.x == 0) {
                avatarDto.setY(dist.y + avatarDto.getY());
            } else if (dist.y == 0) {
                avatarDto.setX(dist.x + avatarDto.getX());
            } else {
                if (Math.random() > 0.5) {
                    avatarDto.setX(dist.x + avatarDto.getX());
                } else {
                    avatarDto.setY(dist.y + avatarDto.getY());
                }
            }
        }
    }

    @CrossOrigin
    @PostMapping("/move-avatars")
    public void moveAvatars(@RequestBody List<AvatarDto> listAvatar) {
        listAvatar.forEach(this::move);
        messagingTemplate.convertAndSend("/topic/progress", listAvatar);
    }


}
