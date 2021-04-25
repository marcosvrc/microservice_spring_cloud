package br.com.auth;

import br.com.auth.entity.Permission;
import br.com.auth.entity.User;
import br.com.auth.repository.PermissionRepository;
import br.com.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository,
						   BCryptPasswordEncoder passwordEncoder){
		return args -> {
			initUsers(userRepository,permissionRepository,passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository,
						   BCryptPasswordEncoder passwordEncoder) {
		Permission permission = null;
		Permission findPermisson = permissionRepository.findByDescription("admin");
		if(findPermisson == null){
			permission = new Permission();
			permission.setDescription("admin");
			permission = permissionRepository.save(permission);
		} else {
			permission = findPermisson;
		}

		User admin = new User();
		admin.setUsername("marcos");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setEnabled(true);
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setPermissions(Arrays.asList(permission));

		User find = userRepository.findByUsername("marcos");
		if(find == null) {
			userRepository.save(admin);
		}
	}

}
