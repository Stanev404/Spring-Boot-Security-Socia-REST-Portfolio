package com.ivanstanev.portfolio.dao;

import com.ivanstanev.portfolio.entity.VerificationToken;
import com.ivanstanev.portfolio.mapper.VerificationTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class VerificationTokenDAO extends JdbcDaoSupport {

    private VerificationTokenMapper verificationTokenMapper = new VerificationTokenMapper();

    @Autowired
    public VerificationTokenDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public VerificationToken validateToken(String verificationToken){
        String sql = VerificationTokenMapper.BASE_SQL  + "where u.Token = ? ";

        Object[] params = new Object[]{verificationToken};
        try {
            VerificationToken currentToken = this.getJdbcTemplate().queryForObject(sql, params, this.verificationTokenMapper);
            return currentToken;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteToken(Long verificationTokenId){
        String sql = "DELETE FROM verification_token WHERE token_id=" + verificationTokenId + ";";
        this.getJdbcTemplate().update(sql);
    }

    public Long getTokenId(String verificationToken) {
        String sql = "SELECT token_id FROM verification_token WHERE token=" + verificationToken + ";";
        return this.getJdbcTemplate().queryForObject(sql,Long.class);
    }
}
