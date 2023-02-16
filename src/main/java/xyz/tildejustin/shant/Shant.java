package xyz.tildejustin.shant;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModOrigin;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class Shant implements ModInitializer {
    public static final String modid = "shant";
    private static final Logger logger = LogManager.getLogger(modid);

    public void log(Level level, String text) {
        logger.log(level, text);
    }

    @Override
    public void onInitialize() {
        Path modDir = FabricLoader.getInstance().getGameDir().resolve("mods");
        StringBuilder text = new StringBuilder();
        text.append(modid).append(":\n");
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        for (ModContainer mod : mods) {
            System.out.println(modDir);
            if (mod.getOrigin().getKind() != ModOrigin.Kind.NESTED && mod.getOrigin().getPaths().get(0).getParent().equals(modDir)) {
                try {
                    text.append("    - ")
                            .append(mod.getMetadata().getName())
                            .append(" ")
                            .append(mod.getMetadata().getVersion())
                            .append(": ")
//                            assuming one path for each mod
//                            I don't think any mods break this (maybe optifine), but it should probably be documented
                            .append(DigestUtils.sha256Hex(Files.newInputStream(mod.getOrigin().getPaths().get(0))))
                            .append("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log(Level.INFO, text.toString());
    }
}
