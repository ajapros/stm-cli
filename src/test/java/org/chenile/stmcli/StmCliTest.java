package org.chenile.stmcli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import picocli.CommandLine;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class StmCliTest {
    @TempDir Path temporary;

    private Path flow() throws Exception {
        Path xml = temporary.resolve("happy-path.xml");
        try (var in = getClass().getResourceAsStream("/happy-path.xml")) {
            Files.copy(in, xml, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return xml;
    }

    private int run(String... args) {
        return new CommandLine(new StmCli()).execute(args);
    }

    @Test void rendersAMermaidDiagram() throws Exception {
        Path out = temporary.resolve("flow.mmd");
        assertEquals(0, run("-m", "-o", out.toString(), flow().toString()));
        String diagram = Files.readString(out);
        assertTrue(diagram.contains("INITIATED"), diagram);
        assertTrue(diagram.contains("APPROVED"), diagram);
    }

    @Test void listsAllowedActions() throws Exception {
        Path out = temporary.resolve("actions.txt");
        assertEquals(0, run("-a", "INITIATED", "-o", out.toString(), flow().toString()));
        String actions = Files.readString(out);
        assertTrue(actions.contains("approve") && actions.contains("cancel"), actions);
    }

    @Test void generatesTestCases() throws Exception {
        Path out = temporary.resolve("tests.txt");
        assertEquals(0, run("-t", "-o", out.toString(), flow().toString()));
        assertFalse(Files.readString(out).isBlank());
    }

    @Test void needsExactlyOneOperation() throws Exception {
        String xml = flow().toString();
        assertNotEquals(0, run(xml));
        assertNotEquals(0, run("-m", "-s", xml));
    }

    @Test void reportsItsVersion() {
        String[] version = new VersionProvider().getVersion();
        assertTrue(version[0].startsWith("stm-cli "));
        assertTrue(version[1].startsWith("built with Chenile "));
    }
}
