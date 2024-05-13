#!/bin/bash

script="$0"
scriptDir="$(dirname "$script")"
basedir="target/educates"
springModulithVersion=$($scriptDir/../mvnw help:evaluate -Dexpression=spring-modulith.version -q -DforceStdout)

rm -rf $basedir

# Scan folders starting with two digits
for dir in [0-9][0-9]*/; do

    # Move setup files
    target=${basedir}/workshops/${dir}
    mkdir -p $target

    if [[ $(basename $dir) != "00-introduction" ]]; then
        cp -r .educates/files/* $target
    fi

    # Find all AsciiDoc files recursively
    find $dir -name "[0-9][0-9][0-9]*.adoc" -type f | while read -r file; do

        filename=$(basename "$file" .adoc)

        if [[ $filename =~ .*-article-.* ]]; then
            targetDir="${basedir}/${dir}"
            targetFile="content.md"
            isArticle=true
        else
            targetDir="${basedir}/workshops/${dir}workshop/content"
            targetFile="${filename}.md"
            isArticle=false
        fi

        # Create folder
        mkdir -p $targetDir


        # Convert AsciiDoc to DocBook using Asciidoctor
        asciidoctor -b docbook -d book \
            -a educates \
            -a tabsize=4 \
            -a imagesdir=images \
            -a spring-modulith-version=$springModulithVersion \
            -o - "$file" | \

        # Convert DocBook to Markdown using Pandoc
        # -s to keep the primary headline
        pandoc -s -f docbook -t markdown -o "${targetDir}/${targetFile}"

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
        if [[ $line =~ !\[img\]\((.*)\) ]]; then
            mkdir $target/$imagesdir
            cp "${asciidoctorbase}/${BASH_REMATCH[1]}" "${target}/${BASH_REMATCH[1]}"
        fi
    done < "$file"

done
