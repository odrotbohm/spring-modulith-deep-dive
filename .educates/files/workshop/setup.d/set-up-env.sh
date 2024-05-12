#!/bin/bash

# Trim the value of the environment variables if they exist
exercisesDir="${EXERCISES_DIR// /}" # Removes spaces from the beginning and end
solutionsDir="${SOLUTIONS_DIR// /}" # Removes spaces from the beginning and end

vscodeFile=~/.vscode/settings.json
mkdir -p ~/.vscode

# Check if the environment variable exists and is not empty or blank
# Also, check if the trimmed value represents an existing directory on disk
if [ -n "$exercisesDir" ] && eval [ -d $exercisesDir ]; then

    # Create a symbolic link from ~exercises to the current lab code
    echo "Linking ~/exercises to $exercisesDir"
    if [ ! -L ~/exercises ]; then
        eval ln -s $exercisesDir ~/exercises
    fi

    # Configure VSCode
    echo "Creating '$vscodeFile' to control visible files in VSCode (show only ~/exercises/*)"
    cat << EOF > "$vscodeFile"
{
    "files.exclude": {
        "**/.*": true,
        "**/code": true,
        "**/exercises/**": false,
    },
    "editor.formatOnSave": true
}
EOF

else
    echo "[WARNING] The environment variable EXERCISES_DIR does not exist, is empty or blank, or does not represent an existing directory on disk"

    # Configure VSCode
    echo "Creating '$vscodeFile' to control visible files in VSCode (hide all 'dot' files)"
    cat << EOF > "$vscodeFile"
{
    "files.exclude": {
        "**/.*": true,
    },
    "editor.formatOnSave": true
}
EOF

fi

# Check if the environment variable exists and is not empty or blank
# Also, check if the trimmed value represents an existing directory on disk
if [ -n "$solutionsDir" ] && eval [ -d $solutionsDir ]; then
    # Create a symbolic link from ~solutions to the current lab code
    echo "Linking ~/solutions to $solutionsDir"
    if [ ! -L ~/solutions ]; then
        eval ln -s $solutionsDir ~/solutions
    fi
fi