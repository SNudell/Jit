# JIT

## Assignment for Advanced Java Programming

This is supposed to be a basic clone of git. 
Implemented commands:
 
    1. init
    2. add [file or directory] // == git add
    3. remove [file] // unstage file
    3. print  // prints the current staging area
    4. commit "message" // == git commit
    5. delete // will delete the entire .jit directory
    6. callback // == git checkout
    7. list // == git log

## Note
callback will not actually callback your jit directory to the state of the commit, but instead callback the commit in the directory .jit/reconstructs.
This is for development purposes where i didn't want to accidentally delete my project ;)
Important stuff!