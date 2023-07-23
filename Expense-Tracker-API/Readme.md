# Expense-Tracker-API




### Feature
```
- User
    1. User can create new Account.
    2. User can sign In existing Account.
    3. Signed In User can add product.
    4. Signed In User can get product by date .
    5. Signed In User can add daily expenses.
    6. Signed In User can get weekly expenses details.
    7. Signed In User can get monthly expenses details.
    8. Signed In User can get  expenses details in a particular date range.
    9. Signed In User can delete expense by Id.
    7. Signed In User can update his contact details (e.g:email,phone)
    8. Signed In User can changed his Account Password.
    9. Signed In User can logout from Account.
    10.Signed In User can delete own Account.

```

### Technologies Used
```
- Programming Language: Java
- Web Framework: SpringBoot
- Database : The project utilizes the MySQL database for data storage.
- Hibernate (for ORM)
- Maven (for dependency management)
- Deployed on AWS server.
```
### Data flow in Project
```
 Controller
    1.UserController
  Service
    1. UserService
    2. ProductService
    3. AuthenticationService
    4. ExpenseService
  Repository
    1. IUserRepo
    2. IProductRepo
    3. IAuthenticationTokenRepo
    4. IExpenseRepo
  Model
    1. User
    2. Product 
    3. AuthenticationToken
    4. Expense

```

### Validation Rules
- User Email should be a normal Email like - ("something@something.com")


    
## Entity Relationships
- **Product** and **User** have a **Many-to-One mapping**. Many Products can be added by a single User, but each Product belongs to only one User.
- **Authentication Token** and **User** have a **One-to-One** mapping. Each token can have  associated with one User, and each User can be linked to only one Token at a time.
- **Expense** and **User** have a **Many-to-One mapping**. Many Expenses can be added by a single User, but each Expense belongs to only one User.
- **Expense** and **Product** have a **Many-to-Many mapping**. Many Expenses can be linked with a single Product,And also many Products can be linked with a single Expense.
###  Conclusion
This is an Expense-Tracker-API project that consists of many entities:  User, Product,Expense .From this project a valid User can do the CRUD operations with the Expenses and Products.
In this project I used passwordEncryptor which save the password in database in hashCode.
This Project provide a email Authentication when a user sign in his Account , a Token received that user mail.This Token will be valid when till user is sign in his Account after sign out the token will be expired.
With the help of token the valid user can do CRUD operations .
The project utilizes different types of mappings between these entities to establish relationships and functionality within the system.
And this project is deployed on a Public Server(AWS).

### Author
 👨‍💼 **Ankit Kumar**
 + Github : [Ankit kumar](https://github.com/ankitk55?tab=repositories)
 + Linkdin : [Ankit Kumar](https://www.linkedin.com/in/ankit-kumar-7300581b3/)
 
### 🤝 Contributing
Contributions, issues and feature requests are Welcome!\
Feel free to check [issues Page](https://github.com/issues) 

### Show Your Support 
 Give a ⭐ if this project help you!