CREATE TABLE Operation (
    id SERIAL PRIMARY KEY,
    amount REAL,
    type VARCHAR(20),
    date DATE,
    saving_account_number VARCHAR(255) REFERENCES SavingAccount(account_number) ON DELETE CASCADE,
    current_account_number VARCHAR(255) REFERENCES CurrentAccount(account_number) ON DELETE CASCADE,
    employee_id INT REFERENCES Employee(registernumber) ON DELETE CASCADE
);

CREATE TABLE Mission (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE SavingAccount (
    account_number VARCHAR(255) PRIMARY KEY,
    balance REAL,
    creation_date DATE,
    status VARCHAR(20),
    interest_rate DECIMAL(5, 2),
    client_id INT REFERENCES Client(id) ON DELETE CASCADE ON UPDATE CASCADE,
    employee_id INT REFERENCES Employee(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE CurrentAccount (
    account_number VARCHAR(255) PRIMARY KEY,
    balance REAL,
    discovery REAL,
    creation_date DATE,
    status VARCHAR(20),
    client_id INT REFERENCES Client(id) ON DELETE CASCADE ON UPDATE CASCADE,
    employee_id INT REFERENCES Employee(id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    birthDate DATE,
    phone INT
);

CREATE TABLE client (
    code VARCHAR(255) PRIMARY KEY,
    address VARCHAR(255)
    FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE,

);

CREATE TABLE employee (
    registerNumber VARCHAR(255) PRIMARY KEY,
    recruitmentDate DATE,
    email VARCHAR(255)
    FOREIGN KEY (userid) REFERENCES user(id) ON DELETE CASCADE,
);


