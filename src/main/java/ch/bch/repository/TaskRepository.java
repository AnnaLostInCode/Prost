package ch.bch.repository;

import ch.bch.model.Task;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TaskRepository extends MongoRepository<Task, String> {

    @Query("{'level':?0}")
    List<Task> findAllByLevel(Long level);

    long count();
}
