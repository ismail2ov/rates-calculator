# rates-calculator

This is a command-line rate calculation application that allow prospective borrowers to obtain a quote from pool of lenders for 36 month loans.
The application load a list of all the offers being made by the lenders within the system in CSV format and provide as low a rate to the borrower as is posible.

### The application should take arguments in the form:

> cmd> [application] [market_file] [loan_amount]

### Example:

> cmd> quote.exe market.csv 1500

### The application should produce output in the form:

> cmd> [application] [market_file] [loan_amount]

> Requested amount: £XXXX

> Rate: X.X%

> Monthly repayment: £XXXX.XX

> Total repayment: £XXXX.XX


### To generate the JAR file you can use the following Maven command:

> mvn clean package


### The .exe file has been generated with Launch4j