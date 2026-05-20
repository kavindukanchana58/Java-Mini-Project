package Customer;

public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String connectionType;

    Customer(int customerID, String name){
        this.customerID = customerID;
        this.name = name;
    }

    Customer(int customerID, String name, String address, String connectionType){
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.connectionType = connectionType;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void displayCustomer(){
        System.out.println("----------Customer Informations----------");
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Connection Type: " + connectionType);
    }

    public String toCSV(){
        return customerID +  ", " + name + ", " + address + ", " + connectionType;
    }

    public static Customer fromCSV(String csvLine){
        String[] parts =  csvLine.split(", ");
        if (parts.length != 4){
            return null;
        }
        try{
            int id =  Integer.parseInt(parts[0]);
            return new Customer(id, parts[1], parts[2], parts[3]);
        }catch(NumberFormatException e){
            return null;
        }
    }
}
