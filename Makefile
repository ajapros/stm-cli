.DEFAULT_GOAL := all

## all: Build the jar and the distribution zip (target/stm-cli-<version>-dist.zip)
.PHONY: all
all:
	mvn -B package

## run: Run stm-cli from the build, e.g. make run args="--mermaid flow.xml"
.PHONY: run
run:
	java -jar target/stm-cli-*-jar-with-dependencies.jar $(args)

## man: Regenerate the man page from src/man/stm-cli.1.md (needs pandoc)
.PHONY: man
man:
	pandoc -s -t man src/man/stm-cli.1.md -o src/man/stm-cli.1

## clean: Delete build output
.PHONY: clean
clean:
	mvn -B clean

## help: Show this help
.PHONY: help
help: Makefile
	@sed -n 's/^##//p' $< | column -t -s ':' | sed -e 's/^/ /'
