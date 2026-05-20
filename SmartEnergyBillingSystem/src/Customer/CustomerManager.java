package Customer;

import java.io.*;

public class CustomerManager {
    private Customer[] customers;
    private int customerCount;
    private static final String CUSTOMER_FILE = "customers.txt";
    private static final int MAX_CUSTOMERS = 1000;

    public CustomerManager(){
        customers = new Customer[MAX_CUSTOMERS];
        customerCount = 0;
        loadCustomers();
    }

    public void addCustomer(String name, String address, String connectionType) {
        if (customerCount >= MAX_CUSTOMERS) {
            System.out.println("Can't add more customers. Maximum number reached.");
            return;
        }
        int customerID = 1001 + customerCount;
        Customer customer = new Customer(customerID, name, address, connectionType);
        customers[customerCount] = customer;
        customerCount++;
        saveCustomers();
        System.out.println("Customer added successfully!");
        System.out.println("Customer ID: " + customerID);
    }

    public Customer findCustomerByID(int customerID){
        for(int i = 0; i < customerCount; i++){
            if(customers[i].getCustomerID() == customerID){
                return customers[i];
            }
        }
        return null;
    }

    public void displayAllCustomers(){
        if(customerCount == 0) {
            System.out.println("No customers to display!");
            return;
        }

        System.out.println("-".repeat(20) + "All Customers(" + customerCount + ")" + "-".repeat(20));
        for(int i = 0; i < customerCount; i++){
            System.out.println("-".repeat(50));
            System.out.println("Customer " + (i+1));
            System.out.println("ID: " + customers[i].getCustomerID());
            System.out.println("Name: " + customers[i].getName());
            System.out.println("Address: " + customers[i].getAddress());
            System.out.println("Connection Type: " + customers[i].getConnectionType());
            System.out.println("-".repeat(50));
        }
    }

    public void updateCustomer(int customerID, String name, String address, String connectionType){
        Customer customer = findCustomerByID(customerID);
        if(customer!=null){
            customer.setName(name);
            customer.setAddress(address);
            customer.setConnectionType(connectionType);
            saveCustomers();
            System.out.println("Customer updated successfully!");
        }else{
            System.out.println("Customer not found.");
        }
    }

    public void removeCustomer(int customerID){
        int index = -1;
        for(int i = 0; i < customerCount; i++){
            if(customers[i].getCustomerID() == customerID){
                index = i;
                break;
            }
        }
        if(index != -1){
            for(int i = index; i < customerCount - 1; i++){
                customers[i] = customers[i+1];
            }
            customers[customerCount - 1] = null;
            customerCount--;
            saveCustomers();
            System.out.println("Customer removed successfully.");
        }else{
            System.out.println("Customer not found.");
        }
    }

    private void saveCustomers(){
        try(PrintWriter printWriter = new PrintWriter(new FileWriter(CUSTOMER_FILE))){
            for(int i = 0; i < customerCount; i++){
                printWriter.println(customers[i].toCSV());
            }
        }catch (IOException e){
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    private void loadCustomers(){
        File file = new File(CUSTOMER_FILE);
        if(!file.exists()){
            return;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))){
            String line;
            customerCount = 0;

            while((line = reader.readLine()) != null && customerCount < MAX_CUSTOMERS){
                Customer customer = Customer.fromCSV(line);
                if(customer != null){
                    customers[customerCount] = customer;
                    customerCount++;
                }
            }
        }catch(IOException e){
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public int getCustomerCount(){
        return customerCount;
    }
}
