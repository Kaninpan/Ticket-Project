package ticket.backend.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ticket.backend.entity.UserEntity;
import ticket.backend.repository.AdduserRepository;

import java.time.LocalDateTime;

@Service
public class AddusersService {

    private final AdduserRepository adduserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AddusersService(AdduserRepository adduserRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.adduserRepository = adduserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(UserEntity userEntity) {
        if (adduserRepository.findByUsername(userEntity.getUsername()) != null) {
            throw new IllegalArgumentException("ไม่สามารถดำเนินการได้ เนื่องจากมีชื่อผู้ใช้งานอยู่แล้ว");
        }
        if (adduserRepository.findByEmail(userEntity.getEmail()) != null) {
            throw new IllegalArgumentException("ไม่สามารถดำเนินการได้ เนื่องจากมีอีเมลใช้งานอยู่แล้ว");
        }

        userEntity.setCreateDate(LocalDateTime.now());
        userEntity.setCreateBy(1);
        userEntity.setUpdateDate(LocalDateTime.now());
        userEntity.setLastLogin(LocalDateTime.now());
        userEntity.setUpdateBy(1);
        userEntity.setStatusId(1);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if (userEntity.getRole() == null || userEntity.getRole().isEmpty()) {
            userEntity.setRole("User");
        }
        adduserRepository.save(userEntity);
    }
}
