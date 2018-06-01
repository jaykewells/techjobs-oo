package org.launchcode.models;

/**
 * Created by LaunchCode
 */
public class Job {

    private int id;
    private static int nextId = 1;

    private String name;
    private Employer employer;
    private Location location;
    private PositionType positionType;
    private CoreCompetency coreCompetency;

    public Job() {
        id = nextId;
        nextId++;
    }

    public Job(String aName, Employer aEmployer, Location aLocation,
               PositionType aPositionType, CoreCompetency aSkill) {

        this();

        this.name = aName;
        this.employer = aEmployer;
        this.location = aLocation;
        this.positionType = aPositionType;
        this.coreCompetency = aSkill;

    }

    public String getName() {
        return name;
    }

    public void setName(String aname) {
        this.name = aname;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer aemployer) {
        this.employer = aemployer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location alocation) {
        this.location = alocation;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType apositionType) {
        this.positionType = apositionType;
    }

    public CoreCompetency getCoreCompetency() {
        return coreCompetency;
    }

    public void setCoreCompetency(CoreCompetency acoreCompetency) {
        this.coreCompetency = acoreCompetency;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        return id == job.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
