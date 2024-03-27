package ch.bch.controller.internal;

import ch.bch.config.SwaggerConfiguration;
import ch.bch.controller.Controllers;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Controllers.INTERNAL_PATH + Controllers.TAGS)
@Tag(name = SwaggerConfiguration.INTERNAL_TAG_TAG)
@Slf4j
@RequiredArgsConstructor
public class TagController extends BasicRestController<ch.bch.model.Tag> {

}
