package edu.tum.ase.darkmode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DarkmodeApplication {

    private boolean isDarkMode = false;
    private long lastToggleTime = 0;
    private static final long COOLDOWN_PERIOD = 3000;

    public static void main(String[] args) {
        SpringApplication.run(DarkmodeApplication.class, args);
    }

    @GetMapping(path = "/dark-mode/toggle")
    public Boolean toggleDarkMode() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime >= COOLDOWN_PERIOD) {
            isDarkMode = !isDarkMode;
            lastToggleTime = currentTime;
        }
        return isDarkMode;
    }

    // endpoint which returns dark mode status
    @GetMapping(path = "/dark-mode")
    public Boolean darkMode() {
        return isDarkMode;
    }

}
