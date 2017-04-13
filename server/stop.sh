echo $i
for (( p=0; p < $i; p++))
do
  echo $i
  tmux kill-window -t "server"$p
done
