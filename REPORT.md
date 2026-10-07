# University of Regina - ENSE 375 - Software Testing and Validation
## Universal Smart Parking Billing System (SPBS)

**Team Members:**
* Ali Abdullah - 200518299
* Abraham Omoregie - 200507536
* Fairuz Nawar - 200515433
* Muhammad Shami - 200491628

---

## Table of Contents
1. [Introduction](#1-introduction)
2. [Design Problem](#2-design-problem)
   * [2.1 Problem Definition](#21-problem-definition)
   * [2.2 Design Requirements](#22-design-requirements)
     * [2.2.1 Functions](#221-functions)
     * [2.2.2 Objectives](#222-objectives)
     * [2.2.3 Constraints](#223-constraints)
3. [Solution](#3-solution)
   * [3.1 Solution 1](#31-solution-1)
   * [3.2 Solution 2](#32-solution-2)
   * [3.3 Final Solution](#33-final-solution)
     * [3.3.1 Components](#331-components)
     * [3.3.2 Environmental, Societal, Safety, and Economic Considerations](#332-environmental-societal-safety-and-economic-considerations)
     * [3.3.3 Test Cases and Results](#333-test-cases-and-results)
     * [3.3.4 Limitations](#334-limitations)
4. [Team Work](#4-team-work)
   * [4.1 Meeting 1](#41-meeting-1)
   * [4.2 Meeting 2](#42-meeting-2)
   * [4.3 Meeting 3](#43-meeting-3)
   * [4.4 Meeting 4](#44-meeting-4)
5. [Project Management](#5-project-management)
6. [Conclusion and Future Work](#6-conclusion-and-future-work)
7. [References](#7-references)
8. [Appendix](#8-appendix)

---

## 1. Introduction
This report documents the design, architecture, and comprehensive testing suites for the Smart Parking Billing System (SPBS). Campus parking system utilizes various distinct parking zones, including underground parkades, standard surface lots, economy outer lots, and block-heater electrical plug equipped lots, that are tied to specific time duration as well. Current physical terminal meters are location-bound which creates an inconvenience for people as they are forced to walk back to the specific lot to extend parking time.

The SPBS project addresses this issue by providing a centralized Java terminal application using Model-View-Controller (MVC) architecture. The focus of this project is to apply software testing methodologies including path testing, data flow testing, integration testing, boundary value testing, equivalence class testing, decision tables testing, state transition testing, and use case testing, using JUnit test suites to ensure reliability.

---

## 2. Design Problem

### 2.1 Problem Definition
Campus parking management utilizes various distinct parking zones, including underground parkades, standard surface lots, economy outer lots, and block-heater electrical plug equipped lots. Currently, physical terminal meters are location-bound. People who do not use a mobile app and need to extend their parking duration have to physically walk back to the specific terminal meter tied to their parking lot to pay, causing significant inconvenience - especially during harsh weathers or while attending class. 

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

---

## 3. Solution
In accordance with the iterative engineering design process, multiple software architectural concepts were evaluated to implement the Universal Smart Parking Billing System (SPBS). Each iteration was assessed based on its ability to satisfy system functions, adhere to binary constraints, and support comprehensive automated unit testing using JUnit.

### 3.1 Solution 1: Inflexible Single-Zone Console Application (Base Draft)
The initial design concept proposed a monolithic Java application where user input, pricing logic, and spot tracking were combined within a single class using static methods. This solution focused exclusively on standard surface lot billing without distinguishing between parking zones or electrical plug usage.

* **Design Description:** A single `Main` class handled terminal prompts, hardcoded fee calculations ($3.00/hour), and basic console output.
* **Testing Evaluation & Rejection:** From a software testing perspective, this design was rejected due to high code coupling. Because business logic (rate calculations) was intertwined with direct user input/output (`Scanner` and `System.out.println`), writing isolated JUnit unit tests without triggering interactive console prompts was impossible. Furthermore, it failed to support cross-zone extensions or variable pricing tiers.

### 3.2 Solution 2: Flexible Multi-Class Architecture (Updated Draft but Pre-MVC)
The second iteration improved upon Solution 1 by separating the project into distinct Java classes (`ParkingLot`, `Vehicle`, `Ticket`) and introducing basic multi-zone pricing logic (Underground, Surface, Economy, and Plug-in stalls).

* **Design Description:** Business logic was refactored out of the main loop into helper classes, allowing basic calculations to be invoked programmatically. Cross-zone terminal selection was introduced, allowing drivers to specify their parking zone.
* **Testing Evaluation & Rejection:** While an improvement, this architecture still lacked a formal Model-View-Controller (MVC) separation. The `ParkingLot` class maintained both data state and user view updates, leading to side effects during state transition tests. Additionally, error handling relied on direct console outputs rather than throwing testable custom exceptions, making automated edge-case verification (such as boundary value time extensions or invalid plate formats) inefficient and brittle. Consequently, a third iteration was required to achieve full testability.
