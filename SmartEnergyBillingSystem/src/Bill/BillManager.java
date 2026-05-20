package Bill;

import java.io.*;
import java.time.LocalDate;

public class BillManager {
    private Bill[] bills;
    private int billCount;
    private static final String BILL_FILE ="bills.txt";
    private static final int MAX_BILLS = 1000;

    public BillManager(){
        bills = new Bill[MAX_BILLS];
        billCount = 0;
        loadBillsFromTextFile();
    }

    public void addBill(Bill bill){
        if(billCount >= MAX_BILLS){
            System.out.println("Cannot add more bills. Max limit reached.");
            return;
        }

        bills[billCount] = bill;
        billCount++;
        saveBillsToTextFile();
        System.out.println("Bill added successfully.");
    }

    public Bill findBillByCustomerID(int customerID){
        for(int i = 0; i < billCount; i++){
            if(bills[i].getCustomerID() == customerID && !bills[i].isPaid()){
                return bills[i];
            }
        }
        return null;
    }

    public Bill[] getBillsByMonth(int month, int year){
        Bill[] monthlyBills = new Bill[MAX_BILLS];
        int count = 0;

        for (int i = 0; i < billCount; i++){
            LocalDate billDate = bills[i].getBillDate();
            if(billDate.getMonthValue() == month && billDate.getYear() == year){
                monthlyBills[count++] = bills[i];
            }
        }

        Bill[] result = new Bill[count];
        for(int i = 0; i < count; i++){
            result[i] = monthlyBills[i];
        }
        return result;
    }

    public void displayAllBills(){
        if(billCount == 0){
            System.out.println("No bills found.");
            return;
        }
        System.out.println("-".repeat(10) + "All Bills" + "-".repeat(10));
        System.out.println("Total Bills: " + billCount);
        for(int i = 0; i < billCount; i++){
            System.out.println("Bill : "+ i+1);
            bills[i].displayBill();
        }
    }

    public void displayPendingBills(){
        boolean found = false;
        System.out.println("-".repeat(10) + "Pending Bills" + "-".repeat(10));
        for(int i = 0; i < billCount; i++){
            if(!bills[i].isPaid()){
                System.out.println("Bills [" + i + "]: ");
                bills[i].displayBill();
                found = true;
            }
        }
        if(!found){
            System.out.println("No pending bills;");
        }
    }

    private void saveBillsToTextFile(){
        try(PrintWriter writer = new PrintWriter(new FileWriter(BILL_FILE))){
            for(int i = 0; i < billCount; i++){
                writer.println(bills[i].toCSV());
            }
        }catch (IOException e){
            System.out.println("Error saving bill to the text file: " + e.getMessage());
        }
    }

    private void loadBillsFromTextFile(){
        File file = new File(BILL_FILE);
        if(!file.exists()){
            System.out.println("File not found!");
            return;
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(BILL_FILE))){
            String line;
            billCount = 0;
            while((line = reader.readLine()) != null && billCount < MAX_BILLS){
                Bill bill = Bill.fromCSV(line);
                if(bill != null){
                    bills[billCount] = bill;
                    billCount++;
                }
            }
        }catch (IOException e){
            System.out.println("Error loading bills: " + e.getMessage());
        }

    }

    public int getBillCount() {
        return billCount;
    }

    public void saveBills(){
        saveBillsToTextFile();
    }
}
