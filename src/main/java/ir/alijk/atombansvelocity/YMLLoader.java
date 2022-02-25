package ir.alijk.atombansvelocity;


import com.moandjiezana.toml.Toml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// ------------------------------------
//       Handling Config Files
// ------------------------------------
public class YMLLoader {
        public static Toml configuration;
        /**
         * This method will fully load config files
         *
         */
        public static void load(Path path, Class<?> clazz) {
                File folder = path.toFile();
                File file = new File(folder, "config.toml");
                if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                }

                if (!file.exists()) {
                        try (InputStream input = clazz.getResourceAsStream("/" + file.getName())) {
                                if (input != null) {
                                        Files.copy(input, file.toPath());
                                } else {
                                        file.createNewFile();
                                }
                        } catch (IOException exception) {
                                exception.printStackTrace();
                        }
                }

                configuration = new Toml().read(file);

                Config.init();
        }


        public static class Config {
                public static long PVPBAN_START;
                public static long PVPBAN_MULTIPLY;
                public static long PVPBAN_MAX;

                public static long PLAYERBAN_START;
                public static long PLAYERBAN_MULTIPLY;
                public static long PLAYERBAN_MAX;

                public static long MOVEBAN_START;
                public static long MOVEBAN_MULTIPLY;
                public static long MOVEBAN_MAX;

                public static long FLYBAN_START;
                public static long FLYBAN_MULTIPLY;
                public static long FLYBAN_MAX;


                public static void init() {
                        Toml pvp = configuration.getTable("PVP");
                        PVPBAN_START = pvp.getLong("start");
                        PVPBAN_MULTIPLY = pvp.getLong("multiply");
                        PVPBAN_MAX = pvp.getLong("max");

                        Toml player = configuration.getTable("PLAYER");
                        PLAYERBAN_START = player.getLong("start");
                        PLAYERBAN_MULTIPLY = player.getLong("multiply");
                        PLAYERBAN_MAX = player.getLong("max");

                        Toml move = configuration.getTable("MOVE");
                        MOVEBAN_START = move.getLong("start");
                        MOVEBAN_MULTIPLY = move.getLong("multiply");
                        MOVEBAN_MAX = move.getLong("max");

                        Toml fly = configuration.getTable("FLY");
                        FLYBAN_START = fly.getLong("start");
                        FLYBAN_MULTIPLY = fly.getLong("multiply");
                        FLYBAN_MAX = fly.getLong("max");

                }
        }
}