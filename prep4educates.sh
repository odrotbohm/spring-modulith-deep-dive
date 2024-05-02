#!/bin/bash

basedir="target/educates"

rm -rf $basedir

# Scan folders starting with two digits
for dir in [0-9][0-9]*/; do

    # Spring Academy Course expected folder structure
    target="${basedir}/workshops/${dir}workshop"

    # Create folder
    mkdir -p $target/content

    # Find all AsciiDoc files recursively
    find $dir -name "*.adoc" -type f | while read -r file; do

        filename=$(basename "$file" .adoc)

        # Convert AsciiDoc to DocBook using Asciidoctor
        asciidoctor -b docbook -d book \
            -a educates \
            -a imagesdir=images \
            -o - "$file" | \

        # Convert DocBook to Markdown using Pandoc
        # -s to keep the primary headline
        pandoc -s -f docbook -t markdown -o "${target}/content/${filename}.md"

        # Copy branches script
        cp -r educates-files/* $target/

    done

done

# Copy images to target folder
asciidoctorbase="src/docs/asciidoc"
imagesdir="images"
files=$(find $basedir -type f -name "*.md")

# Loop through each file
for file in $files; do

    target=$(dirname $file)

    # Search for image tags and extract src attribute value
    while IFS= read -r line; do
        if [[ $line =~ \<img.*src=\"([^\"]+)\" ]]; then
            mkdir $target/$imagesdir
            cp "${asciidoctorbase}/${BASH_REMATCH[1]}" "${target}/${BASH_REMATCH[1]}"
        fi
    done < "$file"

done
