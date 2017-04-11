string=$(ps)
mvn compile
if [[ $string == *"tmux"* ]]; then
  echo "tmux"

  tmux rename-session "sec"
  window=$(tmux list-windows -t sec | cut -d ':' -f 1 |awk 'END{print}')
  var=0
  var=$((window+1))

  for (( i=0; i < $1; i++))
  do
    tmux new-window
    tmux send-keys -t sec:$var " mvn exec:java -Dexec.args="808"$i" Enter
    var=$((var+1))
  done
else
  for (( i=0; i < $1; i++))
  do
    var=$(echo '"mvn exec:java -Dexec.args=808'$i)
    var=$var'"'
    osascript -e 'tell application "iTerm" to activate' \
        -e 'tell application "System Events" to tell process "iTerm" to keystroke "t" using command down' \
        -e 'tell application "System Events" to tell process "iTerm" to keystroke "cd '$(pwd)'"' \
        -e 'tell application "System Events" to tell process "iTerm" to key code 52' \
        -e 'tell application "System Events" to tell process "iTerm" to keystroke "'"${var//\"/}"'"'  \
        -e 'tell application "System Events" to tell process "iTerm" to key code 52'

  done
fi
