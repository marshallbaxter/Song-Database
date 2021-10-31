#!/bin/bash

# Marshall Baxter

#javac student/*.java

# Run program and output results to file

#java student.SearchByLyricsPhrase allSongs.txt "school's out" > prog10testFiles/prog10outschoolsout.txt

#diff prog10testFiles/prog10outschoolsout.txt prog10testFiles/resultSchoolsOut.txt -y  --strip-trailing-cr --ignore-blank-lines --ignore-space-change

#java student.SearchByLyricsPhrase allSongs.txt "love love love" > prog10testFiles/prog10outlovelovelove.txt
#diff prog10testFiles/prog10outlovelovelove.txt prog10testFiles/resultLoveLoveLove.txt -y  --strip-trailing-cr --ignore-blank-lines --ignore-space-change

#java  student.SearchByLyricsPhrase allSongs.txt "she loves you" > prog10testFiles/prog10outSheLovesYou.txt
#diff prog10testFiles/prog10outSheLovesYou.txt prog10testFiles/resultSheLovesYou.txt -y --strip-trailing-cr --ignore-blank-lines --ignore-space-change

java student.P10timer "allSongs.txt"


#clear

#diff prog10testFiles/prog10output.txt prog10testFiles/prog10results.txt -y --strip-trailing-cr --ignore-blank-lines --ignore-space-change | tee results.txt


echo All done
