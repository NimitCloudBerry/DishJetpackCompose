# About

This project is a Jetpack Compose Android application that displays a list of dishes fetched from a remote API. The app includes a search bar, filters, and a navigation sidebar. When a dish is selected, a dialog appears at the bottom of the screen with the selected dish details.

## Image
![Screenshot_20241116_041252](https://github.com/user-attachments/assets/698c5255-273d-4828-9d47-33342d2158a3)
![Screenshot_20241116_041358](https://github.com/user-attachments/assets/8d29ceed-c87d-4595-bd9f-1817b0fc4db5)



## Features
- Flexible, reusable, and adaptable UI components
- Fetches dish data using Retrofit API
- Displays dish images and names in a list
- Sidebar navigation with hardcoded categories
- Responsive layout designed for tablet screens (7-inch tablet)

## Technologies Used
- **Jetpack Compose** for building the UI
- **Retrofit** for making network requests to fetch dish data
- **Coil** for loading images efficiently in Compose
- **Coroutines** for handling background tasks
- **Material Design** for UI components
- **Room Database** for Offline Storage
- **Dagger Hilt** helps in making classes independent of each other by automatically providing dependencies, thus reducing boilerplate code. It prevents the need to manually create objects of the same class repeatedly by managing the lifecycle and instantiation of dependencies for you.

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/dish-display-project.git
cd dish-display-project
