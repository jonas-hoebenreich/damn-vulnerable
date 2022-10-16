package com.example.issues;

import com.example.issues.model.AnonUser;
import com.example.issues.model.User;
import com.example.issues.model.UserRole;
import com.example.issues.repository.AnonUserRepository;
import com.example.issues.repository.UserRepository;
import com.example.issues.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;


@EnableJpaRepositories
@EnableAspectJAutoProxy
@EntityScan("com.example.issues.*")
@SpringBootApplication
public class IssuesApplication {

	@Autowired
	private ConfigurableEnvironment env;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AnonUserRepository anonUserRepository;
	public static void main(String[] args) {

		SpringApplication.run(IssuesApplication.class, args);

	}
	@PostConstruct
	public void initRng() throws NoSuchAlgorithmException, IOException {
		String seed = String.valueOf(System.nanoTime());
		writeProp("random.seed", seed);
	}
	@PostConstruct
	public void initUsers() {
		BCryptPasswordEncoder encoder = encoder();
		if (anonUserRepository.findByUsername("Anonymous") == null) {
			AnonUser delUser = new AnonUser();
			delUser.setEmail("Anonymous");
			delUser.setUsername("Anonymous");
			delUser.setPassword(encoder.encode( UUID.randomUUID().toString()));
			delUser.setName("Anonymous");
			delUser.setUserRole(UserRole.USER);
			anonUserRepository.save(delUser);
		}
		// if at this point there is only one account, there cant be an admin account
		if (userService.findAll().size() == 1){
			User newAdmin = new User();
			newAdmin.setEmail("admin@admin.com");
			newAdmin.setUsername("admin");
			newAdmin.setPassword(encoder.encode("admin"));
			newAdmin.setName("admin");
			newAdmin.setUserRole(UserRole.ADMIN);
			userRepository.save(newAdmin);
		}
	}


	@PostConstruct
	public void initAttachments(){
		try {
			Files.createDirectories(Paths.get("attachment/"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}



	private static boolean isInstalled(){
		File confFile = new File("resources/application.properties");
		if (confFile.exists()) return true;
		return false;
	}

	public void writeProp(String key, String value){
		MutablePropertySources propertySources = env.getPropertySources();
		Map<String, Object> map = new HashMap<>();
		map.put(key,
				value);
		propertySources
				.addFirst(new MapPropertySource("key", map));
	}
}
