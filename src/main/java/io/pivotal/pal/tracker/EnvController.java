package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class EnvController {

    String PORT;
    String MEMORY_LIMIT;
    String CF_INSTANCE_INDEX;
    String CF_INSTANCE_ADDR;

    public EnvController(
            @Value("${PORT:NOT SET}") String PORT,
            @Value("${MEMORY_LIMIT:NOT SET}") String MEMORY_LIMIT,
            @Value("${CF_INSTANCE_INDEX:NOT SET}") String CF_INSTANCE_INDEX,
            @Value("${CF_INSTANCE_ADDR:NOT SET}") String CF_INSTANCE_ADDR
    ) {
        this.PORT = PORT;
        this.MEMORY_LIMIT = MEMORY_LIMIT;
        this.CF_INSTANCE_INDEX = CF_INSTANCE_INDEX;
        this.CF_INSTANCE_ADDR = CF_INSTANCE_ADDR;

    }

    @GetMapping("/env")
    public HashMap<String, String> getEnv() {
        HashMap<String, String> env = new HashMap<>();

        env.put("PORT", PORT);
        env.put("MEMORY_LIMIT", MEMORY_LIMIT);
        env.put("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX);
        env.put("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR);

        return env;
    }
}
