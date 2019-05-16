## How to Contribute

### Raising an issue:
 This is an Open Source project and we would be happy to see contributors who report bugs and file feature requests submitting pull requests as well.
 This project adheres to the Contributor Covenant code of conduct.
 By participating, you are expected to uphold this code style.
 Please report issues here [Issues - dsciitpatna/SAAR-Android-Client](https://github.com/dsciitpatna/SAAR-Android-Client/issues)

### Branch Policy

#### Sending pull requests:

Go to the repository on github at https://github.com/dsciitpatna/SAAR-Android-Client
.

Click the “Fork” button at the top right.

You’ll now have your own copy of the original dsciitpatna repository in your github account.

Open a terminal/shell.

Type

`$ https://github.com/username/SAAR-Android-Client.git`

where 'username' is your username.

You’ll now have a local copy of your version of the original dsciitpatna repository.

#### Change into that project directory (SAAR-Android-Client):

`$ cd SAAR-Android-Client`

#### Add a connection to the original owner’s repository.

`$ git remote add upstream https://github.com/dsciitpatna/SAAR-Android-Client.git`

#### To check this remote add set up:

`$ git remote -v`

#### Make changes to files.

`git add` and `git commit` those changes

`git push` them back to github. These will go to your version of the repository.

#### Now Create a PR (Pull Request)
Go to your version of the repository on github.

Click the “New pull request” button at the top.

Note that dsciitpatna’s repository will be on the left and your repository will be on the right.

Click the green button “Create pull request”. Give a succinct and informative title, in the comment field give a short explanation of the changes and click the green button “Create pull request” again.

#### Pulling others’ changes
Before you make further changes to the repository, you should check that your version is up to date relative to dsciitpatna’s version.

Go into the directory for the project and type:

`$ git checkout development`
`$ git pull upstream development --rebase`

This will pull down and merge all of the changes that have been made in the original dsciitpatna repository.

Now push them back to your github repository.

`$ git push origin development`
