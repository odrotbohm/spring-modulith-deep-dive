#!/bin/bash

set +x

## NOTE: git clone is now being done in ../../resources/workshop.yaml
#git clone https://github.com/odrotbohm/spring-modulith-deep-dive ~/exercises

git config --global user.name student
git config --global user.email "student@example.com"

cd ~/exercises

for dir in [0-9][0-9]-*/; do

  branch_name="$(basename "$dir")"
  [[ $branch_name == "00-introduction" ]] && continue

  # Create branch
  git checkout --orphan "$branch_name" origin/main
  git add -A  # Add all files from the main branch to the staging area
  git commit -m "Initial commit for branch $branch_name"

  # Delete all other lab directories
  ls -d [0-9][0-9]-*/ | grep -v "$branch_name" | xargs git rm -r
  git rm -r src/
  git rm -r .github/
  #git rm readme.adoc
  find . -name "*.adoc" | xargs git rm
  git commit -m "Keep only necessary lab files"

done

git checkout main
git reset --hard origin/main
