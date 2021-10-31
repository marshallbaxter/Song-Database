#!/bin/bash

# Marshall Baxter

declare -a StringArray=("a man hears what he wants to hear and disregards the rest"
 "All of us get lost in the darkness" "All you need is love" "An honest man's pillow is his peace of mind"
  "And in the end, the love you take is equal to the love you make" "Before you accuse me take a look at yourself"
  "Bent out of shape from society's pliers" "Different strokes for different folks"
  "Don't ask me what I think of you" "Don't you draw the Queen of Diamonds" "Even the genius asks questions"
  "Every new beginning comes from some other beginning's end" "knowledge you're nothing, knowledge is king"
  "Fathers be good to your daughters" "Fear is the lock and laughter the key to your heart"
  "For what is a man, what has he got? If not himself" "Freedom, well, that's just some people talking"
  "Freedom's just another word for nothing left to lose" "Get your head out of the mud"
  "Heard ten thousand whispering and nobody listening" "Hero not the handsome actor who plays a hero's role"
  "How many ears must one man have before he can hear people cry?" "I can dig it, he can dig it, she can dig it, we can dig it"
  "I don't need no money, fortune, or fame" "I got a lot to say" "I said, baby, do you have no shame?"
  "I understand about indecision" "I was born with a plastic spoon in my mouth" "I was so much older then, I'm younger than that now"
  "I'd rather be a hammer than a nail" "If we were blind and had no choice" "If we weren't all crazy we would go insane"
  "If you believe in forever, then life is just a one night stand" "If you choose not to decide, you still have made a choice"
  "If you smile at me I will understand" "I'm not a number. Dammit, I'm a man" "I'm proud to be an American, where at least I know I'm free"
  "It ain't me, it ain't me, I ain't no Senator's son" "It could be a spoonful of diamonds, could be a spoonful of gold"
  "It seems to me, sorry seems to be the hardest word" "It's been a hard day's night" "It's better to burn out, than to fade away"
  "It's the end of the world as we know it and I feel fine" "Just slip out the back, Jack" "Love is a battlefield" 
  "Love when you can, cry when you have to" "My uniform is leather and my power is my age" "No matter what you do, you'll never run away from you"
  "Nobody wants him, he just stares at the world" "Ob-la-di, Ob-la-da, life goes on" "Once upon a time I was falling in love, now I'm only falling apart."
  "One victim lives in tragedy, another victim stops to stare" "Optimism is my best defense" "Our early morning singing song"
  "Rather die on our feet, than keep living on our knees." "Read about some squirrelly guy who claims that he just don't believe in fighting"
  "Same old song, just a drop of water in an endless sea" "Send me dead flowers to my wedding" "Silence means security, silence means approval"
  "Since my baby left me, I've found a new place to dwell" "So often in time it happens, we all live our life in chains"
  "So tonight you better stop and rebuild all your ruins" "Some people never come clean, I think you know what I mean"
  "Space ain't man's final frontier, man's final frontier is the soul" "Take my hand, take my whole life too, but I can't help falling in love with you"
  "Talk about your plenty, talk about your ills, one man gathers what another man spills" "The bubble headed bleach blonde comes on at five"
  "The future is so bright that I have got to wear shades" "The pain of war cannot exceed the woe of aftermath" "The path that I have chosen now has led me to a wall"
  "The preacher said, you know you always have the Lord by your side" "The swift don't win the race" "The words of the prophets are written on the subway walls"
  "Then one day you find, ten years have got behind you" "There are places I remember all my life" "There's an opera out on the turnpike"
  "Thinking is the best way to travel" "Time can bring you down" "War is not the answer" "We learned more from a three minute record than we ever learned in school"
  "Well, you don't tug on Superman's cape" "We're going to Surf City" "We're just two lost souls swimming in a fish bowl year after year"
  "What the world needs now is love, sweet love" "When I first saw you with your smile so tender" "When you call my name, I salivate like Pavlov's dog"
  "Where am I to go, now that I've gone too far?" "nobody give me trouble cause they know I've got it made" "cause the times they are a-changing"
  "You bought a guitar to punish your Mom" "You can't always get what you want" "You can't fight the tears that ain't coming" "You don't have to be old to be wise"
  "You don't need a weatherman to know which way the wind blows" "You got to know when to hold them" "You rise as high as your dominant aspiration"
  "You shake my nerves and you rattle my brain" "we might find a higher purpose and a better use of mind" "You're dangerous cause your honest" "You're living in your own private Idaho")

> out.txt

# Basic loop
for value in "${StringArray[@]}"; do
	echo $value
  # Run program and output results to file
  java student.SearchByLyricsWords allSongs.txt "$value" >> out.txt

done

diff out.txt 100LyricsWordsSearches.txt --strip-trailing-cr --ignore-blank-lines --ignore-space-change | tee results.txt

echo All done
