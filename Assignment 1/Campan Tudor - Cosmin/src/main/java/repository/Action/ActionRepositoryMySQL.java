package repository.Action;


import java.sql.*;

import static database.Constants.Tables.CLIENT;

public class ActionRepositoryMySQL implements ActionRepository {

    private final Connection connection;

    public ActionRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(String action) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO action values (null, ?)");
            insertStatement.setString(1, action);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String findAllActions() throws SQLException {
        String actions="";
        Statement statement = connection.createStatement();

        String fetchUserSql =
                "Select * from action";
        ResultSet userResultSet = statement.executeQuery(fetchUserSql);
        while (userResultSet.next())
        {
          actions=actions+"\n"+userResultSet.getString("action")  ;
        }
        return actions;
    }
}
