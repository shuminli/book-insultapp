package org.openshift;

import java.sql.*;
import java.util.Random;

public class InsultGenerator {
    private static final String databaseURL = "jdbc:postgresql://"
            + System.getenv("POSTGRESQL_SERVICE_HOST") + "/"
            + System.getenv("POSTGRESQL_DATABASE");
    private static final String username = System.getenv("POSTGRESQL_USER");
    private static final String pgpassword = System.getenv("PGPASSWORD");

	public String generateInsult() {
        String vowels = "AEIOU";
        String article = "an";
        String theInsult = "";

        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(databaseURL, username, pgpassword);
            if (connection != null) {
                final String SQL = "select a.string as first, b.string as second, c.string as noun from short_adjective a, long_adjective b, noun c order by random() limit 1";
                final Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(SQL);
                while (resultSet.next()) {
                    if (vowels.indexOf(resultSet.getString("first").charAt(0)) == -1) {
                        article = "a";
                    }
                    theInsult = String.format("Thou art %s %s %s %s!", article
                            , resultSet.getString("first")
                            , resultSet.getString("second")
                            , resultSet.getString("noun"));
                }
            }


/*
            String words[][] = {{"Artless", "Bawdy", "Beslubbering"}, {"Base-court", "Bat-fowling", "Beef-witted"}, {"Apple-john", "Baggage", "Barnacle"}};
            String firstAdjective = words[0][new Random().nextInt(words[0].length)];
            String secondAdjective = words[1][new Random().nextInt(words[1].length)];
            String noun = words[2][new Random().nextInt(words[2].length)];
            if (vowels.indexOf(firstAdjective.charAt(0)) == -1) {
                article = "a";
            }
            return String.format("Thou art %s %s %s %s!", article, firstAdjective, secondAdjective, noun);
*/
        } catch (SQLException e) {
            return "Database connection problem!";
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return theInsult;
    }

}
