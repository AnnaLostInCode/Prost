package ch.bch.model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tasks")
@EqualsAndHashCode(callSuper = true)
@Getter
public class Task extends MongoBase {

    final Long level;
    final Long amountPlayers;
    final String challenge;

    final List<String> tagIds;

    public Task(String id, Long level, Long amountPlayers, String challenge, List<String> tagIds) {
        super(id);
        this.level = level;
        this.amountPlayers = amountPlayers;
        this.challenge = challenge;
        this.tagIds = tagIds;
    }

    public boolean isValid(List<String> allowedTags) {
        return new HashSet<>(allowedTags).containsAll(tagIds);
    }

    public static Task random(List<Task> tasks) {
        int randomIndex = new Random().nextInt(tasks.size());
        return tasks.get(randomIndex);
    }

}
