package me.khaoula.studentmvc;

import me.khaoula.studentmvc.entities.Gender;
import me.khaoula.studentmvc.entities.Student;
import me.khaoula.studentmvc.repositories.StudentRepository;
import me.khaoula.studentmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class StudentMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentMvcApplication.class, args);
    }

    @Bean
        //au démarrage crée moi un PasswordEncoder et tu le place dans context
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            studentRepository.save(new Student(null,"khaoula","ELMAJNI","elmajnikhaoula99@gmail.com",new Date(), Gender.Feminin,true));
            studentRepository.save(new Student(null,"khaoula","ELMAJNI","elmajnikhaoula99@gmail.com",new Date(), Gender.Feminin,true));
            studentRepository.save(new Student(null,"khaoula","ELMAJNI","elmajnikhaoula99@gmail.com",new Date(), Gender.Feminin,true));
            studentRepository.save(new Student(null,"khaoula","ELMAJNI","elmajnikhaoula99@gmail.com",new Date(), Gender.Feminin,true));

            studentRepository.findAll().forEach(p->{
                System.out.println(p.getNom());
            });

        };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args ->{
            securityService.saveNewUser("khaoula","1234","1234");
            securityService.saveNewUser("ahmed","1234","1234");
            securityService.saveNewUser("mohammed","1234","1234");

            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");

            securityService.addRoleToUser("khaoula","USER");
            securityService.addRoleToUser("khaoula","ADMIN");
            securityService.addRoleToUser("mohammed","USER");
            securityService.addRoleToUser("ahmed","USER");


        };
    }
}
