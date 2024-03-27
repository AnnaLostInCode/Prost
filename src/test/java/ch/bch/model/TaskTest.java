package ch.bch.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void getRandomTask() {
        Task task1 = new Task("taskId1", 0L, 1L, "challange1", List.of("tagid1", "tagid2"));
        Task task2 = new Task("taskId2", 0L, 1L, "challange2", List.of("tagid1", "tagid2"));

        Task random = Task.random(List.of(task1, task2));

        assertThat(List.of(task1, task2)).contains(random);
    }

}