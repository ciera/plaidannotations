# How to add a new annotation #
  1. Checkout all three projects: PlaidAnnotations, PlaidFeature, and PlaidAnnotationsUpdateSite
  1. Add the annotation in the appropriate package. Make a new package for your project if one isn't already there.
  1. Create the jar file. In PlaidAnnotations:
    1. Export to jar file
    1. Include the source of all annotations. Not the src directory though.
    1. Save as over the existing "plaid-annotations.jar"
  1. In PlaidAnnotations/plugin.xml, do the following:
    1. Increment the version number.
    1. If you have added a new package, add it to the list of exported packages
    1. If you have added a new source directory, select it in the "binary build"
  1. In PlaidAnnotationsFeature/feature.xml, increment the version number to match
  1. In PlaidAnnotationsUpdateSite/site.xml
    1. Select the existing feature (under Annotations), right click, and remove it.
    1. elect "Annotations, click "Add Feature.." and add the one with the new version number.
    1. Click "Build All".
  1. Commit, update from the update site, and make sure it worked!

And if you screw up, you MUST increment the version number again!