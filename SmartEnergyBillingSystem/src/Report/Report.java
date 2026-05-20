package Report;

import Bill.Bill;
import Bill.BillManager;

public class Report{
    private BillManager billManager;

    public Report(BillManager billManager){
        this.billManager = billManager;
    }

    public void generateMonthlyReport(int month, int year){
        Bill[] monthlyBills = billManager.getBillsByMonth(month, year);
        if(monthlyBills.length == 0){
            System.out.printf("No bills found!");
            return;
        }

        double totalRevenue = 0;
        double totalUnits = 0;
        int paidBills = 0;
        int pendingBills = 0;

        System.out.println("-".repeat(20) + "Monthly Report" + "-".repeat(20));

        for(Bill bill: monthlyBills){
            totalRevenue += bill.getFinalAmount();
            totalUnits += bill.getUnitsConsumed();

            if(bill.isPaid()){
                paidBills++;
            }else{
                pendingBills++;
            }
        }

        System.out.printf("Total bills generated: %d", monthlyBills.length);
        System.out.println();
        System.out.printf("paid Bills: %d", paidBills);
        System.out.println();
        System.out.printf("Pending Bills: %d", pendingBills);
        System.out.println();
        System.out.printf("Total Units Consumed: %.2f", totalUnits);
        System.out.println();
        System.out.printf("Total Revenue: Rs. %.2f", totalRevenue);
        System.out.println();
        System.out.printf("Average Bill Amount: Rs. %.2f", totalRevenue/monthlyBills.length);
        System.out.println();
        System.out.println("-".repeat(50));
    }

}
