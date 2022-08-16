# Trash-Site-Locator
A program that uses multithreading in Java to parse data from multiple files in a directory.

ICS 440 - Parallel and Distributed Algorithms
Summer 2022
@author Thomas Waterman

For this assignment, we were given a link to a specific webpage for the Environmental Protection Agency. This webpage contained the information for all trash sites where hazardous waste could be dropped off to be properly disposed of in the Unites States. On this page, a user could download a directory (folder) of all trash site locations, and their respective data. The largest directory contains all information for the Unites States, which contains about 450 files that are 10,000 rows of data, with 30+ columns as .csv files. This trash site data contained their names, cities, states, longitude, latitude, and etc. 

The goal of this assignmnet was for a user to be able to import the directory, and have the program parse through all input data, and determine the 8 closest trash site locations to the user's location. The user is prompted for their latitude, longitude, and the absolute filepath on their machine where the directory of EPA trash site data is currently stored. The program then uses multithreading to concurrently process the large voulme of data and return the results. 

Here is a link to the EPA website where this information can be downloaded for testing this program:
https://www.epa.gov/frs/epa-state-combined-csv-download-files
