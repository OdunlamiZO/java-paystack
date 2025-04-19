# Java Paystack

[![codecov](https://codecov.io/gh/OdunlamiZO/java-paystack/graph/badge.svg?token=EL4WAAORI2)](https://codecov.io/gh/OdunlamiZO/java-paystack)

A fully-featured Java SDK for interacting with [Paystack](https://paystack.com)'s RESTful APIs. This SDK enables developers to integrate Paystack's payment services seamlessly into Java-based applications, providing simple, strongly-typed methods for initializing and verifying transactions, managing customers, handling subaccounts, and more.

Whether you're building a backend service, desktop application, or microservice architecture, this SDK is designed to provide an idiomatic Java experience when communicating with Paystack's platform.

## Requirements

- Java 17 or higher
- Maven (for dependency management)

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>odunlamizo</groupId>
  <artifactId>java-paystack</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## Getting Started

### Setup

```java
Paystack paystack = new PaystackOkHttp("YOUR_API_KEY");
```

### Example: Calling the API

Here's a basic example of using the SDK to call Paystack's `resolve account` endpoint:

```java
Response<AccountDetails> response = paystack.resolveAccount("9036678078", "999992");
System.out.println(response);
// Response(code=200, status=true, message=Account number resolved, data=AccountDetails(accountName=ZACCHAEUS OLUWATOSIN ODUNLAMI, accountNumber=9036678078, bankId=171), meta=null)
```

## Contributing

We welcome contributions to improve this SDK! To contribute:

1. **Fork** the repository.
2. **Create a new branch** for your feature or bugfix.
3. Make your changes and write appropriate tests.
4. **Open a Pull Request (PR)** to the `main` branch with a clear description of your changes.

> 📌 Please ensure your code adheres to the project's style and passes all tests before submitting a PR.

