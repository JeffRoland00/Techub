import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Drug {
    private String name;
    private String dosage;
    private Date expiryDate;
    private int quantity;
    private List<String> suppliers;

    // Constructor
    public Drug(String name, String dosage, Date expiryDate, int quantity) {
        this.name = name;
        this.dosage = dosage;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.suppliers = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    // ...

    // Add a supplier to the drug
    public void addSupplier(String supplier) {
        suppliers.add(supplier);
    }

    // Display drug details
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Dosage: " + dosage);
        System.out.println("Expiry Date: " + expiryDate);
        System.out.println("Quantity: " + quantity);
        System.out.println("Suppliers: " + suppliers);
    }
}

class Purchase {
    private Drug drug;
    private int quantity;
    private Date purchaseDate;
    private String buyer;

    // Constructor
    public Purchase(Drug drug, int quantity, String buyer) {
        this.drug = drug;
        this.quantity = quantity;
        this.buyer = buyer;
        this.purchaseDate = new Date();
    }

    // Display purchase details
    public void displayDetails() {
        System.out.println("Drug: " + drug.getName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + calculatePrice()); // Add a method to calculate price if needed
        System.out.println("Purchase Date: " + purchaseDate);
        System.out.println("Buyer: " + buyer);
    }
}

class PharmacyManagementSystem {
    private List<Drug> drugs;
    private List<Purchase> purchaseHistory;

    // Constructor
    public PharmacyManagementSystem() {
        drugs = new ArrayList<>();
        purchaseHistory = new ArrayList<>();
    }

    // Add a drug to the inventory
    public void addDrug(String name, String dosage, Date expiryDate, int quantity) {
        Drug drug = new Drug(name, dosage, expiryDate, quantity);
        drugs.add(drug);
    }

    // Search for a drug by name
    public void searchDrug(String name) {
        for (Drug drug : drugs) {
            if (drug.getName().equalsIgnoreCase(name)) {
                drug.displayDetails();
                return;
            }
        }
        System.out.println("Drug not found!");
    }

    // View all drugs and their suppliers
    public void viewAllDrugs() {
        if (drugs.isEmpty()) {
            System.out.println("No drugs in the inventory!");
            return;
        }

        for (Drug drug : drugs) {
            drug.displayDetails();
            System.out.println();
        }
    }

    // Add a supplier to a drug
    public void addSupplierToDrug(String drugName, String supplier) {
        Drug drug = getDrugByName(drugName);
        if (drug != null) {
            drug.addSupplier(supplier);
            System.out.println("Supplier added successfully!");
        } else {
            System.out.println("Drug not found!");
        }
    }

    // Record a purchase
    public void recordPurchase(String drugName, int quantity, String buyer) {
        Drug drug = getDrugByName(drugName);
        if (drug != null) {
            if (drug.getQuantity() >= quantity) {
                drug.setQuantity(drug.getQuantity() - quantity);
                Purchase purchase = new Purchase(drug, quantity, buyer);
                purchaseHistory.add(purchase);
                System.out.println("Purchase recorded successfully!");
            } else {
                System.out.println("Insufficient quantity available!");
            }
        } else {
            System.out.println("Drug not found!");
        }
    }

    // View purchase history for a drug
    public void viewPurchaseHistory(String drugName) {
        List<Purchase> drugPurchaseHistory = new ArrayList<>();
        for (Purchase purchase : purchaseHistory) {
            if (purchase.getDrug().getName().equalsIgnoreCase(drugName)) {
                drugPurchaseHistory.add(purchase);
            }
        }

        if (drugPurchaseHistory.isEmpty()) {
            System.out.println("No purchase history found for the drug!");
            return;
        }

        for (Purchase purchase : drugPurchaseHistory) {
            purchase.displayDetails();
            System.out.println();
        }
    }

    // Helper method to get a drug by its name
    private Drug getDrugByName(String drugName) {
        for (Drug drug : drugs) {
            if (drug.getName().equalsIgnoreCase(drugName)) {
                return drug;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        PharmacyManagementSystem pharmacy = new PharmacyManagementSystem();

        // Adding drugs
        pharmacy.addDrug("Drug A", "5mg", parseDate("2023-08-31"), 100);
        pharmacy.addDrug("Drug B", "10mg", parseDate("2023-09-30"), 50);

        // Adding suppliers
        pharmacy.addSupplierToDrug("Drug A", "Supplier X");
        pharmacy.addSupplierToDrug("Drug A", "Supplier Y");
        pharmacy.addSupplierToDrug("Drug B", "Supplier Z");

        // Searching for a drug
        pharmacy.searchDrug("Drug A");

        // Viewing all drugs
        pharmacy.viewAllDrugs();

        // Recording a purchase
        pharmacy.recordPurchase("Drug A", 10, "Buyer 1");

        // Viewing purchase history
        pharmacy.viewPurchaseHistory("Drug A");
    }

    // Helper method to parse a date string
    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false); // Enforce strict date parsing
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format! Please provide a date in yyyy-MM-dd format.");
        }
        return null;
    }
}

