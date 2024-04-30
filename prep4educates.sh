#!/bin/bash

basedir="target/educates"

rm -rf $basedir

# Scan folders starting with two digits
for dir in [0-9][0-9]*/; do

    # Skip 60-documentation for now
    if [[ $dir == "60-"* ]]; then
        continue;
    fi

    mkdir -p "${basedir}/${dir}"

    # Find all AsciiDoc files recursively
    find $dir -name "*.adoc" -type f | while read -r file; do

        filename=$(basename "$file" .adoc)

        # Convert AsciiDoc to DocBook using Asciidoctor
        asciidoctor -b docbook -d book -a educates -o - "$file" | \

        # Convert DocBook to Markdown using Pandoc
        # -s to keep the primary headline
        pandoc -s -f docbook -t markdown -o "./target/educates/${dir}/${filename}.md"

    done

done
