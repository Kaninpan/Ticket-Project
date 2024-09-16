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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(userEntity);
    }
}
