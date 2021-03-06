import database.DBConnectionFactory;
import database.JDBConnectionWrapper;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        //final boolean connection = new JDBConnectionWrapper(PRODUCTION).testConnection();
       // System.out.println(connection);

       // User user = new UserBuilder().setUsername("andrei").setPassword("Masina45!").build();

        //testing connection
        JDBConnectionWrapper connectionWrapper = DBConnectionFactory.getConnectionWrapper(true);
        boolean res = connectionWrapper.testConnection();

        if(res){
            System.out.println("All good");
        }
        else {
            System.out.println("Bad connection");
        }


    }

}
