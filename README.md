# Dish Display Project

This project is a Jetpack Compose Android application that displays a list of dishes fetched from a remote API. The app includes a search bar, filters, and a navigation sidebar. When a dish is selected, a dialog appears at the bottom of the screen with the selected dish details.

## Problem Statement
You have been provided with a design that includes:
- A display of dishes
- A search bar & filter at the top
- A few tabs positioned on the left side

When a dish is chosen, a dialog appears at the bottom of the screen, displaying additional details about the selected dish.

## Features
- Flexible, reusable, and adaptable UI components
- Fetches dish data using Retrofit API
- Displays dish images and names in a list
- Search functionality for filtering dishes
- Sidebar navigation with hardcoded categories
- Responsive layout designed for tablet screens (7-inch tablet)

## Technologies Used
- **Jetpack Compose** for building the UI
- **Retrofit** for making network requests to fetch dish data
- **Coil** for loading images efficiently in Compose
- **Coroutines** for handling background tasks
- **Material Design** for UI components

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/dish-display-project.git
cd dish-display-project
