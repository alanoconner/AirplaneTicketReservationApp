# ReservationApp
Airplane ticket reservation web-application. I used Vaadin Flow framework for setting the frontend, and  JDBS for backend.
Application connects to the Databases and searches for available tickets depending on user's settings, such as departure, arrival point, and date.


## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.


