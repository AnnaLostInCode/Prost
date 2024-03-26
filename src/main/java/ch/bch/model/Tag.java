package ch.bch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tags")
@Data
@AllArgsConstructor
public class Tag {

    @Id
    Long id;
    String tag;
    String description;

}
