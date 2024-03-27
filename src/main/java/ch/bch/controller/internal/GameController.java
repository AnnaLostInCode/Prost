package ch.bch.controller.internal;

import ch.bch.config.SwaggerConfiguration;
import ch.bch.controller.Controllers;
import ch.bch.controller.apidoc.OkApiResponse;
import ch.bch.model.ActiveTask;
import ch.bch.model.Game;
import ch.bch.service.GameService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Controllers.INTERNAL_PATH + Controllers.GAMES)
@Tag(name = SwaggerConfiguration.INTERNAL_TAG_GAME)
@Slf4j
@RequiredArgsConstructor
public class GameController extends BasicRestController<Game> {

    @Autowired
    GameService gameService;

    @GetMapping(path = "nextTask/{gameId}")
    @OkApiResponse
    ActiveTask getNextTask(@Parameter(description = "Game Id", required = true) @PathVariable String gameId) {
        return gameService.nextTask(gameId);
    }

}
