GENERAL NOTES
=============Useage Example (make sure csv files are comma delimited):
java -jar Checker.jar -t targetValuesCSVFile -p predictionValuesCSVFile -c predictionConfidenceCSVFile

IMPLEMENTATION SUMMARY
====================PACKAGES
--------
checker package:	This package contains all files specific to this application.
turkov package:		Utility package

CLASSES
-------
Checker.java:  		This class contains the main method and should not need to be instantiated.

DataItem.java:		An instance of this class represents one entry in a label containing all neccessary
			data and/or metadata.

Label.java:		An instance of this object represents a label as specified in the rules.	

OTHER
====If you encounter any unusual errors or other problems please send an email or call the author of this program:
Eugene Turkov
eturkov@email.arc.nasa.gov 
650-604-1363