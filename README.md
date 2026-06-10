# Bank Account CLI

A command-line banking application written in Java that allows users to register, log in, and manage their bank account.

## Features

- **Sign up** — Create a new account with a starting balance of $0
- **Log in** — Authenticate with an existing account and restore your balance
- **Deposit** — Add funds to your account
- **Withdraw** — Remove funds with insufficient balance protection
- **Transfer** — Send money to another registered account
- **Persistent storage** — Balances are saved to a local file and restored on next login

## Project Structure

```
src/
├── Main.java          # Entry point, wires dependencies together
├── BankAccount.java   # Account data model (owner, balance, auth state)
├── BankService.java   # Banking operations (deposit, withdraw, transfer)
├── AuthService.java   # Authentication (login, sign up)
└── UserInterface.java # CLI menus
```

## How to Run

**Requirements:** Java 14 or higher (uses switch expressions)

**Compile:**
```bash
javac src/*.java -d out/
```

**Run:**
```bash
java -cp out Main
```

## Usage

On launch you will be prompted to log in or sign up:
```
| 1. Log in
| 2. Sign up
Choose an option (1-2):
```

Once authenticated, you can choose a banking operation:
```
****************
BANK APP
****************
OPTIONS
| 1. Deposit
| 2. Withdraw
| 3. Transfer
Choose an option (1-3):
```

## Data Storage

Account data is stored locally in `database.txt` in the following format:
```
john,150.0
mary,320.5
```
The file is created automatically on first run.
