package pl.radomyski.skillsjoboffers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api")
public class JobController {

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Integer> skillCount = new HashMap<>();

    @GetMapping("/api")
    public Map<String, Integer> get() throws IOException {

        String jobOffers = Files.readString(Path.of("src/main/resources/jobs.json"));
        JsonNode jsonParent = objectMapper.readTree(jobOffers);
        List<JsonNode> jsonNodes = jsonParent.findValues("requiredSkills");

        for (JsonNode jsonNode : jsonNodes) {
            for (JsonNode node : jsonNode) {
                String skillName = node.asText();
                skillCount.put(skillName, skillCount.getOrDefault(skillName, 0) + 1);
            }
        }

        return skillCount;
    }
}
