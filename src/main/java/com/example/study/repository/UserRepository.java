package com.example.study.repository;

import com.example.study.domain.User;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

public class UserRepository {
    private DataSource dataSource;

    public void setDataSource(SimpleDriverDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findUser(int id) {
        try {
            Connection connection = dataSource.getConnection();

            connection.prepareStatement("SELECT * FROM user WHERE id = ?", id);

            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void saveUser(User user) {
        try {

        } catch (Exception e) {

        }
    }

    public void modifyUser(User user) {

    }

    public void remove(int id) {

    }
}
