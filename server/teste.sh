string=$(ps)
if [[ $string == *"tmux"* ]]; then
  echo "It's there!"
fi
