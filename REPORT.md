# University of Regina - ENSE 375 - Software Testing and Validation
## Universal Smart Parking Billing System (SPBS)

**Team Members:**
* [Member 1 Name] ([Student ID])
* [Member 2 Name] ([Student ID])

---

## 1. Introduction
This report documents the design, architecture, and comprehensive testing suite for the Universal Smart Parking Billing System (SPBS). Campus parking management frequently involves distinct pricing zones, varying stall features (such as block-heater plug-in outlets), and duration limits. Current physical parking meters are location-bound, creating inconvenience for users who must walk back to specific lots to extend parking time. 

The SPBS project addresses this issue by providing a centralized Java terminal application using Model-View-Controller (MVC) architecture. The focus of this project is to apply software testing methodologies—including path testing, data flow testing, integration testing, boundary value analysis, decision tables, state transitions, and use cases—using JUnit to ensure software reliability.

---

## 2. Design Problem

### 2.1 Problem Definition
Campus parking management utilizes various distinct parking zones, including underground parkades, standard surface lots, economy outer lots, and outdoor stalls equipped with block-heater electrical plugs. Currently, physical terminal meters are location-bound. Drivers who do not use a mobile app and need to extend their parking duration have to physically walk back to the specific terminal meter tied to their parking lot to pay, causing significant inconvenience—especially during harsh weather or while attending class. 

This project focuses on building a Java terminal application using Model-View-Controller (MVC) architecture to allow cross-zone billing and systematically validate its core business logic using comprehensive JUnit test suites.

### 2.2 Design Requirements

#### 2.2.1 Functions
* **Calculate** parking fees dynamically based on duration, parking zone tier, and plug-in power usage.
* **Validate** license plate format and input parameters before issuing or extending a ticket.
* **Update** parking spot states (`EMPTY`, `OCCUPIED`, `RESERVED`, `PAYMENT_PENDING`) in real time.
* **Process** cross-zone ticket extensions from any terminal location without altering the original spot assignment.
* **Generate** payment receipts and audit log records for administrative reporting.

#### 2.2.2 Objectives
* **Accurate:** Ensure zero calculation errors across all tiered fee calculations and peak-hour multipliers.
* **Reliable:** Prevent system crashes or invalid state overrides during spot updates.
* **Efficient:** Minimize processing steps so terminal ticket issuance completes instantly.
* **Maintainable:** Structure source code cleanly using MVC separation to simplify unit testing in JUnit.
* **User-Friendly:** Provide clear command-line prompts and error messages for driver interaction.

#### 2.2.3 Constraints
* **Constraint 1 (Language & Framework):** The application must be implemented in Java and tested using JUnit.
* **Constraint 2 (Architecture):** Code management must strictly follow the Model-View-Controller (MVC) pattern.
* **Constraint 3 (Reliability / Access Control):** The system must not allow a ticket duration to be extended beyond the lot's maximum allowable limit (e.g., max 4 hours for underground).
* **Constraint 4 (Economic / Security):** Unpaid transactions must revert spot status to `EMPTY` or `EXPIRED` within a timeout threshold without applying charges.
* **Constraint 5 (Sustainability):** The system must track electrical plug usage separately for block-heater stalls to regulate winter energy consumption metrics.
