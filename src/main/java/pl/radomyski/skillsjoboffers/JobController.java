package pl.radomyski.skillsjoboffers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
public class JobController {

    ObjectMapper objectMapper = new ObjectMapper();

    @EventListener(ApplicationReadyEvent.class)
    public String get() throws IOException {

        String jobOffers = Files.readString(Path.of("src/main/resources/jobs.json"));
        JsonNode jsonParent = objectMapper.readTree(jobOffers);

        List<JsonNode> jsonNodes = jsonParent.findValues("requiredSkills");

        System.out.print(jsonNodes.size());

        System.out.print(jsonNodes);


        return null;
    }


}
