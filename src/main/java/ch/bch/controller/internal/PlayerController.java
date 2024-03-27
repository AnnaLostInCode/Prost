package ch.bch.controller.internal;

import ch.bch.config.SwaggerConfiguration;
import ch.bch.controller.Controllers;
import ch.bch.model.Player;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Controllers.INTERNAL_PATH + Controllers.PLAYERS)
@Tag(name = SwaggerConfiguration.INTERNAL_TAG_PLAYER)
@Slf4j
@RequiredArgsConstructor
public class PlayerController extends BasicRestController<Player> {

}
