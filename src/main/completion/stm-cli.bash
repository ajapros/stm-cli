# bash completion for stm-cli
_stm_cli() {
    local cur prev
    cur="${COMP_WORDS[COMP_CWORD]}"
    prev="${COMP_WORDS[COMP_CWORD-1]}"
    case "$prev" in
        -o|--output) COMPREPLY=( $(compgen -f -- "$cur") ); return ;;
        -S|--styling) COMPREPLY=( $(compgen -f -X '!*.json' -- "$cur") $(compgen -d -- "$cur") ); return ;;
        -e|--enablement) COMPREPLY=( $(compgen -f -X '!*.properties' -- "$cur") $(compgen -d -- "$cur") ); return ;;
        -a|--allowed-actions|-p|--prefix) return ;;
    esac
    if [[ "$cur" == -* ]]; then
        COMPREPLY=( $(compgen -W "-s --uml-state-diagram -m --mermaid -a --allowed-actions -j --render-json
            -t --generate-test-cases -T --visualize-test-cases -r --render-tests-as-state -o --output
            -S --styling -e --enablement -p --prefix -h --help -V --version" -- "$cur") )
    else
        COMPREPLY=( $(compgen -f -X '!*.xml' -- "$cur") $(compgen -d -- "$cur") )
    fi
}
complete -o filenames -F _stm_cli stm-cli
