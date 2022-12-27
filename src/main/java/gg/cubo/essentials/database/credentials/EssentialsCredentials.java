package gg.cubo.essentials.database.credentials;

import com.jaoow.sql.connector.SQLConnector;
import com.jaoow.sql.connector.type.impl.MySQLDatabaseType;
import com.spigonate.Credentials;
import gg.cubo.essentials.database.ConnectionType;
import gg.cubo.essentials.database.H2DatabaseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

@RequiredArgsConstructor
@Data
public class EssentialsCredentials implements Credentials {

    @NotNull
    private final ConnectionType connectionType;
    @NotNull
    private final Plugin plugin;

    @Override
    public SQLConnector createDatabase() throws SQLException {
        if (connectionType == ConnectionType.PRODUCTION) {
            return MySQLDatabaseType.builder()
                    .database(plugin.getConfig().getString("mysql.database"))
                    .address(plugin.getConfig().getString("mysql.address"))
                    .username(plugin.getConfig().getString("mysql.username"))
                    .password(plugin.getConfig().getString("mysql.password"))
                    .build()
                    .connect();
        }
        return new H2DatabaseType("org.h2.Driver", "jdbc:h2:~/test").connect();
    }
}
