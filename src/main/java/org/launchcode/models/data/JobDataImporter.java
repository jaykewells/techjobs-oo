package org.launchcode.models.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.launchcode.models.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobDataImporter {

    private static final String DATA_FILE = "job_data.csv";
    private static boolean isDataLoaded = false;

    /**
     * Read in data from a CSV file and store it in a list
     */
    static void loadData(JobData jobData) {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Resource resource = new ClassPathResource(DATA_FILE);
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            // Put the records into a more friendly format
            for (CSVRecord record : records) {

                String empStr = record.get("employer");
                String locStr = record.get("location");
                String coreCompStr = record.get("core competency");
                String posTypeStr = record.get("position type");

                //This section creates the appropriate JobField object for each individual info type.
                Employer emp = jobData.getEmployers().findByValue(empStr);
                if (emp == null) {
                    emp = new Employer(empStr);
                    jobData.getEmployers().add(emp);
                }
                /**
                 * Process for these methods:
                 * Employer emp = ....
                 * This line attempts to find a match in the "Database" or adds it if it doesn't exist.
                 *  if emp == null {...}
                 *  This block recreates the object if it doesn't exist, and then adds it to the Employers list.
                 */

                Location loc = jobData.getLocations().findByValue(locStr);
                if (loc == null) {
                    loc = new Location(locStr);
                    jobData.getLocations().add(loc);
                }

                PositionType posType = jobData.getPositionTypes().findByValue(posTypeStr);
                if (posType == null) {
                    posType = new PositionType(posTypeStr);
                    jobData.getPositionTypes().add(posType);
                }

                CoreCompetency coreComp = jobData.getCoreCompetencies().findByValue(coreCompStr);
                if (coreComp == null) {
                    coreComp = new CoreCompetency(coreCompStr);
                    jobData.getCoreCompetencies().add(coreComp);
                }

                //Finally, after checking if each field exists or not, and adding it, creates a Job object with the data.
                Job newJob = new Job(record.get("name"), emp, loc, posType, coreComp);

                jobData.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
