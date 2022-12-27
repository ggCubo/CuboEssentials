package gg.cubo.essentials.database;

import com.jaoow.sql.connector.SQLConnector;
import com.jaoow.sql.connector.type.SQLDatabaseType;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DatabaseType extends SQLDatabaseType {

    private final HikariDataSource dataSource = new HikariDataSource();

    public H2DatabaseType(String driverClassName, String jdbcUrl) {
        super(driverClassName, jdbcUrl);

        dataSource.setJdbcUrl(this.getJdbcUrl());
        dataSource.setDriverClassName(this.getDriverClassName());

        dataSource.setUsername("root");
        dataSource.setPassword("123");
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:h2:~/testing";
    }

    @Override
    public SQLConnector connect() throws SQLException{
        dataSource.getConnection().close();

        return consumer -> {
            try (Connection connection = dataSource.getConnection()) {
                consumer.accept(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public String getDriverClassName() {
        return "org.h2.Driver";
    }
}
