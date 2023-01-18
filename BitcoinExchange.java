import java.util.*;

class User {
    private String id; //must be unique
    private String name;
    private double bitcoinBalance;
    private double inrBalance;

    //initially user can enter with 0 bitcoin and inr balance
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.bitcoinBalance = 0;
        this.inrBalance = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBitcoinBalance() {
        return bitcoinBalance;
    }

    public double getInrBalance() {
        return inrBalance;
    }

    public void depositInr(double amount) {
        this.inrBalance += amount;
    }

    public void depositBitcoin(double amount) {
        this.bitcoinBalance += amount;
    }

    //checking if user has sufficient funds
    public boolean canOrder(String type, double price, double quantity) {
        
        double availableQuantity = getAvailableQuantity(type);

        if (availableQuantity < quantity) {
           
            System.out.println("Sorry, Order can't be placed, insuffiecient funds!");
            return false;
        } else {
            
            return true;
        }
    }
    public double getAvailableQuantity(String type) {

        if (type.equals("buy")) {
            return inrBalance;
        } else if (type.equals("sell")) {
            return bitcoinBalance;
        } else {
            return 0;
        }
    }
}

class Order {
    
    private User trader;
    private String type;
    private double amount;
    private double quantity;
    private double price;

    public Order(User trader, String type, double amount, double quantity, double price) {
        
        this.trader = trader;
        this.type = type;
        this.amount = amount;
        this.quantity = quantity;
        this.price = price;
    }

    public User getTrader() {
        return trader;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

   
    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTrader(User trader) {
        this.trader = trader;
    }

}

class OpenOrderBook {

    //the orders are put in a queue so that they are matched in a FIFO manner (first come first basis)
    private Queue<Order> buyOrders;
    private Queue<Order> sellOrders;

    public OpenOrderBook() {
        this.buyOrders = new LinkedList<>();
        this.sellOrders = new  LinkedList<>();
    }

    public void addOrder(Order order) {
        if (order.getType().equals("buy")) {
            buyOrders.add(order);
        } else {
            sellOrders.add(order);
        }
    }

    public void matchOrders(Order order, User user) {
        if (order.getType().equals("buy")) {
            while (!sellOrders.isEmpty() && sellOrders.peek().getPrice() == order.getPrice()) {
                Order sellOrder = sellOrders.poll();
                if (order.getQuantity() == sellOrder.getQuantity()) {
                    
                    //fulfill the order
                    user.depositBitcoin(order.getQuantity());
                    user.depositInr(-1*order.getAmount());//for the buyer

                    sellOrder.getTrader().depositBitcoin(-1*order.getQuantity());
                    sellOrder.getTrader().depositInr(order.getAmount());//for the seller
                    
                    // match is found, remove the order
                    order.setQuantity(0);
                    System.out.println("Order fulfilled successfully!");
                    break;
                } else if (order.getQuantity() < sellOrder.getQuantity()) {
                    // partial match is found, update the sell order
                    sellOrder.setQuantity(sellOrder.getQuantity() - order.getQuantity());
                    sellOrder.setAmount(sellOrder.getAmount() - order.getAmount());
                    //fulfill the order
                    user.depositBitcoin(order.getQuantity());
                    user.depositInr(-1*order.getAmount());//for the buyer

                    sellOrder.getTrader().depositBitcoin(-1*order.getQuantity());
                    sellOrder.getTrader().depositInr(order.getAmount());//for the seller

                    System.out.println("Order fulfilled !");

                    addOrder(sellOrder); // add remaining order to open order book
                    order.setQuantity(0);
                    break;
                } else {

                    // order is larger, update the order
                    order.setQuantity(order.getQuantity() - sellOrder.getQuantity());
                    order.setAmount(order.getAmount() - sellOrder.getAmount());

                    //fulfill the order
                    user.depositBitcoin(order.getQuantity());
                    user.depositInr(-1*order.getAmount());//for the buyer

                    sellOrder.getTrader().depositBitcoin(-1*order.getQuantity());
                    sellOrder.getTrader().depositInr(order.getAmount());//for the seller
                    
                    System.out.println("Order fulfilled partially!");

                }
            }
            if (order.getQuantity() > 0) {
                System.out.println("Order sent to open order book!");
                addOrder(order);
            }
        } else {
            while (!buyOrders.isEmpty() && buyOrders.peek().getPrice() == order.getPrice()) {
                Order buyOrder = buyOrders.poll();
                if (order.getQuantity() == buyOrder.getQuantity()) {

                    //fulfill the order
                    user.depositBitcoin(-1*order.getQuantity());
                    user.depositInr(order.getAmount());//for the seller

                    buyOrder.getTrader().depositBitcoin(order.getQuantity());
                    buyOrder.getTrader().depositInr(-1*order.getAmount());//for the buyer


                    // match is found, remove the order
                    order.setQuantity(0);
                    System.out.println("Order fulfilled successfully!");
                    break;
                } else if (order.getQuantity() < buyOrder.getQuantity()) {
                    // partial match is found, update the buy order
                    buyOrder.setQuantity(buyOrder.getQuantity() - order.getQuantity());
                    buyOrder.setAmount(buyOrder.getAmount() - order.getAmount());
                    //fulfill the order
                    user.depositBitcoin(-1*order.getQuantity());
                    user.depositInr(order.getAmount());//for the seller

                    buyOrder.getTrader().depositBitcoin(order.getQuantity());
                    buyOrder.getTrader().depositInr(-1*order.getAmount());//for the buyer

                    System.out.println("Order fulfilled!");
        
                    addOrder(buyOrder); // add remaining order to open order book
                    order.setQuantity(0);
                    break;
                } else {

                    // order is larger, update the order
                    order.setQuantity(order.getQuantity() - buyOrder.getQuantity());
                    order.setAmount(order.getAmount() - buyOrder.getAmount());

                    //fulfill the order
                    user.depositBitcoin(-1*order.getQuantity());
                    user.depositInr(order.getAmount());//for the seller

                    buyOrder.getTrader().depositBitcoin(order.getQuantity());
                    buyOrder.getTrader().depositInr(-1*order.getAmount());//for the buyer
                    

                    System.out.println("Order fulfilled partially!");
                    
                }
            }
            if (order.getQuantity() > 0) {
                System.out.println("Order sent to open order book!");
                addOrder(order);
            }
        }
    }
    
    public Queue<Order> getBuyOrders() {
        return buyOrders;
    }

    public Queue<Order> getSellOrders() {
        return sellOrders;
    }
}

class BitcoinExchange {
    private static Map<String, User> users;// mapping user with id
    private static OpenOrderBook openOrderBook;
    private static User user;

    public static void main(String[] args) {

        users = new HashMap<>();
        openOrderBook = new OpenOrderBook();

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Add User");
            System.out.println("2. Deposit INR or Bitcoin");
            System.out.println("3. Place Order(buy or sell)");
            System.out.println("4. View Open Orders");
            System.out.println("5. View User Account Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    deposit();
                    break;
                    case 3:
                    placeOrder();
                    break;
                case 4:
                    viewOpenOrders();
                    break;
                case 5:
                    viewUserAccountBalance();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private static void addUser()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Name : ");
        String name = sc.nextLine();

        boolean notTaken = false;

        String id = "";
        System.out.println("Enter User Id : ");

        while(!notTaken)
        {
            id = sc.nextLine();
            if(!users.containsKey(id))
                notTaken = true;
            else
                System.out.println("Id already taken!, please enter some other User Id : ");
        }

        user = new User(id, name);
        users.put(id, user);
        System.out.println("User added successfully!");
    }

    private static void deposit()
    {
        Scanner sc = new Scanner(System.in);
 
        System.out.println("Enter User Id : ");

        String id = sc.nextLine();
        user = users.get(id);

        if (user == null) 
        {
            System.out.println("Invalid user id!");
        } 
        else
        {
        System.out.println("Enter INR : ");
        double inr = sc.nextDouble();
        System.out.println("Enter Bitcoin : ");
        double btc = sc.nextDouble();

        user = users.get(id);
        user.depositInr(inr);
        user.depositBitcoin(btc);
        System.out.println("Deposited successfully!");
        }
    }

    private static void placeOrder() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter user id: ");
        String id = sc.nextLine();
        user = users.get(id);
        if (user == null) {
            System.out.println("Invalid user id!");
        }
        
        else {
            System.out.print("Enter order type (buy/sell): ");
            String type = sc.nextLine();
            System.out.print("Enter price: ");
            double price = sc.nextDouble();
            System.out.print("Enter quantity: ");
            double quantity = sc.nextDouble();
            sc.nextLine();

            Order order = new Order(user, type, (double)price*(double)quantity, quantity, price);

            boolean success = user.canOrder(type, (double)price*(double)quantity, quantity);
            if (success) {
                openOrderBook.matchOrders(order, user);
            }
        }
                
            
                
    }
            
    private static void viewOpenOrders() 
    {
        // Create new lists for sorting
        List<Order> sortedBuyOrders = new ArrayList<Order>();
        List<Order> sortedSellOrders = new ArrayList<Order>();

            
        // Add buy orders to the new list
        for (Order order : openOrderBook.getBuyOrders()) 
        {
            sortedBuyOrders.add(order);
        }
            
        // Add sell orders to the new list
        for (Order order : openOrderBook.getSellOrders()) 
        {
            sortedSellOrders.add(order);
        }
            
        // Sort the new lists
        Collections.sort(sortedBuyOrders, (o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        Collections.sort(sortedSellOrders, (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice()));
            
        System.out.println("Buy Orders (Descending Order of Price):");
        if(sortedBuyOrders.isEmpty())
            System.out.println("No Buy Orders as of now!");
        else
        {
        for (Order order : sortedBuyOrders) {
            System.out.println(order.getQuantity() + "BTC with " + order.getAmount());
        }
        }
        

        System.out.println("Sell Orders (Ascending Order of Price):");
        if(sortedSellOrders.isEmpty())
            System.out.println("No Sell Orders as of now!");
        else
        {
        for (Order order : sortedSellOrders) {
            System.out.println(order.getQuantity() + "BTC with " + order.getAmount());
        }
        }
    }
            
    
    private static void viewUserAccountBalance() 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user id: ");
        String id = sc.nextLine();
            
        user = users.get(id);
        if (user == null) {
            System.out.println("Invalid user id!");
        } else {
            System.out.println("Bitcoin Balance: " + user.getBitcoinBalance());
            System.out.println("INR Balance: " + user.getInrBalance());
        }
    }
}

   
