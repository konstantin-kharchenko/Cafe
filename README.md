# Cafe. 
The client makes an Order for lunch (chooses from the menu) and indicates the time
when he would like to receive an order. The system shows the price of the Order and
offers to pay from a client account or in cash upon receipt
order. Points are awarded to the client for pre-orders
loyalty. If the Client places an order and does not pick it up, then the points
loyalty is removed up to its blocking. The client can evaluate
every order and leave feedback. The administrator manages the menu,
sets/removes bans/bonuses/points for Clients.

### The client can:
+ create an order,
+ delete order,
+ edit order,
+ add products from the shopping cart to the order,
+ remove products from an order,
+ remove products from cart,
+ add products to cart,
+ change profile,
+ change language.

### Administrator can:
+ create an ingredient,
+ edit ingredient,
+ remove an ingredient,
+ create a product,
+ delete a product,
+ change the product,
+ add ingredients from the basket to the product,
+ remove ingredients from a product,
+ add game items to cart,
+ remove ingredients from cart,
+ approve requests from new administrators,
+ change language,
+ confirm customer orders,
+ do not confirm customer orders,
+ release client.


This project was created in order to demonstrate the interaction of the client and the server, the creation of a connection pool, mappers, the operation of the MVC application, knowledge and understanding of different levels of the application, such as: Controllers, Services, DAO.


![image](https://user-images.githubusercontent.com/51529773/192051361-48446ca7-00f9-4e46-bd81-562acecb2b62.png)

This diagram shows in detail how the application process works, starting from the client and ending with the database.
From the very beginning, an HTTP request is made on the client side, after which, thanks to the servlet, it is caught and placed in the controller, which calls a certain command, depending on what the client asked us to do. This command calls the services that contain the main application logic, after which the services call different DAO and the database is accessed already in the DAO.
How it works? First, each method of the DAO class receives a Connection from the Connection Pool in order to access the database.
Due to the fact that accessing the database is a very long and resource-intensive process, a connection pool is needed. It already contains several connections that are in the queue for use by DAO methods of objects. The connection pool is created at the beginning of the application startup.
After the Connection is received, a SQL request is made to our database. The result is returned and placed in the Mapper.
Mapper extracts data from the received result, creates this or that Entity and fills it with data.
After that, the Entity returns to the Service, the Service returns the Entity to the command, and there the Entities are put in the request and the request is sent to the client.

### Below is the schema of the tables in my database.
![image](https://user-images.githubusercontent.com/51529773/192054306-b34fb363-b2f0-4201-a3c6-f02c97156387.png)


## Applied technologies:
+ MVC
+ Java EE
+ JSP
+ Mail Sender
+ Localization
+ Password encoding
+ MySQL
+ Bootstrap
