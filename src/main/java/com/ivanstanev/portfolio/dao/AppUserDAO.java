package com.ivanstanev.portfolio.dao;

import javax.sql.DataSource;

import com.ivanstanev.portfolio.entity.auth.AppUser;
import com.ivanstanev.portfolio.mapper.AppUserMapper;
import com.ivanstanev.portfolio.service.gmail.GmailService;
import com.ivanstanev.portfolio.service.job.VerificationTokenJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

//The  AppUserDAO class is used to manipulate with the APP_USER table.
// It has a method for finding an user in the database corresponding to an username.
@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport {

    private AppUserMapper mapper = new AppUserMapper();

    @Bean
    public PasswordEncoder passwordUserEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    VerificationTokenJobFactory verificationTokenJobFactory;


    @Autowired
    public AppUserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccountByEmail(String email) {
        // Select .. from App_User u Where u.Email = ?
        String sql = AppUserMapper.BASE_SQL + " where u.Email = ? ";
        Object[] params = new Object[]{email};
//        String testSql = AppUserMapper.BASE_SQL + " where u.Email = union select table_schema from information_schema.tables union ";
//        this.getJdbcTemplate().queryForObject(testSql,Long.class);

        try {
            AppUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, this.mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void createUserAccount(AppUser appUser) {
        int indexOfNewUser = saveUserToAppUserTable(appUser);
        System.out.println(indexOfNewUser);
        sendEmail(appUser, indexOfNewUser);

        setRoleForNewUser(indexOfNewUser);
    }

    public void setEnabledToTrue(Long userId){
        String sql = "UPDATE app_user SET enabled = 1 WHERE user_id = ?";
        this.getJdbcTemplate().update(sql,userId);
    }

    private void sendEmail(AppUser appUser, int indexOfNewUser) {
        String verificationToken = generateAndSaveVerificationToken(indexOfNewUser);
        // delete verification token job
        this.verificationTokenJobFactory.createAndScheduleVerificationTokenDeleteJob(verificationToken);

        String messageSubject = "Email verification request from Ivan Stanev";
        String confirmationUrl = "https://ivan-stanev.herokuapp.com" + "/registrationConfirm?token=" + verificationToken;
        String message = "Hey " + appUser.getName() + ",\n" + "\n" +
                "Your credentials to ivan-stanev.herokuapp.com are: " + "\n" + "\n" +
                "Email: " + appUser.getEmail() + "\n" + "Password: " + appUser.getEncrytedPassword() + "\n" + "\n" +
                "To verify your email and complete your registration click the link:" + "\n" + confirmationUrl;
        // send activation link to user email

        GmailService gmailService = new GmailService(new JavaMailSenderImpl());
        gmailService.sendEmail(appUser.getEmail(),messageSubject,message);
    }

    // generate token and save token to DB with new user ID
    private String generateAndSaveVerificationToken(int indexOfNewUser) {
        String verificationToken = UUID.randomUUID().toString();
        int indexOfNewVerificationToken = this.getJdbcTemplate().queryForObject("SELECT MAX(TOKEN_ID) FROM verification_token", Integer.class) + 1;
        System.out.println(indexOfNewVerificationToken);
        this.getJdbcTemplate().update("INSERT INTO VERIFICATION_TOKEN VALUES (?, ?, ?)",
                indexOfNewVerificationToken,
                verificationToken,
                indexOfNewUser);
        System.out.println(indexOfNewVerificationToken + "22");

        return verificationToken;
    }

    private void setRoleForNewUser(int indexOfNewUser) {
        //    SET THE ROLE FOR THE NEW USER
        // index of new user_role row
        int indexOfNewUserRole = this.getJdbcTemplate().queryForObject("SELECT MAX(ID) FROM user_role", Integer.class) + 1;
        //query for adding new user_role row
        this.getJdbcTemplate().update("INSERT INTO USER_ROLE VALUES (?, ?, ?)",
                indexOfNewUserRole,
                indexOfNewUser,
                2);
    }

    private int saveUserToAppUserTable(AppUser appUser) {
        // TO DO CHECK IF AUTOINCREMENT WORKS
        int indexOfNewUser = this.getJdbcTemplate().queryForObject("SELECT MAX(USER_ID) FROM app_user", Integer.class) + 1;
        // query for adding new user to DB
        this.getJdbcTemplate().update("INSERT INTO APP_USER VALUES(?, ?, ?, ?, ?)",
                indexOfNewUser,
                appUser.getName(),
                appUser.getEmail(),
                this.encoder.encode(appUser.getEncrytedPassword()),
                appUser.getEnabled());
        return indexOfNewUser;
    }

    public void saveFacebookAccount(Map<String, String> facebookMap) {
        String facebookEmail = facebookMap.get("email");
        String facebookName = facebookMap.get("name");
        System.out.println(facebookEmail + " " + facebookName);
        System.out.println(facebookMap);
        AppUser facebookUser = new AppUser();
        facebookUser.setName(facebookName);
        facebookUser.setEmail(facebookEmail);
        facebookUser.setEncrytedPassword("FACEBOOK HAS THIS");
        facebookUser.setEnabled(1);
        int indexOfNewUser = this.saveUserToAppUserTable(facebookUser);
        this.setRoleForNewUser(indexOfNewUser);
    }
}
