package edu.dsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialNetworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApplication.class, args);
        System.out.println("🚀 Social Network Graph Analysis UI is running!");
        System.out.println("📱 Open your browser and navigate to: http://localhost:8080");
        System.out.println("🎨 Enjoy exploring the interactive social network visualization!");
    }
}
