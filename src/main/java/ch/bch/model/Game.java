package ch.bch.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("games")
@EqualsAndHashCode(callSuper = true)
@Getter
public class Game extends MongoBase {

    final List<String> tagIds;
    final List<String> playerIds;

    final Long level;

    public Game(String id, List<String> tagIds, List<String> playerIds, Long level) {
        super(id);
        this.tagIds = tagIds;
        this.playerIds = playerIds;
        this.level = level;
    }
}
