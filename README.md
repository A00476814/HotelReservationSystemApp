# InnQuest

InnQuest is a hotel reservation ANDROID app designed as part of Assignment under course MCDA 5550.

## Features

- **Hotel Search**: Users can search for hotels by specifying their check-in and check-out dates, the number of guests, and the number of rooms required.
- **Validation System**: InnQuest ensures all input data is correct and logical before processing the search, enhancing user experience and data integrity.
- **Dynamic Hotel Listings**: Based on the search criteria, users are presented with a list of available hotels. This list includes essential details such as hotel name, price, and availability. (For now list is fetched from dummy data inside the code.)

## Validations

InnQuest incorporates the following validations:

- **Name Validation**: The name of the primary guest should only contain alphabets to maintain data quality.
- **Date Validations**:
  - The check-in date cannot be before the current date.
  - The check-out date must be at least one day after the check-in date to ensure a logical stay duration.
- **Guest and Room Validations**:
  - The total number of guests and the number of rooms have logical constraints ensuring the maximum guests allowed per room is 2, prompting users to adjust their room requirements accordingly.

## How to Use

1. **Starting the Search**: Upon launching InnQuest, users are greeted with a search page where they can enter their details.
2. **Viewing Results**: After filling in the search criteria and passing the validation checks, users can view a list of available hotels.
3. **Making a Selection**: Users can browse the hotel listings(Selecting a listing will come in later releases).

## Technical Details

- **Platform**: Android
- **Programming Language**: Java
- **Primary Components**: Fragments, RecyclerView for listing hotels, DatePicker for selecting dates, ScrollView for list, Contraint View for layout adjustments.
- **Databse**: The Application uses MSSQL Databse to store the hotels and reservation informations.


