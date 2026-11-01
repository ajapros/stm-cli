---
title: STM-CLI
section: 1
header: stm-cli Manual
footer: stm-cli 1.0.0
date: November 2026
---

# NAME

stm-cli - render and inspect Chenile state machine definitions

# SYNOPSIS

**stm-cli** *operation* \[*options*\] *states.xml*...

**stm-cli** **-h** | **-V**

# DESCRIPTION

**stm-cli** reads Chenile state definition files (states XML, the format of Chenile's STM and Finito) and renders
or inspects them. The state machine is not started, so the components named in the file need not exist or be on the
class path. When several files are given, they are read as one definition.

Exactly one operation must be given.

# OPERATIONS

**-s**, **\--uml-state-diagram**
:   Print a PlantUML state diagram.

**-m**, **\--mermaid**
:   Print a state diagram for Mermaid.

**-a**, **\--allowed-actions** *state*
:   Print the events allowed in *state*.

**-j**, **\--render-json**
:   Print the definition as JSON.

**-t**, **\--generate-test-cases**
:   Print test cases that walk the flow's transitions.

**-T**, **\--visualize-test-cases**
:   Print the generated test cases as a PlantUML diagram.

**-r**, **\--render-tests-as-state**
:   Write one PlantUML state diagram per generated test case into the folder given with **-o**.

# OPTIONS

**-o**, **\--output** *file-or-folder*
:   Write the output to *file* instead of standard output (a folder for **-r**).

**-S**, **\--styling** *rules.json*
:   Style states and transitions in diagrams according to their metadata.

**-e**, **\--enablement** *file.properties*
:   Apply enablement properties, so that disabled states and transitions are left out.

**-p**, **\--prefix** *prefix*
:   The prefix of the enablement properties.

**-h**, **\--help**
:   Show help.

**-V**, **\--version**
:   Show stm-cli's version and the Chenile version it was built with.

# ENVIRONMENT

**JAVA_HOME**
:   The Java installation to use (Java 25 or later). Otherwise **java** on the **PATH**, then Homebrew's
    **openjdk@25**.

**JAVA_OPTS**
:   Options passed to the Java virtual machine.

# EXAMPLES

```
stm-cli --mermaid returns-states.xml
stm-cli -s -S styles.json -o returns.puml returns-states.xml
stm-cli --allowed-actions INITIATED returns-states.xml
stm-cli --generate-test-cases returns-states.xml
stm-cli --render-tests-as-state -o test-diagrams returns-states.xml
```

# VERSIONS

stm-cli is released on its own cadence, independently of Chenile, and is built only against released Chenile
versions. **stm-cli -V** shows both.

# SEE ALSO

**jgen**(1), https://thefinito.org, https://chenile.org

# AUTHOR

Raja Shankar Kolluru and the Chenile contributors.

# COPYRIGHT

MIT License.
