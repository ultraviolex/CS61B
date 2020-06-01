# Gitlet Design Document

**Name**: Xiuhui Ming

## Classes and Data Structures


###Commit
Stores log message and commit date, references to parental commits,
hashmap of file names to blobs

**Fields**

1. String log message: Message in string format
2. Date commit date: Date of commit
3. String parents: String shaID references to parent commit(s)
4. Hashmap files: Mapping of file names to blob references
5. SHA ID: Unique shaID that serves as reference 
to this commit
   
###Blob
Content of a file

**Fields**

1. String ID: Unique shaID that serves are reference to this blob
2. String file name: name of the file
3. byte[] contents: contents of the file at commit time



## Algorithms
###.gitlet

All algorithms check if there is an existing repository and
if the number of arguments is correct

**init**

If no existing repository, it initializes .gitlet directory in CWD,
staging area directory within .gitlet, as well as makes the 
first commit

**add**

Checks if file exists, then calls on commit add method

**commit**

Checks for commit message then checks if staging area directory is 
not empty, then makes a blob for each file in the staging area and
adds that to the _files hashmap of filename to blob ID. Updates
the shaID, parent, message, and time fields and then saves current 
commit to a file named shaID

**remove**

Checks if file is in the current working directory and if file is 
being tracked. If file exists in staging area, it removes the file. 
If file is being tracked, it removes the file from being tracked. 
If file is also in the CWD, it removes the file. Does not remove file
from CWD if file is not being tracked by current commit. 

**log**

Prints out logs of the commit starting with the most recent and ending 
with the initial commit by calling on commit.log()

###Commit
**add**

Adds file to staging area. If file is already being tracked, checks
to see if file being added has changes, if not, it does not add the file
and if that file exists in the staging area, it removes it from staging
area.

**log**

Prints out log of commit.


## Persistence
In the .gitlet directory, I store commits as files. I also keep a 
commit file named current, which is a copy of the last file to be 
committed. Whenever the commit method is called, I update current
to have the files in the staging area in _files, the parent ID as currents shaID
(which is the previos commits shaID), the message as message, time as
the current date, and the shaID as a new shaID for current by calling the Utils.shaId method on current. Next, I store
current in the .gitlet directory as a commit with the file name shaID
and then I update my current file in the .gitlet directory. This means
that my current file is the same as the newly committed commit file.


