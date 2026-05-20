package Bill;

import Utility.DateUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*Methods Used:
    applyPenalty
    markAsPaid
    getFinalBill
    toCSV
    fromCSV
    getters
 */

public class Bill {
    private int customerID;
    private double unitsConsumed;
    private double totalAmount;
    private LocalDate billDate;
    private LocalDate dueDate;
    private double penalty;
    private boolean isPaid;

    public Bill(int customerID, double unitsConsumed, double totalAmount){
        this.customerID = customerID;
        this.unitsConsumed = unitsConsumed;
        this.totalAmount = totalAmount;
        this.billDate = LocalDate.now();
        this.dueDate = billDate.plusDays(30);
        this.penalty = 0.0;
        this.isPaid = false;
    }
    public void applyPenalty(LocalDate paymentDate){
        if(!isPaid && paymentDate.isAfter(dueDate)){
            long daysLate = DateUtility.daysBetween(dueDate, paymentDate);
            penalty = daysLate * 5.0;
        }
    }

    public void markAsPaid(){
        this.isPaid = true;
        applyPenalty(LocalDate.now());
    }

    public double getFinalAmount(){
        return totalAmount + penalty;
    }

    public void displayBill(){
        System.out.println("----------Electricity Bill----------");
        System.out.println("Customer ID: " + customerID);
        System.out.println("units Consumed: " + unitsConsumed);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("Bill Date: " + billDate.format(formatter));
        System.out.println("Due Date: " + dueDate.format(formatter));

        boolean isOverdue = LocalDate.now().isAfter(dueDate);
        System.out.println("Status: " + (isOverdue ? "Overdue!": "NotDue"));
        System.out.printf("Base Amount: Rs. %.2f\n", totalAmount);

        if(penalty > 0){
            System.out.printf("Late Payment Charge: Rs. %2f\n", getFinalAmount());
        }
        System.out.println("Payment Status: " + (isPaid ? "Paid!" : "Pending!"));
        System.out.println("-".repeat(20));
    }

    public String toCSV(){
        return customerID + ", " + unitsConsumed + ", " + totalAmount + ", " +
                billDate + ", " + dueDate + ", " + penalty + ", " + isPaid;
    }

    public static Bill fromCSV(String csvLine){
        String[] parts = csvLine.split(",");
        if(parts.length != 7){
            return null;
        }

        try{
            int customerID = Integer.parseInt(parts[0]);
            double units = Double.parseDouble(parts[1]);
            double amount = Double.parseDouble(parts[2]);
            LocalDate billDate = LocalDate.parse(parts[3]);
            LocalDate dueDate = LocalDate.parse(parts[4]);
            double penalty = Double.parseDouble(parts[5]);
            boolean isPaid = Boolean.parseBoolean(parts[6]);

            Bill bill = new Bill(customerID, units, amount);
            bill.billDate = billDate;
            bill.dueDate = dueDate;
            bill.penalty = penalty;
            bill.isPaid = isPaid;
            return bill;
        }catch(Exception e){
                return null;
        }


    }
    public int getCustomerID() {
        return customerID;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getPenalty() {
        return penalty;
    }

    public boolean isPaid() {
        return isPaid;
    }


}