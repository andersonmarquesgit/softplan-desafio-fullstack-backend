package com.softplan.restapi;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.softplan.restapi.api.entity.User;
import com.softplan.restapi.api.enums.ProfileEnum;
import com.softplan.restapi.api.repository.UserRepository;

@SpringBootApplication
public class RestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// Criando usuário ao iniciar a aplicação
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		admin.setEmail("admin@softplan.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		User userScreening = new User();
		userScreening.setEmail("sc@softplan.com");
		userScreening.setPassword(passwordEncoder.encode("123456"));
		userScreening.setProfile(ProfileEnum.ROLE_USER_SCREENING);
		
		User userFinisher = new User();
		userFinisher.setEmail("fc@softplan.com");
		userFinisher.setPassword(passwordEncoder.encode("123456"));
		userFinisher.setProfile(ProfileEnum.ROLE_USER_FINISHER);

		User find = userRepository.findByEmail("admin@softplan.com");
		User find1 = userRepository.findByEmail("sc@softplan.com");
		User find2 = userRepository.findByEmail("fc@softplan.com");
		if (find == null && find1 == null && find2 == null) {
			userRepository.save(admin);
			userRepository.save(userScreening);
			userRepository.save(userFinisher);
		}
	}
	
}
