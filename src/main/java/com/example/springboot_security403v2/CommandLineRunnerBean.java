package com.example.springboot_security403v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    public void run(String... args) {
        boolean commandrunner = false;
        if (commandrunner) {
            User user = new User("user", "user@domain.com", "user", "user", "user", true);
            Role userRole = new Role("user", "ROLE_USER");

            userRepository.save(user);
            roleRepository.save(userRole);

            User admin = new User("admin", "admin@domain.com", "admin", "admin", "admin", true);
            Role adminRole = new Role("admin", "ROLE_ADMIN");


            userRepository.save(admin);
            roleRepository.save(adminRole);

            //setting 3 pre-loaded teams
            Team team1 = new Team();
            Team team2 = new Team();
            Team team3 = new Team();

            team1.setName("Cicadas");
            team1.setCity("Gainesville");

            team2.setName("Blue Crabs");
            team2.setCity("Miami");

            team3.setName("Woodpeckers");
            team3.setCity("Tampa");

            // creating 2 players for each team
            Player player1 = new Player();
            Player player2 = new Player();
            Player player3 = new Player();
            Player player4 = new Player();
            Player player5 = new Player();
            Player player6 = new Player();

            player1.setfName("Daniel");
            player1.setlName("LeRuso");
            player1.setAge(11);
            player1.setTeam(team1);
            player1.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881407/african-american-teenage-boy-holding-600w-1065585587_oedm5n.webp");

            player2.setfName("Johnny");
            player2.setlName("Lawrence");
            player2.setAge(11);
            player2.setTeam(team1);
            player2.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881715/boy-throwing-basketball-isolated-on-600w-671586961_tkbsiu.webp");

            player3.setlName("Halpert");
            player3.setfName("Jim");
            player3.setAge(10);
            player3.setTeam(team2);
            player3.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881732/cute-young-boy-plays-basketball-600w-1898102833_imsabc.webp");

            player4.setfName("Pam");
            player4.setlName("Beesly");
            player4.setAge(8);
            player4.setTeam(team2);
            player4.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881750/full-length-portrait-kid-playing-600w-667803286_ey9xcg.webp");

            player5.setfName("Dwight");
            player5.setlName("Schrute");
            player5.setAge(7);
            player5.setTeam(team3);
            player5.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881778/happy-asian-boy-playing-basketball-600w-70067704_rkjjex.webp");

            player6.setfName("Andy");
            player6.setlName("Dwyer");
            player6.setTeam(team3);
            player6.setAge(12);
            player6.setPic("https://res.cloudinary.com/dnjzvt63c/image/upload/v1628881803/young-cute-boy-plays-basketball-600w-1898892895_gnecc8.webp");

            //adding two players to our first team
            Set<Player> playersForTeam1 = new HashSet<>();
            playersForTeam1.add(player1);
            playersForTeam1.add(player2);
            team1.setPlayers(playersForTeam1);
            teamRepository.save(team1);

            //same for 2nd team
            Set<Player> playersForTeam2 = new HashSet<>();
            playersForTeam2.add(player3);
            playersForTeam2.add(player4);
            team2.setPlayers(playersForTeam2);
            teamRepository.save(team2);

            //last team
            Set<Player> playersForTeam3 = new HashSet<>();
            playersForTeam3.add(player5);
            playersForTeam3.add(player6);
            team3.setPlayers(playersForTeam3);
            teamRepository.save(team3);

        }






    }
}
