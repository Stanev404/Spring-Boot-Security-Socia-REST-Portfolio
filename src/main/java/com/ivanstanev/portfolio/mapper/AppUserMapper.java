package com.ivanstanev.portfolio.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ivanstanev.portfolio.entity.auth.AppUser;
import org.springframework.jdbc.core.RowMapper;

//The  AppUserMapper class is used for mapping the columns in the  APP_USER table
// with the fields in the  AppUser class (Based on the query statement).
// returns AppUser as object from DB
public class AppUserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL //
            = "Select u.User_Id, u.Name, u.Email, u.Encryted_Password, u.Enabled From App_User u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("User_Id");
        String name = rs.getString("Name");
        String email = rs.getString("Email");
        String encrytedPassword = rs.getString("Encryted_Password");
        int enabled = rs.getInt("Enabled");

        System.out.println("In appusermapper");



        return new AppUser(userId, name, email, encrytedPassword, enabled);
    }

}