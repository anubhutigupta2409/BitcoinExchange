# BitcoinExchange  
  
## Design  

* A `User` class that has the following properties: `id`, `name`, `bitcoinBalance`, `inrBalance`. The class should have methods to deposit bitcoin and INR, place buy or sell orders, and check the balance of bitcoin and INR. 

* An `Order` class that has the following properties: `type`, `price`, `quantity`. The `price` feature depends on the person making a sell/buy `order` and can be calculated like this :-  
```
price = Amount/Number of bitcoins;
```

* An `OpenOrderBook` class that has two lists of orders, one for buy orders and one for sell orders. The class should have methods to add orders, match orders, and view all open orders.  

* A `BitcoinExchange` class that acts as the main entry point for the application. It should have a menu-driven console interface that allows users to perform various operations such as adding a new user, depositing bitcoin and INR, placing buy or sell orders, and viewing open orders, user-wise account balance of bitcoin and INR.  

* The `placeOrder` method in the User class should check for available quantity and requested quantity. If the available quantity is less than the requested quantity, the remaining quantity should be added to the open order book.  

* The `matchOrders` method in the `OpenOrderBook` class should match orders based on the price and order type, and remove the matched orders from the open order book.  

* The `addOrder` method in the `OpenOrderBook` class should be able to handle the edge cases of partial orders and one order matching with multiple open orders.  

* The application should have APIs for adding a new user, depositing bitcoin and INR, placing buy or sell orders, and viewing open orders, user-wise account balance of bitcoin and INR.  

* The response time for the application should be maximum 3 seconds and should be able to handle a maximum of 20,000 concurrent place order requests, and 5,000 concurrent deposit requests.  

* The application should be able to handle a maximum of 1 million open orders without any persistent storage.  

* The application can be a simple console application and does not require REST APIs.
