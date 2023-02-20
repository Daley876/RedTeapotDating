# RedTeapotDating

##App Description/Functionality:
 - This app allows the user retrieve a list of profiles belonging to different users from an endpoint ("/users") and display those profiles in the UI.
 - The profiles are displayed individually and can be cycled through by pressing navigation buttons on the screen.
 - The app will display the user profile data (name, gender, photo, hobbies, school and about) in the order specified in the "/config" endpoint.

##How To Use the App:
 - After launching the app, there will be a button on the bottom of the screen that reads "Find Matches"
 - When the "Find Matches" button is tapped, the first user profile in the list retriedved will be shown on the screen
 - User can see the next profile in the list tapping the "->" button
 
 ##Technologies And Libraries Used
  - Retrofit: 2.9.0
  - converter-gson: 2.9.0
  - glide: 4.12.0
  - glide-compiler: 4.12.0
  - viewmodel-ktx: 2.5.1
  - livedata-ktx: 2.5.1
  - ViewBinding
  - Coroutines
  - recyclerview: 1.2.1
