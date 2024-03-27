package ch.bch.controller.internal;

import ch.bch.controller.apidoc.AcceptedApiResponse;
import ch.bch.controller.apidoc.OkApiResponse;
import ch.bch.model.MongoBase;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@RequiredArgsConstructor
public class BasicRestController<T extends MongoBase> {

    @Autowired
    MongoRepository<T, String> repo;

    @GetMapping(path = "/{id}")
    @OkApiResponse
    public Optional<T> get(@Parameter(description = "Object Id", required = true) @PathVariable String id) {
        return repo.findById(id);
    }

    @GetMapping
    @OkApiResponse
    public List<T> getAll() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @AcceptedApiResponse
    public T create(@Valid @RequestBody T t) {
        return repo.save(t);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @AcceptedApiResponse
    public T update(@Valid @RequestBody T t) {
        String id = t.getId();
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
        return repo.save(t);
    }


    @DeleteMapping(path = "/{id}")
    @OkApiResponse
    public void delete(@Parameter(description = "Object Id", required = true) @PathVariable String id) {
        repo.deleteById(id);
    }


}
