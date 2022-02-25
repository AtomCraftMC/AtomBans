package ir.alijk.atombansvelocity;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import ir.alijk.atombansvelocity.database.DataSource;
import ir.alijk.atombansvelocity.database.commands.AtomBanCommand;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

@Plugin(
        id = "atombansvelocity",
        name = "AtomBansVelocity",
        version = "1.0.0",
        authors = {"Alijk"}
)
public class AtomBansVelocity {
    private static AtomBansVelocity instace;

    @Getter
    private Path folder;
    @Getter
    private ProxyServer server;

    @Inject
    private Logger logger;

    @Inject
    public AtomBansVelocity(ProxyServer server, Logger logger, @DataDirectory final Path folder) {
        instace = this;
        this.logger = logger;
        this.folder = folder;
        this.server = server;
        YMLLoader.load(folder, getClass());

        try {
            DataSource.SQLite();
        } catch (SQLException | IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        server.getCommandManager().register("atomban", new AtomBanCommand());
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {

    }


    public static AtomBansVelocity getInstance() {
        return instace;
    }
}
