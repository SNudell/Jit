Important stuff!

I used serialization to store the files in the .jit/objects folder and therefore you wont be able to read them.
I asked prof. Jobst and this method was approved.
I dont delete my workspace and replace it, but instead write the "callback" in the directory ".jit/testReconstructs",
so that i wouldnt accidentally replace my workspace with some random shit due to a bug in my code.
Also for testung you can just add directories so for example i always used "add src"
i just couldn't be bothered to write so much stuff all the time,
so i just implemented the functionality to add directories.
The way i tested it was always:
jit init
jit add src
jit print
jit commit "something"
change something in the project
jit commit "something else"
jit list
jit callback "one of the commits"
check if the calback worked
jit delete
to clean up everything

To meet the criteria the only change would be to call delete Directory on the workspace and
then use the right directory as path for the reconstruction/callback
