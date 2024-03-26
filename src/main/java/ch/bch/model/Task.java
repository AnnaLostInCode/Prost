package ch.bch.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tasks")
@Data
@AllArgsConstructor
public class Task {

    @Id
    Long id;
    Long level;
    Long amountInvolvedPlayers;
    String challange;
    String resolve;

    List<Tag> containsTags;

}
