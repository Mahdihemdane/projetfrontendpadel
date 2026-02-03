package com.padel.reservation;

import com.padel.reservation.entity.Role;
import com.padel.reservation.entity.Terrain;
import com.padel.reservation.entity.User;
import com.padel.reservation.repository.TerrainRepository;
import com.padel.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TerrainRepository terrainRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Admin
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@test.com");
            admin.setPassword(encoder.encode("123456"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            // Member
            User member = new User();
            member.setFirstName("Mahdi");
            member.setLastName("Hemdane");
            member.setEmail("user@test.com");
            member.setPassword(encoder.encode("123456"));
            member.setRole(Role.MEMBER);
            userRepository.save(member);

            // Terrains
            Terrain t1 = new Terrain();
            t1.setName("Court Central");
            t1.setDescription("Terrain principal avec gradins.");
            t1.setLocation("Indoors");
            t1.setPrice(40.0);
            t1.setState("Available");
            terrainRepository.save(t1);

            Terrain t2 = new Terrain();
            t2.setName("Court 2");
            t2.setDescription("Terrain extérieur.");
            t2.setLocation("Outdoors");
            t2.setPrice(30.0);
            t2.setState("Available");
            terrainRepository.save(t2);

            System.out.println("Data Seeding Completed: Admin(admin@test.com), User(user@test.com)");
        }
    }
}
