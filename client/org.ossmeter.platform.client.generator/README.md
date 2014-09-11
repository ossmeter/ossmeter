#README

To regenerate clients, take the following steps:

* Copy the latest versions of all relevant .ecore files into the ```models``` directory
* Update the build.xml ```loadModels``` target to include all .ecore files (TODO: an automated way of loading all ecore models in a directory would be really useful here).
* Run the appropriate generators

To add a new language:

* Create a folder with the language's name, e.g. "haskell"
* Edit line 32 of build.xml to include the language in the list, e.g. ```<for list="java,csharp,haskell" ...```