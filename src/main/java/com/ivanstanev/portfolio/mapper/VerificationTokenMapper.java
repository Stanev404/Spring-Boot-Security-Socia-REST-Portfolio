package com.ivanstanev.portfolio.mapper;

import com.ivanstanev.portfolio.entity.VerificationToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificationTokenMapper implements RowMapper<VerificationToken> {

    public static final String BASE_SQL //
            = "Select u.Token_Id, u.Token, u.User_Id From Verification_Token u ";

    @Override
    public VerificationToken mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long tokenId = rs.getLong("Token_Id");
        String token = rs.getString("Token");
        Long userId= rs.getLong("User_Id");


        return new VerificationToken(tokenId,token,userId);
    }

}