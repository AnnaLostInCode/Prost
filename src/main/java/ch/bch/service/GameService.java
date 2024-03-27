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
            Task task = chooseTask(getValidTasks(game.getTagIds()));
            List<Player> validPlayers = getValidPlayers(game.getPlayerIds(), task.getTagIds());
            ArrayList<Player> activePlayers = choosePlayers(task, new ArrayList<>(validPlayers));
            return new ActiveTask(task, activePlayers);
        }).orElseThrow();
    }

    Task chooseTask(List<Task> validTasks) {
        return random(validTasks);
    }

    ArrayList<Player> choosePlayers(Task task, ArrayList<Player> validPlayers) {
        ArrayList<Player> activePlayers = new ArrayList<>();
        for (int i = 0; i < task.getAmountPlayers(); i++) {
            Player randomPlayer = random(validPlayers);
            activePlayers.add(randomPlayer);
            validPlayers.remove(randomPlayer);
        }
        return activePlayers;
    }

    List<Task> getValidTasks(List<String> tagIds) {
        return taskRepo.findAll().stream()
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
