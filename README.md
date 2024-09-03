# Yelp Database Application

**Assignment 7 by Sean Wotherspoon**

This application connects to Sean Wotherspoon's Yelp database hosted on the SFU Cypress server. It provides a user-friendly interface for interacting with the database, allowing users to search for businesses, review them, search for users, and manage friendships. The steps to use the application are detailed in the sections below.

## Table of Contents

1. [Setup](#setup)
2. [Usage](#usage)
    - [Login](#login)
    - [Business Search](#business-search)
        - [Business Search Results](#business-search-results)
    - [User Search](#user-search)
        - [User Search Results](#user-search-results)
    - [Add a Friend](#add-a-friend)
    - [Review a Business](#review-a-business)

## Setup

To begin using the application, double-click on `assignment.jar`. This will launch the application.

## Usage

### 1. Login

- **Accessing the Menu:** Click on "Options" to reveal the hierarchical menu.
- **User ID:** You will be prompted to enter a valid user ID from the Yelp database.
- **Login:** After entering the ID, click "Login." If the ID is invalid, the application will notify you.

### 2. Business Search

- **Login Requirement:** Ensure you are logged in; this option will only become available once logged in.
- **Search Criteria:** Navigate through the hierarchical menu and select "Business Search."
- **Search Options:** You can search for businesses by name, city, and star rating.
- **Sorting:** Results can be ordered by name, city, or stars.
- **Enabling/Disabling Fields:** You can disable specific search fields by clicking the checkbox associated with each row.
- **Initiate Search:** After entering your search criteria, click "Search" to view the results.

#### 2.1 Business Search Results

- **Viewing Results:** After performing a search, you will be taken to the business search results interface.
- **Displayed Information:** The results show the business ID, name, address, city, and star rating.
- **Navigation:** Use the `<` and `>` arrow buttons to scroll through the results.
- **Add Review:** Click "Add Review" to review a business. This will auto-fill the business ID and user ID, leaving you to enter the star rating.
- **Back Navigation:** Click "Back" to return to the business search page.

### 3. User Search

- **Login Requirement:** Ensure you are logged in; this option will only become available once logged in.
- **Search Criteria:** Navigate through the hierarchical menu and select "User Search."
- **Search Options:** You can search for users by name, review count, and average star rating.
- **Sorting:** Results are automatically ordered alphabetically by name.
- **Enabling/Disabling Fields:** You can disable specific search fields by clicking the checkbox associated with each row.
- **Initiate Search:** After entering your search criteria, click "Search" to view the results.

#### 3.1 User Search Results

- **Viewing Results:** After performing a search, you will be taken to the user search results interface.
- **Displayed Information:** The results show the user ID, name, review count, useful, funny, cool ratings, average stars, and sign-up date.
- **Navigation:** Use the `<` and `>` arrow buttons to scroll through the results.
- **Add Friend:** Click "Add Friend" to send a friend request to a user. The user ID will be auto-filled, and you will only need to confirm.
- **Back Navigation:** Click "Back" to return to the user search page.

### 4. Add a Friend

- **Login Requirement:** Ensure you are logged in; this option will only become available once logged in.
- **Adding a Friend:** Navigate through the hierarchical menu and select "Add a Friend."
- **Friend ID:** Enter a valid user ID of the person you want to add as a friend. If the ID is invalid, the application will notify you.

### 5. Review a Business

- **Login Requirement:** Ensure you are logged in; this option will only become available once logged in.
- **Adding a Review:** Navigate through the hierarchical menu and select "Add a Review."
- **Review Details:** You will be prompted to enter a valid business ID and a star rating. If the input is invalid, the application will notify you.
