package pl.radomyski.skillsjoboffers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class JobController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public Map<String, Integer> get() {
        try {
            return countSkillsFromJobOffers();
        } catch (IOException e) {
            throw new RuntimeException("Problem z wczytywaniem ofert pracy.", e);
        }
    }

    private Map<String, Integer> countSkillsFromJobOffers() throws IOException {
        String jobOffers = Files.readString(Path.of("src/main/resources/jobs.json"));
        JsonNode jsonParent = objectMapper.readTree(jobOffers);
        List<JsonNode> jsonNodes = jsonParent.findValues("requiredSkills");

        Map<String, Integer> skillCount = new HashMap<>();
        for (JsonNode jsonNode : jsonNodes) {
            for (JsonNode node : jsonNode) {
                String skillName = node.asText();
                skillCount.put(skillName, skillCount.getOrDefault(skillName, 0) + 1);
            }
        }

        return filterSkillsWithMinimumCount(skillCount, 10);
    }

    private Map<String, Integer> filterSkillsWithMinimumCount(Map<String, Integer> skillCount, int minimumCount) {
        return skillCount.entrySet().stream()
                .filter(entry -> entry.getValue() >= minimumCount)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
}
