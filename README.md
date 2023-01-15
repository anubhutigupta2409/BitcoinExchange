# BitcoinExchange  
  
## Design  

* A `User` class that has the following properties: `id`, `name`, `bitcoinBalance`, `inrBalance`. The class should have methods to :
  * deposit bitcoin and INR, 
  * check fund availability for buy or sell orders, 
  * and check the balance of bitcoin and INR. 

* An `Order` class that has the following properties: `trader`, `type`, `price`, `amount`, `quantity`. The `price` feature here, represents the price of the bitcoin set by the user, and depends on the person making a sell/buy `order` and can be calculated like this :-  
```
price = Amount/Quality;
```

* An `OpenOrderBook` class that has two queues (to implement FIFO functionality) of orders, one for buy orders and one for sell orders. The class should have methods to add orders, match orders.  

* A `BitcoinExchange` class that acts as the main entry point for the application. It should have a menu-driven console interface that allows users to perform various operations such as :
  * adding a new user, 
  * depositing bitcoin and INR, 
  * placing buy or sell orders,  
  * viewing open orders, 
  * and user-wise account balance of bitcoin and INR.  

* The `canOrder` method in the User class should check for available quantity and requested quantity. If the available quantity is less than the requested quantity, then the user should be prompted with the error of "Insufficient Balance".  

* The `matchOrders` method in the `OpenOrderBook` class should :  
    * match orders based on the price and order type, fufill them(transfer INR and bitcoin) and remove the matched orders from the open order book. 
    * If the price and type could not be matched the order is moved to open order book. 
    * If price and order type matches and the quantity could not be matched, the order should be partially fulfilled and open order book is iterated over so that the       remaining order can be fulfilled by other potential orders, in the end the remaining should go into open order book with the same price, but remaining quantity       and amount.

* The `addOrder` method in the `OpenOrderBook` class adds order to the open order book in case of partial or unmatched orders.
* The application should have APIs for adding a new user, depositing bitcoin and INR, placing buy or sell orders, and viewing open orders, user-wise account balance of bitcoin and INR.  

* The response time for the application should be maximum 3 seconds and should be able to handle a maximum of 20,000 concurrent place order requests, and 5,000 concurrent deposit requests.  

* The application should be able to handle a maximum of 1 million open orders without any persistent storage.  

* The application can be a simple console application and does not require REST APIs.  

## Demo  

### The following scenario is being demonstrated in the following output screens :-  

| Users & Id  | Orders placed by them | Bitcoin Price  | Order Status | Order Comments |
| ------------- | ------------- | ------------- | ------------- | ------------- | 
| Anubhuti , 123| Buy 30 Bitcoins with 150 INR  | 5 INR/BTC | Partially fulfilled | 5 BTC bought from Akanksha, 10 BTC bought from Manju ; 15 BTC in 75 INR moved to Open Order Book |
| Akanksha , 1234| Sell 5 Bitcoins with 25 INR | 5 INR/BTC  | Fulfilled | Complete order sold to Anubhuti |
| Manju , 12| Sell 10 Bitcoins with 50 INR | 5 INR/BTC  | Fulfilled | Complete order sold to Anubhuti |


### Use Cases :-  

* Users are added 
  * `Anubhuti` is added
 <img width="313" alt="image" src="https://user-images.githubusercontent.com/56643076/212564389-c01dac14-a881-40ac-9c9c-b9d8a3bc53c7.png">  
  * `Akanksha` is added, where they are prompted with an error if the try to use a userId that is already in use
<img width="295" alt="image" src="https://user-images.githubusercontent.com/56643076/212564481-13fe21bd-1213-458e-99b4-590403d72771.png">  
  * `Manju` is added  
<img width="161" alt="image" src="https://user-images.githubusercontent.com/56643076/212564513-3764fae4-99a9-422e-81e1-99acb842563d.png">  

* Users start depositing INR and Bitcoin to their accounts, to start trading  
  * `Anubhuti` with `id` = `123` deposits money  
<img width="191" alt="image" src="https://user-images.githubusercontent.com/56643076/212564620-4ac4e82c-6d21-4d90-9287-d4bfc9c07f41.png">  
  * `Akanksha` with `id` = `1234` deposits money  
<img width="149" alt="image" src="https://user-images.githubusercontent.com/56643076/212564701-38d508eb-fca1-4536-adb3-0069ef67a41c.png"> 
  * `Manju` with `id` = `12` deposits money 
<img width="137" alt="image" src="https://user-images.githubusercontent.com/56643076/212564728-70864ad3-a36e-45b5-834a-f1234fd90db0.png"> yes
  

