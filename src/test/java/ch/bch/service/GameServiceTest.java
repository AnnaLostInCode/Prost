package ch.bch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GameServiceTest {

    @MockBean
    TaskRepository taskRepoMock;
    @MockBean
    PlayerRepository playerRepoMock;
    @MockBean
    GameRepository gameRepoMock;

    @Autowired
    GameService gameService;


    // ----------------------------------------------------------------------
    // nextTask
    // ----------------------------------------------------------------------

    @Test
    void nextTask() {
        // PREPARE
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));
        Task task2 = new Task("taskId2", 0L, 1L, "challange2", List.of("tagid1", "tagid3"));
        Game game = new Game("gameId1", List.of("tagid1", "tagid2", "tagid3"), List.of("playerId1", "playerId2"), 0L);

        when(playerRepoMock.findAll()).thenReturn(List.of(player2, player1));
        when(taskRepoMock.findAllByLevel(0L)).thenReturn(List.of(task2, task1));
        when(gameRepoMock.findById("gameId1")).thenReturn(Optional.of(game));

        // EXECUTE
        ActiveTask activeTask = gameService.nextTask("gameId1");

        // ASSERT
        assertThat(List.of(player1, player2)).containsAll(activeTask.getInvolvedPlayers());
        assertThat(List.of(task1, task2)).contains(activeTask.getTask());
        assertThat(activeTask.getInvolvedPlayers().size()).isEqualTo(activeTask.getTask().getAmountPlayers().intValue());
    }

    @Test
    void nextTask_noGame() {
        // PREPARE
        when(gameRepoMock.findById("gameId1")).thenReturn(Optional.empty());

        // EXECUTE & ASSERT
        assertThatThrownBy(() -> gameService.nextTask("gameId1"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("There is no Game with this id. Make sure you created a game with this id.");
    }

    // ----------------------------------------------------------------------
    // chooseTask
    // ----------------------------------------------------------------------

    @Test
    void chooseTask() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));

        // EXECUTE
        Task task = gameService.chooseTask(List.of(task1));

        // ASSERT
        assertThat(task).isEqualTo(task1);
    }

    @Test
    void chooseTask_notEnough() {
        // EXECUTE & VERIFY
        assertThatThrownBy(() -> gameService.chooseTask(List.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("There are not enough valid tasks to choose from. Create at least one.");
    }

    // ----------------------------------------------------------------------
    // choosePlayers
    // ----------------------------------------------------------------------

    @Test
    void choosePlayers() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1"));
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));

        // EXECUTE
        ArrayList<Player> players = gameService.choosePlayers(task1, new ArrayList<>(List.of(player1)));

        // ASSERT
        assertThat(players.size()).isEqualTo(task1.getAmountPlayers().intValue());
        assertThat(players).contains(player1);
    }

    @Test
    void choosePlayers_several() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 2L, "challange1", List.of("tagid1"));
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        List<Player> playerList = List.of(player1, player2);

        // EXECUTE
        ArrayList<Player> players = gameService.choosePlayers(task1, new ArrayList<>(playerList));

        // ASSERT
        assertThat(players.size()).isEqualTo(task1.getAmountPlayers().intValue());
        assertThat(players).containsAll(playerList);
    }

    @Test
    void choosePlayers_notEnough() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 2L, "challange1", List.of("tagid1"));
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));

        // EXECUTE & ASSERT
        assertThatThrownBy(() -> gameService.choosePlayers(task1, new ArrayList<>(List.of(player1))))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("There are not enough valid players to choose from. Add at least 2");
    }

    // ----------------------------------------------------------------------
    // getValidTasks
    // ----------------------------------------------------------------------

    @Test
    void getValidTasks() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));
        Task task2 = new Task("taskId2", 0L, 1L, "challange2", List.of("tagid1", "tagid3"));
        when(taskRepoMock.findAllByLevel(0L)).thenReturn(List.of(task2, task1));

        // EXECUTE
        List<Task> validTasks = gameService.getValidTasks(0L, List.of("tagid1", "tagid2"));

        // ASSERT
        assertThat(validTasks.size()).isOne();
        Task validTask = validTasks.getFirst();
        assertThat(validTask).isEqualTo(task1);
    }

    @Test
    void getValidTasks_several() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));
        Task task2 = new Task("taskId2", 0L, 1L, "challange2", List.of("tagid1", "tagid3"));
        when(taskRepoMock.findAllByLevel(0L)).thenReturn(List.of(task2, task1));

        // EXECUTE
        List<Task> validTasks = gameService.getValidTasks(0L, List.of("tagid1", "tagid2", "tagid3"));

        // ASSERT
        assertThat(validTasks.size()).isEqualTo(2);
        assertThat(validTasks).containsAll(List.of(task1, task2));
    }

    @Test
    void getValidTasks_none() {
        // PREPARE
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));
        Task task2 = new Task("taskId2", 0L, 1L, "challange2", List.of("tagid1", "tagid3"));
        when(taskRepoMock.findAllByLevel(0L)).thenReturn(List.of(task2, task1));

        // EXECUTE
        List<Task> validTasks = gameService.getValidTasks(0L, List.of("tagid1"));

        // ASSERT
        assertThat(validTasks.size()).isZero();
    }

    // ----------------------------------------------------------------------
    // getValidPlayers
    // ----------------------------------------------------------------------

    @Test
    void getValidPlayers() {
        // PREPARE
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        when(playerRepoMock.findAll()).thenReturn(List.of(player2, player1));

        // EXECUTE
        List<Player> validPlayers = gameService.getValidPlayers(List.of("playerId1", "playerId2"), List.of("tagid1", "tagid2"));

        // ASSERT
        assertThat(validPlayers.size()).isOne();
        Player player = validPlayers.getFirst();
        assertThat(player).isEqualTo(player1);
    }

    @Test
    void getValidPlayers_several() {
        // PREPARE
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        when(playerRepoMock.findAll()).thenReturn(List.of(player2, player1));

        // EXECUTE
        List<Player> validPlayers = gameService.getValidPlayers(List.of("playerId1", "playerId2"), List.of("tagid1"));

        // ASSERT
        assertThat(validPlayers.size()).isEqualTo(2);
        assertThat(validPlayers).containsAll(List.of(player1, player2));
    }

    @Test
    void getValidPlayers_none() {
        // PREPARE
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        when(playerRepoMock.findAll()).thenReturn(List.of(player2, player1));

        // EXECUTE
        List<Player> validPlayers = gameService.getValidPlayers(List.of("playerId1", "playerId2"), List.of("tagid1", "tagid2", "tagid3"));

        // ASSERT
        assertThat(validPlayers.size()).isZero();
    }

    @Test
    void getValidPlayers_inactive() {
        // PREPARE
        Player player1 = new Player("playerId1", "Anna", "Purple", "They/Them", List.of("tagid1", "tagid2"));
        Player player2 = new Player("playerId2", "Mättu", "Green", "He/Him", List.of("tagid1", "tagid3"));
        when(playerRepoMock.findAll()).thenReturn(List.of(player2, player1));

        // EXECUTE
        List<Player> validPlayers = gameService.getValidPlayers(List.of("playerId1"), List.of("tagid1", "tagid3"));

        // ASSERT
        assertThat(validPlayers.size()).isZero();
    }
}