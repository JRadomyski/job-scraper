package pl.radomyski.skillsjoboffers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JobControllerTest {

    private JobController jobController;

    @BeforeEach
    public void setUp() {
        jobController = new JobController();
    }

    @Test
    public void getTest() {
        Map<String, Integer> result = jobController.get();
        assertTrue(result.containsKey("Java"));
        assertTrue(result.get("Java") >= 10);

    }
}
