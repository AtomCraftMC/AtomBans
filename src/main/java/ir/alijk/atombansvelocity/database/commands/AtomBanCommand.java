package ir.alijk.atombansvelocity.database.commands;

import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import ir.alijk.atombansvelocity.AtomBansVelocity;
import ir.alijk.atombansvelocity.YMLLoader;
import ir.alijk.atombansvelocity.database.models.BanRecord;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class AtomBanCommand implements SimpleCommand {
    private final Component NO_PERMISSION = Component.text("You don't have permissions", NamedTextColor.RED);
    private final Component WRONG_ARGS = Component.text("Follow the damn train CJ", NamedTextColor.RED);
    private final Component DONE = Component.text("Done", NamedTextColor.GREEN);

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player)) return;

        Player p = (Player) invocation.source();
        String[] args = invocation.arguments();

        if (!p.hasPermission("atomban.admin")) {
            p.sendMessage(NO_PERMISSION);
            return;
        }

        if (!(args.length >= 2)) {
            p.sendMessage(WRONG_ARGS);
            return;
        }

        String type = args[0];
        String player = args[1];

        // Getting rest of args as reason
        LinkedList<String> argsCopy = new LinkedList<String>(Arrays.asList(args));
        argsCopy.removeFirst();
        argsCopy.removeFirst();
        String userReason = String.join(" ", argsCopy);

        long count = BanRecord.recordsCount(player, type);

        long start, multiply, max;
        start = multiply = max = 0;

        switch (type.toLowerCase()) {
            case "pvpban":
                start = YMLLoader.Config.PVPBAN_START;
                multiply = YMLLoader.Config.PVPBAN_MULTIPLY;
                max = YMLLoader.Config.PVPBAN_MAX;
                break;
            case "flyban":
                start = YMLLoader.Config.FLYBAN_START;
                multiply = YMLLoader.Config.FLYBAN_MULTIPLY;
                max = YMLLoader.Config.FLYBAN_MAX;
                break;
            case "moveban":
                start = YMLLoader.Config.MOVEBAN_START;
                multiply = YMLLoader.Config.MOVEBAN_MULTIPLY;
                max = YMLLoader.Config.MOVEBAN_MAX;
                break;
            case "playerban":
                start = YMLLoader.Config.PLAYERBAN_START;
                multiply = YMLLoader.Config.PLAYERBAN_MULTIPLY;
                max = YMLLoader.Config.PLAYERBAN_MAX;
                break;
            case "reset":
                BanRecord.deletePlayerRecords(player, type);
                p.sendMessage(DONE);
                return;
            default:
                return;

        }

        String reason = userReason + " - Count " + (count + 1);;

        if (count == 0) {
            exec("kick " + player + " " + reason.replace("ban", "kick"));
            p.sendMessage(DONE);

            BanRecord record = new BanRecord(player, type);
            record.save();

            return;
        }

        BanRecord record = new BanRecord(player, type);
        record.save();

        long hours = (int) (start * Math.pow(multiply, count - 1));
        if (hours >= max)
            hours = max;

        exec("tempipban " + player + " " + hours + "h " + reason);
        p.sendMessage(DONE);

    }

    private void exec(String command) {
        ProxyServer server = AtomBansVelocity.getInstance().getServer();
        server.getCommandManager().executeAsync(server.getConsoleCommandSource(), command);
    }
}
