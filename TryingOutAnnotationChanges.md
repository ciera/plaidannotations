# Introduction #

If you check out the `PlaidAnnotations` project and change or add annotations, and then fire up a child Eclipse expecting to take advantage of your changes, you'll be disappointed.  The annotations will be exactly as before, and your changes won't be visible in the child Eclipse.

The problem is that the child eclipse really only looks at the Jar file, plaid-annotations.jar, that's in the project directory.  So, you have to re-build that Jar file.  If you fire up your child Eclipse now, you'll see your changes there.

Therefore, also make sure you commit a new Jar together with any changes to the annotations.

# Details #

The Jar file you need to build must override the existing file.  (If you make a mistake, you can always revert to the Jar file that's in SVN.)  It must contain all the annotations defined in the project.  Currently (March 2009) that means the Jar file must contain the content of the "plural" and "crystal" source folders, but **not** the "src" source folder.  Use the following steps to create the Jar file:

  * Select the files to include in the Jar in Eclipse
  * Go to File | Export... and select Java -> Jar File from the list
  * On the "Next" page, make sure all the needed files are selected.  Make sure to include the source files and binaries into the Jar so that users will be able to browse the source.
  * Also make sure that you specify the right name and location to override the existing Jar file.
  * You can probably select finish at this point, or go through the remaining pages of the wizard to make sure all selections are sensible.  The default settings seem to be sufficient.

That's it!  Eclipse should have now overridden the existing with a new Jar file.  If you fire up your child Eclipse, it should use that new file.  Again, don't forget to commit this file together with any annotation changes you made so that others can see your changes as well.