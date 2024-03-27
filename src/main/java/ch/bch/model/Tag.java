package ch.bch.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tags")
@EqualsAndHashCode(callSuper = true)
@Getter
public class Tag extends MongoBase {

    final String tag;
    final String description;

    public Tag(String id, String tag, String description) {
        super(id);
        this.tag = tag;
        this.description = description;
    }
}
