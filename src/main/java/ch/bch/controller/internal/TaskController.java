package ch.bch.controller.internal;

import ch.bch.config.SwaggerConfiguration;
import ch.bch.controller.Controllers;
import ch.bch.model.Task;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Controllers.INTERNAL_PATH + Controllers.TASKS)
@Tag(name = SwaggerConfiguration.INTERNAL_TAG_TASK)
@Slf4j
@RequiredArgsConstructor
public class TaskController extends BasicRestController<Task> {

}
