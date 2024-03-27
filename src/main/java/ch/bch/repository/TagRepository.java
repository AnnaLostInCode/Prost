package ch.bch.repository;

import ch.bch.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String> {
    long count();
}
