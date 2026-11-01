package org.chenile.stmcli;

import picocli.CommandLine.IVersionProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** {@code stm-cli --version}: stm-cli's own version and the released Chenile it was built with. */
public class VersionProvider implements IVersionProvider {
    @Override
    public String[] getVersion() {
        List<String> lines = read();
        return new String[]{
                "stm-cli " + (lines.size() > 0 ? lines.get(0) : "unknown"),
                "built with Chenile " + (lines.size() > 1 ? lines.get(1) : "unknown")
        };
    }

    private List<String> read() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("stm-cli-version.txt")) {
            if (in == null) return List.of();
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                    .map(String::trim).filter(l -> !l.isEmpty()).toList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
