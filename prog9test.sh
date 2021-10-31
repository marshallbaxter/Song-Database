#!/bin/bash

# Marshall Baxter

clear

value="she loves you"

#javac student/*.java

echo $value
# Run program and output results to file

java student.PhraseRanking allSongs.txt "$value" > prog9files/prog9outSheLovesYou.txt


diff prog9files/prog9outSheLovesYou.txt prog9files/sampleResults_She_loves_you.txt -y --strip-trailing-cr --ignore-blank-lines --ignore-space-change | tee prog9files/results.txt

value="You can't always get what you want"

echo $value
java student.PhraseRanking allSongs.txt "$value" > prog9files/prog9outYouCant.txt


diff prog9files/prog9outYouCant.txt prog9files/sampleResults_You_can_t_always_get_what_you_want.txt -y --strip-trailing-cr --ignore-blank-lines --ignore-space-change | tee prog9files/results.txt


value="shout shout shout"

echo $value
java student.PhraseRanking allSongs.txt "$value" > prog9files/prog9outShout.txt


diff prog9files/prog9outShout.txt prog9files/sampleResults_shout_shout_shout.txt -y  --strip-trailing-cr --ignore-blank-lines --ignore-space-change | tee prog9files/results.txt

echo All done
