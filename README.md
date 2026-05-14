# Flipkart SuperCoin Reward & Wallet System
Java-based in-memory wallet and reward management system implementing FIFO coin deduction, expiry handling, refunds, transaction tracking, and tier-based rewards.

## Features
- Earn SuperCoins
- Spend coins using FIFO
- Coin expiry management
- Refund support
- Transaction tracking
- Auto tier upgrade
- In-memory implementation

## Tech Stack
- Java
- Collections Framework
- OOPs
- Exception Handling

## How to Run
Run Driver.java

===============
### Overview
Flipkart wants to build a loyalty program where users earn "SuperCoins" for their purchases. Customers can later use these SuperCoins to claim discounts on future orders. The system needs to manage user wallets, enforce different reward rules based on customer membership tiers, and ensure transactions are tracked securely with proper internal state management.
For the sake of simplicity, this will be driven by manual commands via a driver class or test cases. Time will be simulated using an integer representing the "Current Day" (e.g., Day 1, Day 15, Day 40) to test coin expiry logic.
Terminologies
User: The end-user making purchases on Flipkart.
Tier: The membership level of the User.
Wallet: The entity holding the user's active SuperCoin balance.
Transaction: The internal ledger entry for adding, deducting, or expiring SuperCoins.
Pre-requisites
Do not use any database or NoSQL store, use only in-memory data-structures for now.
Do not create any UI for the application.
Write a driver class for demo purposes. Which will execute all the commands at one place in the code and have test cases to test multiple scenarios.
Please prioritize code compilation, execution, and completion.
Work on the must-have features first followed by bonus capabilities
Expectations:
Make sure that you have working and demonstrable code.
Make sure that the code is functionally correct. The expiry and deduction logic must perfectly follow a FIFO (First-In, First-Out) approach.
Code should easily accommodate new requirements with minimal changes
Code should have proper error handling & exceptions 
Separation of concerns should be addressed, internal state transitions should not be exposed publicly.

### Requirements
Must Have Features
Ability to login/logout. At any point in time, only a single user should be logged into the system.
A user should be able to Earn (Credit) SuperCoins for a purchase.
REGULAR Tier Rule: Earns 2 coins for every 100 Rs spent. Maximum 50 coins per order.
PLUS Tier Rule: Earns 4 coins for every 100 Rs spent. Maximum 100 coins per order.
Expiry Rule: All newly earned SuperCoins have a strict validity of 30 days from the day they are earned (e.g., earned on Day 1, expires on Day 31).
A user should be able to Spend (Debit) SuperCoins to get a discount.
FIFO Rule: The system MUST deduct SuperCoins using a First-In, First-Out approach. The oldest valid coins must be burned first.
System must check for sufficient valid balance. If insufficient, throw an exception.
A user should be able to Cancel an Order (Refund Coins).
If the user had spent (debited) SuperCoins on this specific orderId, that exact amount must be credited back to the user's wallet.
Expiry Rule for Refunds: These refunded coins are treated as a new batch with a fresh validity of 30 days from the day of cancellation.
Every Credit, Debit, or Expiry action must be internally tracked via a Transaction entity.
Possible Values of Transaction Status: PENDING, IN_PROGRESS, COMPLETED, REJECTED, ROLLED_BACK.
A user should be able to view:
Their current valid SuperCoin balance.
Their current Tier.
The list of all their Transactions (Transaction History/Ledger).
Bonus Capabilities
Auto-Tier Upgrade: If a REGULAR user's cumulative lifetime earned coins (regardless of whether they were spent or expired) cross 300, they are automatically upgraded to the PLUS tier.
Transaction Rollback: Create a simulateFailedSpend() method that mimics a failure where a debit transaction fails at the IN_PROGRESS stage. The exact deducted coin batches should be safely restored to the user's wallet with their original expiry dates, and the transaction marked as ROLLED_BACK.




### Sample Test Cases

preloadUsers([{name: "u1", tier: "REGULAR"}, {name: "u2", tier: "PLUS"}])

setCurrentDay(1)
login("u1")
earnCoins({ orderId: "O-101", orderAmount: 3000 }) // OP:  Earned 50 coins (Expires Day 31) Txn State: COMPLETED Balance: 50.

setCurrentDay(10)
earnCoins({ orderId: "O-102", orderAmount: 5000 }) // Earned: 50, balance: 100

setCurrentDay(15)
spendCoins({ orderId: "O-103", coinsToSpend: 30 }) // Spent: 30, Balance: 70


setCurrentDay(35)
checkBalance()  // 50 , 20 expired from Day1 , TIER: REGULAR

spendCoins({ orderId: "O-104", coinsToSpend: 100 }) // Failed

spendCoins({ orderId: "O-105", coinsToSpend: 40 })

setCurrentDay(36)
cancelOrder("O-105")

viewTransactionHistory()
/* Expected Output:
   [Day 1] CREDIT 50 (O-101) - COMPLETED
   [Day 10] CREDIT 50 (O-102) - COMPLETED
   [Day 15] DEBIT 30 (O-103) - COMPLETED
   [Day 35] EXPIRY 20 - COMPLETED
   [Day 35] DEBIT 100 (O-104) - REJECTED
   [Day 35] DEBIT 40 (O-105) - COMPLETED
   [Day 40] REFUND 40 (O-105) - COMPLETED
*/

logout()
login("u2")
earnCoins({ orderId: "O-201", orderAmount: 2000 })
checkBalance()


