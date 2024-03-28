package ch.bch.service;

import static ch.bch.model.Player.random;
import static ch.bch.model.Task.random;

import ch.bch.model.ActiveTask;
import ch.bch.model.Game;
import ch.bch.model.Player;
import ch.bch.model.Task;
import ch.bch.repository.GameRepository;
import ch.bch.repository.PlayerRepository;
import ch.bch.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepo;
    @Autowired
    PlayerRepository playerRepo;
    @Autowired
    TaskRepository taskRepo;

    public ActiveTask nextTask(String gameId) {
        Optional<Game> optionalGame = gameRepo.findById(gameId);
        return optionalGame.map(game -> {
            Task task = chooseTask(getValidTasks(game.getLevel(), game.getTagIds()));
            List<Player> validPlayers = getValidPlayers(game.getPlayerIds(), task.getTagIds());
            ArrayList<Player> activePlayers = choosePlayers(task, new ArrayList<>(validPlayers));
            return new ActiveTask(task, activePlayers);
        }).orElseThrow(() ->
                new IllegalStateException("There is no Game with this id. Make sure you created a game with this id.")
        );
    }

    Task chooseTask(List<Task> validTasks) {
        if (validTasks.isEmpty()) {
            throw new IllegalStateException("There are not enough valid tasks in this level to choose from. Create at least one.");
        }
        return random(validTasks);
    }

    ArrayList<Player> choosePlayers(Task task, ArrayList<Player> validPlayers) {
        Long amountPlayers = task.getAmountPlayers();
        if (validPlayers.size() < amountPlayers) {
            throw new IllegalStateException("There are not enough valid players to choose from. Add at least " + amountPlayers);
        }
        ArrayList<Player> taskPlayers = new ArrayList<>();
        for (int i = 0; i < amountPlayers; i++) {
            Player randomPlayer = random(validPlayers);
            taskPlayers.add(randomPlayer);
            validPlayers.remove(randomPlayer);
        }
        return taskPlayers;
    }

    List<Task> getValidTasks(Long level, List<String> tagIds) {
        return taskRepo.findAllByLevel(level).stream()
                .filter(task -> task.isValid(tagIds))
                .toList();
    }

    List<Player> getValidPlayers(List<String> inGamePlayers, List<String> tagIds) {
        return playerRepo.findAll().stream()
                .filter(player -> player.isActive(inGamePlayers))
                .filter(player -> player.isValid(tagIds))
                .toList();
    }

}
