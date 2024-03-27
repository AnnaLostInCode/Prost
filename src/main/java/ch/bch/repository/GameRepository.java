package ch.bch.repository;

import ch.bch.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
    long count();
}
