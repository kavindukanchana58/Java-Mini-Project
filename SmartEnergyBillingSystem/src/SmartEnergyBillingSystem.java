import Bill.Bill;
import Bill.BillManager;
import Customer.Customer;
import Customer.CustomerManager;
import Meter.Meter;
import Report.Report;
import java.util.Scanner;

public class SmartEnergyBillingSystem {
    private static CustomerManager customerManager;
    private static BillManager billManager;
    private static Report reportGenerator;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeSystem();
        displayMenu();
    }

    private static void initializeSystem(){
        customerManager = new CustomerManager();
        billManager = new BillManager();
        reportGenerator = new Report(billManager);
        scanner = new Scanner(System.in);

        System.out.println("Smart Energy Billing System");
        System.out.println("System initialized successfully...");
        System.out.println("Customers loaded: "+ customerManager.getCustomerCount());
        System.out.println("Bills loaded: "+ billManager.getBillCount());
    }

    private static void displayMenu(){
        while(true){
            System.out.println("-".repeat(20) + "Main Menu" + "-".repeat(20));
            System.out.println("1. Customer Management");
            System.out.println("2. Generate Electricity Bills");
            System.out.println("3. View Bills");
            System.out.println("4. Make Payment");
            System.out.println("5. Report and Analytics");
            System.out.println("6. System Statistics");
            System.out.println("7. Exit");
            System.out.print("Enter your choice(1-7): ");
            int choice = getValidIntegerInput();

            switch(choice){
                case 1:
                    customerManagementMenu();
                    break;
                case 2:
                    generateBill();
                    break;
                case 3:
                    viewBillsMenu();
                    break;
                case 4:
                    makePayment();
                    break;
                case 5:
                    reportsMenu();
                    break;
                case 6:
                    showStatistics();
                    break;
                case 7:
                    exitSystem();
                    break;
                default:
                    System.out.println("Invalid choice :(");
            }
        }
    }

    private static void exitSystem(){
        System.out.println("Thank you for using Smart Energy Billing System.");
        System.out.println("        Data Saved Successfully!");
        System.out.println("                Good Bye");
    }

    private static void customerManagementMenu(){
        while(true) {
            System.out.println("-".repeat(20) + "Customer Management" + "-".repeat(20));
            System.out.println("1. Add New Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Remove Customer");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice (1-5): ");
            int choice = getValidIntegerInput();

            switch (choice){
                case 1:
                    addCustomer();
                    break;
                case 2:
                    customerManager.displayAllCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    removeCustomer();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice :(");
            }
        }
    }

    private static void addCustomer(){
        System.out.println("-".repeat(20) + "ADD NEW CUSTOMER" + "-".repeat(20));
        scanner.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Connection Type(Domestic/Commercial): ");
        String connectionType = scanner.nextLine();

        if(!connectionType.equalsIgnoreCase("Domestic") &&
                !connectionType.equalsIgnoreCase("Commercial")){
            System.out.println("Invalid Connection Type:Redirecting to Domestic...");
            connectionType = "Domestic";
        }

        customerManager.addCustomer(name, address, connectionType);
    }

    private static void updateCustomer(){
        System.out.println("-".repeat(20) + "UPDATE CUSTOMER" + "-".repeat(20));
        System.out.print("Enter customer ID to update: ");
        int customerID = scanner.nextInt();
        Customer customer = customerManager.findCustomerByID(customerID);

        if(customer == null){
            System.out.println("Customer Not Found");
            return;
        }

        scanner.nextLine();

        System.out.println("Current Details: ");
        customer.displayCustomer();

        System.out.println("Enter new details(press Enter to keep current details)...");
        System.out.print("New Name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()){
            name = customer.getName();
        }

        System.out.print("New Address: ");
        String address = scanner.nextLine();
        if(address.isEmpty()){
            address = customer.getAddress();
        }

        System.out.print("New Connection Type:");
        String connectionType = scanner.nextLine();
        if(connectionType.isEmpty()){
            connectionType = customer.getConnectionType();
        }

        customerManager.updateCustomer(customerID, name, address, connectionType);
    }

    private static void removeCustomer(){
        System.out.println("-".repeat(20) + "REMOVE CUSTOMER" + "-".repeat(20));
        System.out.print("Enter Customer ID to remove: ");
        int customerID = scanner.nextInt();
        System.out.print("Are You Sure? (Y/N): ");
        String confirmation = scanner.next().toLowerCase();
        if(confirmation.equals("y")){
            customerManager.removeCustomer(customerID);
        }else{
            System.out.println("Operation Cancelled.");
        }
    }

    private static void generateBill(){
        System.out.println("-".repeat(20) + "GENERATE ELECTRICITY BILL" + "-".repeat(20));
        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        Customer customer = customerManager.findCustomerByID(customerID);
        if(customer == null){
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Customer Details: ");
        customer.displayCustomer();

        System.out.println("Enter Meter Readings: ");
        System.out.print("\tEnter Previous Reading(units): ");
        double previousReading = scanner.nextDouble();
        System.out.print("\tEnter Current Meter Reading(units): ");
        double currentReading = scanner.nextDouble();

        Meter meter = new Meter(previousReading, currentReading);
        meter.displayMeter();

        Tariff tariff;
        if(customer.getConnectionType().equalsIgnoreCase("Domestic")){
            tariff = new DomesticTariff();
        }else{
            tariff = new CommercialTariff();
        }

        double units = meter.getUnitsConsumed();
        double charge = tariff.calculateCharge(units);

        System.out.println("-".repeat(20) + "TARIFF CALCULATION" + "-".repeat(20));
        System.out.printf("Units Consumed: %.2f units",units);
        System.out.println();
        System.out.printf("Total Charge: Rs.%.2f", charge);
        System.out.println();
        System.out.println("-".repeat(50));

        System.out.print("Generate Bill?(Y/N): ");
        String confirm = scanner.next().toLowerCase();
        if(confirm.equals("y")){
            Bill bill = new Bill(customerID,units,charge);
            billManager.addBill(bill);
            System.out.println("Bill generated successfully!");
            bill.displayBill();
        }else{
            System.out.println("Bill generation failed!");
        }
    }

    private static void viewBillsMenu(){
        while(true){
            System.out.println("-".repeat(20) + "VIEW BILLS" + "-".repeat(20));
            System.out.println("1. View All Bills");
            System.out.println("2. View Pending Bills");
            System.out.println("3. View Bill by Customer ID");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice (1-4): ");
            int choice = getValidIntegerInput();

            switch (choice){
                case 1:
                    billManager.displayAllBills();
                    break;
                case 2:
                    billManager.displayPendingBills();
                    break;
                case 3:
                    viewBillsByCustomer();
                    break;
                case 4:
                    return;
                default:
                    System.out.print("Invalid choice. Please enter (1-4)");
            }
        }
    }

    private static void viewBillsByCustomer(){
        System.out.println("-".repeat(20) + "VIEW BILLS BY CUSTOMER" + "-".repeat(20));
        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        Bill bill = billManager.findBillByCustomerID(customerID);
        if(bill != null){
            bill.displayBill();
        }else{
            System.out.println("No pending Bills under this customer.");
        }
    }

    private static void makePayment(){
        System.out.println("-".repeat(20) + "MAKE PAYMENT" + "-".repeat(20));
        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();

        Bill bill = billManager.findBillByCustomerID(customerID);
        if(bill == null){
            System.out.println("No pending Bills under this customer.");
            return;
        }

        System.out.println("Bill Details:");
        bill.displayBill();
        System.out.print("Proceed with payment?(Y/N): ");
        String confirm = scanner.next().toLowerCase();
        if(confirm.equals("y")){
            bill.markAsPaid();
            billManager.saveBills();
            System.out.println("Payment Successful!Bill Marked as Paid.");
            bill.displayBill();
        }else{
            System.out.println("Payment Cancelled");
        }
    }

    private static void reportsMenu(){
        while(true){
            System.out.println("-".repeat(20) + "REPORTS AND ANALYTICS" + "-".repeat(20));
            System.out.println("1. Generate Monthly Report");
            System.out.println("2. Back to Main Menu");
            System.out.print("Enter your choice (1-3): ");
            int choice = getValidIntegerInput();

            switch (choice) {
                case 1:
                    generateMonthlyReport();
                    break;
                case 2:
                    return;
                default:
                    System.out.print("Invalid choice. Please enter (1-2)");
            }
        }
    }

    private static void generateMonthlyReport(){
        System.out.println("-".repeat(20) + "GENERATE MONTHLY REPORT" + "-".repeat(20));
        System.out.print("Enter month(1-12): ");
        int month = getValidIntegerInput();

        if(month < 1 || month > 12){
            System.out.print("Invalid Month. Please enter 1-12");
            return;
        }

        System.out.println("Enter year(eg: 2025): ");
        int year = getValidIntegerInput();

        reportGenerator.generateMonthlyReport(month,year);
    }

    private static void showStatistics(){
        System.out.println("-".repeat(20) + "SYSTEM STATISTICS" + "-".repeat(20));
        System.out.println("Customers: " + customerManager.getCustomerCount() + " registered");
        System.out.println("Bills: " + billManager.getBillCount() + " generated");

        System.out.println("Data Files: ");
        System.out.println("\tCustomer Database File - customer.txt");
        System.out.println("\tBill Records File - bill.txt");
    }

    private static int getValidIntegerInput(){
        while(true){
            try{
                return scanner.nextInt();
            }catch(Exception e){
                System.out.print("Invalid Input.Please Enter a number: ");
                scanner.next();
            }
        }
    }
}
