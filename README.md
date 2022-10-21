# Ticketmaster-Search-App
This Android App connects with Ticketmaster API and retrieve a list of events



https://user-images.githubusercontent.com/929504/197297077-faab2c2e-1615-44ee-9075-da71cd60ea8f.mp4



### Technologies Used:
- Android SDK
- MVVM Pattern:
   - View: Layout and Fragments
   - ViewModel: To handle the business logic and update the UI with liveData attributes.
   - Repository: which handle the logice where the data is coming from (LocalStorage, or RemoteStorage)
   - LocalStorage: Is responsible to extract or Save data into the cache or local database. In this case, into the secured SharedPreferences.
   - RemoteStorage: Is responsible to make the API calls using retrofit instance and handle the error responses.
- Databinding - To update the UI whenever any LiveData changes
- BindginAdapter - To run complex logic whenever the LiveData updates in order to update de UI
- Navigation Graph - To navigate between screens. For now, this app only contains 1 Fragment.
- SharedPreferences - To store the last success response data
- Hilt - Dependency Injection, I have created 1 Hilt Components: MainModule. This is a component installed in SingletonComponent to be used in the application context. Here this module is providing `LocalStorage`, `RemoteStorage`, `NetworkManager`, `Retrofit` instance and much more. 
- Glide: To load and render external images from URL
- Retrofit: To make API calls to ticketMaster API
- OkHttp Interceptor: To intercept all api calls, then attach the apikey into the query params.
  
- Testing:
  - Implemented 4 test cases using robolectric and Mockito.
    - TicketMasterRepositoryTest (2 Test Cases)
    - EventsListViewModelTest (2 Test Cases)
  
### Bottom line:
- There are some pendings TODO, to complete te UI.
- This project does not include any Automation test, due to lack of time, I was not able to do it. But still planning to do it in a next iteration.
