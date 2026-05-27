# CurrencyCalculator – Documentation

## Overview

The CurrencyCalculator is a Swing-based GUI application that converts between EUR and USD.
It serves as an educational exercise demonstrating:

- Swing GUI construction (`JFrame`, `BorderLayout`, `GridLayout`)
- Lambda expressions as `ActionListener` implementations
- Anonymous inner classes and their access to outer-class state
- Interface-based dependency injection (Strategy Pattern)

---

## Package Structure

```
de.dhbwka.java.exercise.ui.currency
├── CurrencyCalculator.java             – Main JFrame application
├── ExchangeRateSource.java             – Interface for rate data sources
├── ExchangeRateSourceLocal.java        – Reads rates from local JSON snapshot
├── ExchangeRateSourceService.java      – Fetches rates from open.er-api.com
└── exchangeRateSourceLocal.json        – Bundled exchange rate snapshot (base: USD)
```

---

## Architecture

```
CurrencyCalculator (JFrame)
    │
    │  constructor injects
    ▼
ExchangeRateSource  ◄──────────────── interface
    ├── ExchangeRateSourceLocal       reads  exchangeRateSourceLocal.json
    └── ExchangeRateSourceService     fetches https://open.er-api.com/v6/latest/USD
```

---

## Class Reference

### `ExchangeRateSource` — interface

```java
public interface ExchangeRateSource {
    public default double eur();  // returns the EUR rate relative to USD
}
```

The `default` implementation returns `0.2` (acts as a last-resort fallback).
Both concrete implementations override this method.

---

### `ExchangeRateSourceLocal`

Reads the bundled `exchangeRateSourceLocal.json` file and returns the EUR rate.

**File path (relative to project root):**
`src/de/dhbwka/java/exercise/ui/currency/exchangeRateSourceLocal.json`

**How it works:**

1. Reads the entire JSON file as a `String` with `Files.readString()`.
2. Splits the text on `"ETB":` and `"FJD":` to isolate the section containing `"EUR":` (alphabetically between ETB and FJD).
3. Extracts the numeric value via further `split(":")` and `split(",")` calls — no JSON library required.
4. Throws a `RuntimeException` wrapping any `IOException` if the file cannot be read.

---

### `ExchangeRateSourceService`

Fetches a live rate from the public [Open Exchange Rates API](https://open.er-api.com).

**Endpoint:** `https://open.er-api.com/v6/latest/USD`

**Example response (abbreviated):**
```json
{
  "base_code": "USD",
  "rates": {
    "EUR": 0.8926,
    ...
  }
}
```

**How it works:**

1. Sends a GET request using `java.net.http.HttpClient`.
2. Throws an `IOException` if the HTTP status code is not 200.
3. Parses the `"rates"` block into a `HashMap<String, Double>` via string splitting (no JSON library).
4. `eur()` returns `map.get("EUR")`.

---

### `CurrencyCalculator` (JFrame)

The main application window. Receives an `ExchangeRateSource` via its constructor
(dependency injection / Strategy Pattern).

#### Fields

| Field | Type | Description |
|-------|------|-------------|
| `inputField` | `JTextField` | Where the user types amounts and sees results |
| `exchangeRateSource` | `ExchangeRateSource` | The injected rate provider |

#### GUI Layout

```
┌─────────────────────────────────┐
│  [      inputField (NORTH)    ] │  ← right-aligned JTextField
├──────────┬──────────┬───────────┤
│ EUR→USD  │ USD→EUR  │  Cancel   │  ← GridLayout(1,3) in SOUTH panel
└──────────┴──────────┴───────────┘
Window size: 360 × 80 px
```

#### Event Handlers

**`EUR → USD` button — Lambda expression (STUFE I / III)**

```java
eurToUsdButton.addActionListener(e -> {
    double eur = Double.parseDouble(inputField.getText());
    double usd = eur * exchangeRateSource.eur();
    inputField.setText(String.format("%.2f", usd));
});
```

Parses the field value as EUR, multiplies by the current rate, and writes the result
back formatted to two decimal places.

**`USD → EUR` button — Lambda expression (STUFE I / III)**

```java
usdToEurButton.addActionListener(e -> {
    double usd = Double.parseDouble(inputField.getText());
    double eur = usd / exchangeRateSource.eur();
    inputField.setText(String.format("%.2f", eur));
});
```

Parses the field as USD, divides by the rate to get EUR.

**`Cancel` button — Anonymous inner class (STUFE II)**

```java
cancelButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        inputField.setText("");
        CurrencyCalculator.this.inputField.requestFocus();
    }
});
```

Clears the input field and returns focus to it. Uses the explicit outer-class reference
`CurrencyCalculator.this` to demonstrate how inner classes resolve name conflicts with
the enclosing instance.

#### Error Handling

If the text in `inputField` cannot be parsed as a `double`, a `NumberFormatException`
is caught and `showError()` displays a German modal dialog:

> **Fehler** — Bitte geben Sie eine gültige Zahl ein.

#### `main` method — Source selection via CLI argument

```java
public static void main(String[] args) {
    ExchangeRateSource source;
    if (args.length == 0) {
        source = new ExchangeRateSourceLocal();
    } else {
        source = new ExchangeRateSourceService();
    }

    ExchangeRateSource finalSource = source;
    SwingUtilities.invokeLater(() -> {
        CurrencyCalculator calculator = new CurrencyCalculator(finalSource);
        calculator.setVisible(true);
    });
}
```

`SwingUtilities.invokeLater` ensures the window is created on the Event Dispatch Thread (EDT).

---

## How to Run

**With local rates (default):**
```
java de.dhbwka.java.exercise.ui.currency.CurrencyCalculator
```

**With live API rates:**
```
java de.dhbwka.java.exercise.ui.currency.CurrencyCalculator live
```
Any non-empty argument triggers the service source.

---

## Design Patterns

| Pattern | Where |
|---------|-------|
| **Strategy** | `ExchangeRateSource` interface + two implementations; swappable at startup |
| **Dependency Injection** | `CurrencyCalculator` receives its `ExchangeRateSource` via constructor |
| **Anonymous Inner Class** | Cancel button listener (STUFE II) |
| **Lambda / Functional Interface** | EUR↔USD button listeners (STUFE I & III) |

---

## Key Java Concepts Demonstrated

### Lambda expressions
Lambdas compress `ActionListener` anonymous classes to a single expression:
```java
button.addActionListener(e -> doSomething());
```
`ActionListener` is a functional interface (one abstract method), so a lambda satisfies it.

### Anonymous inner classes
An anonymous class is a class definition and object creation merged into one statement:
```java
new ActionListener() {
    @Override public void actionPerformed(ActionEvent e) { ... }
}
```

### Outer-class reference from inner class
Non-static inner classes hold an implicit reference to their enclosing instance.
When field names shadow each other, the outer instance is accessed via `Classname.this`:
```java
CurrencyCalculator.this.inputField.requestFocus();
```

### `exchangeRateSourceLocal.json` format

The file uses USD as the base currency. A snippet:
```json
{
  "base_code": "USD",
  "rates": {
    "EUR": 0.8926,
    ...
  }
}
```
`ExchangeRateSourceLocal` returns this value directly as the EUR rate.
