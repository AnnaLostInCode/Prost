package ch.bch.model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("players")
@EqualsAndHashCode(callSuper = true)
@Getter
public class Player extends MongoBase {

    final String name;
    final String color;
    final String pronouns;

    final List<String> tagIds;

    public Player(String id, String name, String color, String pronouns, List<String> tagIds) {
        super(id);
        this.name = name;
        this.color = color;
        this.pronouns = pronouns;
        this.tagIds = tagIds;
    }

    public boolean isActive(List<String> activePlayers) {
        return activePlayers.contains(super.id);
    }

    public boolean isValid(List<String> tags) {
        return new HashSet<>(this.tagIds).containsAll(tags);
    }

    public static Player random(List<Player> players) {
        int randomIndex = new Random().nextInt(players.size());
        return players.get(randomIndex);
    }

}
