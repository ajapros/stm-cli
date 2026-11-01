# stm-cli

`stm-cli` renders and inspects Chenile state machine definitions (the states XML used by Chenile's STM and
[Finito](https://thefinito.org)) from the command line. It doesn't start the state machine, so no application code
is needed.

```bash
stm-cli --mermaid returns-states.xml                    # Mermaid state diagram
stm-cli -s -o returns.puml returns-states.xml           # PlantUML state diagram
stm-cli --allowed-actions INITIATED returns-states.xml  # events allowed in a state
stm-cli --generate-test-cases returns-states.xml        # test cases that walk every transition
stm-cli --render-tests-as-state -o diagrams returns-states.xml
```

Every option is in the manual page, `man stm-cli` (source: `src/man/stm-cli.1.md`).

## Install

```bash
brew install ajapros/jgen-tap/stm-cli               # macOS / Linux; installs the man page and completions
jbang app install stm-cli@ajapros/stm-cli       # any OS; JBang fetches Java if needed
```

Or download `stm-cli-<version>.zip` from the releases and put its `bin` folder on your PATH. Java 25 or later is
needed.

## Versions

stm-cli has its own version line (1.0.0 onwards) and release cadence, separate from Chenile and from JGen.

- **Built only against released Chenile.** It uses a released Chenile (`chenile-parent` in `pom.xml`, now 2.1.35)
  for the STM readers and generators in `org.chenile:workflow-utils`. The `Build stm-cli` workflow builds from
  Maven Central only, so a dependency on an unreleased Chenile fails there.
- **`stm-cli -V`** shows both versions.

The command used to be `org.chenile.workflow.cli.CLI` inside workflow-utils. It moved here as
`org.chenile.stmcli.StmCli`; the work is still done by workflow-utils' `CLIHelper`.

## Build

```bash
make            # mvn package: target/stm-cli-<version>-jar-with-dependencies.jar and target/stm-cli-<version>-dist.zip
make run args="--mermaid src/test/resources/happy-path.xml"
```

## Releasing

1. Set `<revision>` in `pom.xml` if needed, commit and push.
2. Tag and push: `git tag -a 1.0.0 -m "stm-cli 1.0.0" && git push origin 1.0.0`.

The tag runs `.github/workflows/release.yml`, which builds with `-Drevision=<tag>` and runs JReleaser
(`jreleaser.yml`). JReleaser publishes the GitHub release (the zip and a version-free `stm-cli.jar` for JBang) and
adds or updates the `stm-cli` formula in `ajapros/homebrew-jgen-tap`. It needs the repository secret
`JRELEASER_HOMEBREW_GITHUB_TOKEN`, a fine-grained token for `ajapros/homebrew-jgen-tap` with Contents read and write.

## License

MIT
