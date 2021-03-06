package repository.account;

import model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class AccountRepositoryMySQL implements AccountRepository{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountsSql =
                    "Select * from " + ACCOUNT;
            ResultSet accountResultSet = statement.executeQuery(fetchAccountsSql);
            List<Account> accounts = new ArrayList<>();
            while(accountResultSet.next()) {
                Long id = accountResultSet.getLong("id");
                Long client_id = accountResultSet.getLong("client_id");
                String number = accountResultSet.getString("number");
                String type = accountResultSet.getString("type");
                Integer money = accountResultSet.getInt("money");
                Date creation = accountResultSet.getDate("creation");

                Account account = Account.builder()
                        .id(id)
                        .client_id(client_id)
                        .number(number)
                        .type(type)
                        .money(money)
                        .date(creation)
                        .build();
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);
            if(!accountResultSet.next()) {
                return null;
            }

            return Account.builder()
                    .id(accountResultSet.getLong("id"))
                    .client_id(accountResultSet.getLong("client_id"))
                    .number(accountResultSet.getString("number"))
                    .type(accountResultSet.getString("type"))
                    .money(accountResultSet.getInt("money"))
                    .date(accountResultSet.getDate("creation"))
                    .build();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Account findByNumber(String number) {
        try {
            Statement statement = connection.createStatement();

            String fetchAccountSql =
                    "Select * from `" + ACCOUNT + "` where `number`=\'" + number + "\'";
            ResultSet accountResultSet = statement.executeQuery(fetchAccountSql);

            if(accountResultSet.next()) {
                return Account.builder()
                        .id(accountResultSet.getLong("id"))
                        .client_id(accountResultSet.getLong("client_id"))
                        .number(accountResultSet.getString("number"))
                        .type(accountResultSet.getString("type"))
                        .money(accountResultSet.getInt("money"))
                        .date(accountResultSet.getDate("creation"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + ACCOUNT +  " where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error in remove all account repository");
            System.out.println(e);
        }
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO " + ACCOUNT + " values (null, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertAccountStatement.setLong(1, account.getClient_id());
            insertAccountStatement.setString(2, account.getNumber());
            insertAccountStatement.setString(3, account.getType());
            insertAccountStatement.setInt(4, account.getMoney());
            insertAccountStatement.setDate(5, account.getDate());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            if(rs.next()) {
                long accountId = rs.getLong(1);
                account.setId(accountId);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `" + ACCOUNT + "` where `id`=\'" + id + "\'";
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Account newAccount) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE " + ACCOUNT + " set number = ?, type = ?, money = ?, creation = ?" +
                            " WHERE `id`=\'" + id + "\';", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1, newAccount.getNumber());
            updateAccountStatement.setString(2, newAccount.getType());
            updateAccountStatement.setInt(3, newAccount.getMoney());
            updateAccountStatement.setDate(4, (Date) newAccount.getDate());
            updateAccountStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean transferMoney(Long id, int money) {
        try {
            Account account = findById(id);
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE " + ACCOUNT + " set money = ?" +
                            " WHERE `id`=\'" + id + "\';", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setInt(1, account.getMoney() + money);
            updateAccountStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
