#!/bin/bash
PROJECT_NAME="image-coffee-utils"
PREFIX="$PROJECT_NAME-"

# Config git
git config --global --add safe.directory /workspaces/$PROJECT_NAME

# Config zsh
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
echo -e "export ZSH=\$HOME/.oh-my-zsh\n" >~/.zshrc
echo -e "ZSH_THEME=\"refined\"\n" >>~/.zshrc
echo -e "plugins=(git zsh-autosuggestions)\n" >>~/.zshrc
echo -e "source $ZSH/oh-my-zsh.sh\n" >>~/.zshrc
echo -e "DISABLE_AUTO_UPDATE=true" >>~/.zshrc
echo -e "DISABLE_UPDATE_PROMPT=true\n" >>~/.zshrc
echo -e "alias ga=\"git add\"" >>~/.zshrc
echo -e "alias gcm=\"git commit -m\"" >>~/.zshrc
echo -e "alias gaa=\"git add .\"" >>~/.zshrc
echo -e "alias gco=\"git checkout\"" >>~/.zshrc
echo -e "alias gcb=\"git checkout -b\"" >>~/.zshrc
echo -e "alias gpl=\"git pull\"" >>~/.zshrc
echo -e "alias gps=\"git push\"" >>~/.zshrc
echo -e "alias gst=\"git status\"" >>~/.zshrc

# Build java microservices
./mvnw.sh clean install -DskipTests &

# Build python microservices
python -m pip install --upgrade pip
pip install poetry

bash -c "cd /workspaces/${PROJECT_NAME}/${PREFIX}colors && poetry install &"
