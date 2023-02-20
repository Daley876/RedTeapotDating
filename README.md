#### RedTeapotDating


##App Description/Functionality:
 - This app allows the user retrieve a list of profiles belonging to different users from an endpoint ("/users") and display those profiles in the UI.
 - The profiles are displayed individually and can be cycled through by pressing navigation button on the screen.
 - The app will display the user profile data (name, gender, photo, hobbies, school and about) in the order specified in the response from the "/config" endpoint.



##How To Use the App:
 - After launching the app, there will be a button on the bottom of the screen that reads "Find Matches".
 - When the "Find Matches" button is tapped, the first user profile in the list retrieved will be shown on the screen
 - User can see the next profile in the list by tapping the "->" button to the lower right of the screen



##How The App Works

- This project uses MVVM architecture style including a repository to execute API calls using Retrofit. The response is represented in models.

- The Model classes are:
  > ProfileConfig: represents the order of how user information is displayed and is retrieved from "/config" endpoint.
  
  > User: represents the profile information of a single user.
  
  > UsersInfo: represents a list of User objects and is retrieved from "/users" endpoint.

- Retrofit is used to make Calls to the API endpoints mentioned above through functions in a Service interface. The Retrofit instance uses the Singleton pattern to ensure only one instance of it exists in the app. API calls are triggered from a repositroy which is done using Coroutines so that the main UI thread is not blocked.

- Once the response JSON is transformed and ready to use, the data is set to LiveData variables within the repository which are observed from the ViewModel. The ViewModel stores the observed values from the repository in its own LiveData variables which are observed from our Fragment.

- There is a single Activity and Fragment in the app. The Activity calls the Fragment upon app launch. This fragment has a RecyclerView layout in its related .xml file which serves to take the Users list in UsersInfo and display it in the order specified in ProfileConfig. An Adapter is used to manage and determine which layouts are inflated to which position on the screen. 

- The Adapter is created with a companion object of constants of Int value which represents each layout to be inflated. The layouts here refer to the different user profile sections (name, gender, school etc). As some user information can be missing, there is an empty layout option to be used in those cases. The only exception is for cases where the profile photo is missing. In this case, we will upload a default photo to the UI based on the gender of the currentUser.  

- When the fragment is first called, it does certain initalizations which include the RecyclerView, it's Adapter and the ViewModel. When changes in data retreieved from the web service is reflected in the ViewModel, those changes are observed from the Fragment. The Fragment will take the first user in the UsersInfo.users list (at index 0) and call a method in the Adapter which takes that user's data (which is referred to as the "currentUser") and display it to the UI. 

- Each time the user navigates to the next profile in the UsersInfo.users list, the index is incremeted by 1 and that user becomes the new "currentUser" which is then sent to the adapter to be displayed in the RecyclerView. The index and current user information is all processed and handled within the View Model. Once the current user is determined, this value is observed from the Fragment which is then communicated to the Adapter 



 ##Technologies, Patterns And Libraries Used
  - Retrofit: 2.9.0
  - converter-gson: 2.9.0
  - glide: 4.12.0
  - glide-compiler: 4.12.0
  - viewmodel-ktx: 2.5.1
  - livedata-ktx: 2.5.1
  - Adapter
  - Observer
  - Singleton
  - Data Binding (1-way)
  - Coroutines
  - Animation
  - recyclerview: 1.2.1
  
  
