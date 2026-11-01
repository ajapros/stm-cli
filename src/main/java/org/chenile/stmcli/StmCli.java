package org.chenile.stmcli;

import org.chenile.workflow.cli.CLIHelper;
import org.chenile.workflow.cli.CLIParams;
import picocli.CommandLine;

import java.io.File;

import static picocli.CommandLine.*;

/**
 * stm-cli: reads Chenile state definition (states XML) files and renders diagrams, allowed actions, JSON and test
 * cases. The STM is not started, so the components named in the file need not be on the class path.
 *
 * <p>Moved here from Chenile's workflow-utils ({@code org.chenile.workflow.cli.CLI}) so that stm-cli is released on
 * its own cadence. The work is still done by workflow-utils' {@link CLIHelper}.</p>
 */
@Command(name = "stm-cli", mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        description = "Reads a State Definition file and allows a few operations on it.%n" +
                "STM is not created. Hence components don't have to be in the class path.")
public class StmCli implements Runnable {
    @ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive exclusive;

    static class Exclusive {
        @Option(names = {"-s", "--uml-state-diagram"}, description = "Generate a UML (PlantUML) state diagram")
        boolean umlStateDiagram;
        @Option(names = {"-a", "--allowed-actions"}, paramLabel = "state", description = "Return allowed actions for a state")
        String stateForAllowedActions;
        @Option(names = {"-j", "--render-json"}, description = "Return a JSON representation of the XML file")
        boolean toJson;
        @Option(names = {"-t", "--generate-test-cases"}, description = "Generates test cases")
        boolean genTestCase;
        @Option(names = {"-T", "--visualize-test-cases"}, description = "Visualizes test cases")
        boolean visualizeTestCase;
        @Option(names = {"-r", "--render-tests-as-state"}, description = "Renders state diagrams for all generated test cases")
        boolean renderTestsAsStateDiagram;
        @Option(names = {"-m", "--mermaid"}, description = "Generate a state diagram compatible with Mermaid JS")
        boolean renderMermaidStateDiagram;
    }

    @Parameters(index = "0..*", paramLabel = "<XML File names>",
            description = "The XML files to read. They must be valid states XML. Component names in them are ignored.")
    File[] xmlFileNames;
    @Option(names = {"-o", "--output"}, paramLabel = "output-file-or-directory",
            description = "Writes output to the specified file, or directory for multiple files")
    String outputFile;
    @Option(names = {"-S", "--styling"}, paramLabel = "styling-rules-file",
            description = "Use the JSON file for setting styles according to metadata in states and transitions")
    File stylingFile;
    @Option(names = {"-e", "--enablement"}, paramLabel = "enablement-properties-file",
            description = "Use the properties file for enablement properties")
    File enablementPropertiesFile;
    @Option(names = {"-p", "--prefix"}, paramLabel = "prefix", description = "The prefix for all properties")
    String prefix;

    private final CLIHelper cliHelper = new CLIHelper();

    public static void main(String... args) {
        System.exit(new CommandLine(new StmCli()).execute(args));
    }

    @Override
    public void run() {
        CLIParams params = makeCLIParams();
        try {
            if (exclusive.umlStateDiagram) {
                cliHelper.renderStateDiagram(params, outputFile);
            } else if (exclusive.renderMermaidStateDiagram) {
                cliHelper.renderMermaidStateDiagram(params, outputFile);
            } else if (exclusive.stateForAllowedActions != null && !exclusive.stateForAllowedActions.isEmpty()) {
                cliHelper.allowedActions(params, outputFile);
            } else if (exclusive.toJson) {
                cliHelper.toJson(params, outputFile);
            } else if (exclusive.genTestCase) {
                cliHelper.renderTestCases(params, outputFile);
            } else if (exclusive.visualizeTestCase) {
                cliHelper.renderTestPuml(params, outputFile);
            } else if (exclusive.renderTestsAsStateDiagram) {
                cliHelper.visualizeTestcaseAsStateDiagram(params, outputFile);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CLIParams makeCLIParams() {
        CLIParams params = new CLIParams();
        params.enablementPropertiesFile = this.enablementPropertiesFile;
        params.prefix = this.prefix;
        params.stateForAllowedActions = this.exclusive.stateForAllowedActions;
        params.stylingFile = this.stylingFile;
        params.xmlFiles = xmlFileNames;
        return params;
    }
}
