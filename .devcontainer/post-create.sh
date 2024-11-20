#!/bin/bash
PROJECT_NAME="image-coffee-utils"
PREFIX="$PROJECT_NAME-"

# Config git
git config --global --add safe.directory /workspaces/$PROJECT_NAME
git config devcontainers-theme.show-dirty 1

# Config zsh
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
echo "export ZSH_THEME=\"refined\"" >> ~/.zshrc
echo "plugins=(git, zsh-autosuggestions)" >> ~/.zshrc

# Build java microservices
./mvnw clean install -DskipTests

# Build python microservices
python -m pip install --upgrade pip
pip install poetry

bash -c "cd /workspaces/${PROJECT_NAME}/${PREFIX}colors && poetry install"
