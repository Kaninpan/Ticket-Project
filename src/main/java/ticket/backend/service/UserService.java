package ticket.backend.service;

;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;
import ticket.backend.entity.UserEntity;
import ticket.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserEntity userEntity) {
        if (userRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new IllegalArgumentException("ไม่สามารถดำเนินการได้ เนื่องจากมีชื่อผู้ใช้งานอยู่แล้ว");
        }
        if (userRepository.findByEmail(userEntity.getEmail()) != null) {
            throw new IllegalArgumentException("ไม่สามารถดำเนินการได้ เนื่องจากมีอีเมลใช้งานอยู่แล้ว");
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("User");
        userRepository.save(userEntity);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Bangkok"));
        userRepository.updateLastLogin(username, now);
        return new UserDetailsImpl(userEntity);
    }
}
