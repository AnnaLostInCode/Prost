package ch.bch.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("players")
@Data
@AllArgsConstructor
public class Player {

    @Id
    Long id;
    String name;
    String color;
    String pronoun;

    List<Tag> consentedTags;

}
