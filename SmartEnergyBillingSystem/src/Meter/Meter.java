package Meter;

public class Meter {
    private double previousReading;
    private double currentReading;
    private double unitsConsumed;

    public Meter(double previousReading, double currentReading){
        setReadings(previousReading, currentReading);
    }

    public void setReadings(double previousReading, double currentReading){
        if(currentReading >= previousReading && currentReading >= 0 && previousReading>= 0){
            this.previousReading = previousReading;
            this.currentReading = currentReading;
            this.unitsConsumed = currentReading - previousReading;
        }else{
            System.out.println("Invalid Readings! Current reading must be greater than previous reading.");
            this.previousReading = 0;
            this.currentReading = 0;
            this.unitsConsumed = 0;
        }
    }

    public double getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(double previousReading) {
        this.previousReading = previousReading;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(double currentReading) {
        this.currentReading = currentReading;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public void setUnitsConsumed(double unitsConsumed) {
        this.unitsConsumed = unitsConsumed;
    }

    public void displayMeter(){
        System.out.println("----------Meter Readings----------");
        System.out.println("Previous Reading: " + previousReading);
        System.out.println("Current Reading: " + currentReading);
        System.out.println("Units Consumed: " + unitsConsumed);
    }
}
