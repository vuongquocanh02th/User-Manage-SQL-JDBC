package org.example.usermanager.model;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    public void insertUser(User user) throws SQLException;

    public User selectUser(int id);

    public List<User> selectAllUsers();

    public boolean deleteUser(int id) throws SQLException;

    public boolean updateUser(User user) throws SQLException;

    public List<User> findByCountry(String country) throws SQLException;

    public List<User> sortByName() throws SQLException;

    User getUserById(int id);

    void insertUserStore(User user) throws SQLException;

    void addUserTransaction(User user, List<Integer> permission);

    public void insertUpdateWithoutTransaction();

    public void insertUpdateUseTransaction();

    List<User> getAllUserProc();

    boolean updateUserProc(User user);

    boolean deleteUserProc(int id);

}
