package com.edem.securecapita.repository.Impl;

import com.edem.securecapita.exception.ApiException;
import com.edem.securecapita.model.Role;
import com.edem.securecapita.model.User;
import com.edem.securecapita.repository.RoleRepository;
import com.edem.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static com.edem.securecapita.enums.RoleTypes.ROLE_USER;
import static com.edem.securecapita.enums.VerificationType.ACCOUNT;
import static com.edem.securecapita.query.UserQuery.*;
import static java.util.Map.of;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {


    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        if (getEmailCount(user.getEmail().trim().toLowerCase())> 0) throw new ApiException("Email already exists with another account");
        try{
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);

            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());

            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());

            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());

            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("user_Id",user.getId(), "url",verificationUrl));

//            emailService.sendVerificationUrl(user.getFirstName(), user.getEmail(), verificationUrl, ACCOUNT.getType());

            user.setEnabled(false);
            user.setNotLocked(true);

            return user;
        }

        catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again");
        }
    }


    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type ){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+type+"/"+key).toUriString();
    }
}
