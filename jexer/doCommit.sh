#!/usr/bin/env bash

git config --global user.name "sdimaio"
git config --global user.email "simmaco.dimaio@gmail.com"
git remote set-url origin git@github.com:sdimaio/jexer.git
git checkout
git status
git add .
# Verifica se è stato passato un messaggio come argomento
if [ -z "$1" ]; then
    # Se non è stato passato un messaggio, usa la data e l'ora come messaggio di commit
    now=$(date +"%d_%m_%Y__%H_%M_%S")
    commit_message="$now"
else
    # Se è stato passato un messaggio, usalo come messaggio di commit
    commit_message="$1"
fi

# Esegui il commit con il messaggio scelto
git commit -m "$commit_message"

#now=$(date +"%d_%m_%Y__%H_%M_%S")
# git commit -m $(echo "$now")
git push --force
git push --set-upstream origin master --force
git status


