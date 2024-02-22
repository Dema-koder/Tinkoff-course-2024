package edu.java;

import edu.java.client.GitHubClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.configuration.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
    private static ApplicationConfig applicationConfig;
    private static ClientConfiguration clientConfiguration;

    @Autowired
    public ScrapperApplication(ClientConfiguration clientConfiguration, ApplicationConfig applicationConfig) {
        this.clientConfiguration = clientConfiguration;
        this.applicationConfig = applicationConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
        GitHubClient gitHubClient = new GitHubClient(clientConfiguration.githubWebClient(applicationConfig));
        System.out.println(gitHubClient.getRepositoryInfo("Dema-koder", "Tinkoff-course-2024"));
    }
}
