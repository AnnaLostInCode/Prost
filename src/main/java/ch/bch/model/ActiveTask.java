package ch.bch.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ActiveTask {
    Task task;
    List<Player> involvedPlayers;
}
