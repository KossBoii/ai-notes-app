# AI Notes Application

## Prerequisites
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Run Application
Before running the application, please make sure that Docker Service is currently running. Then, run the following command to start all the docker services needed for the application:

```bash
docker compose up --build --remove-orphans
```

**Note:** Under the hood, it will build the 2 containers (1 for the app and 1 for the MySQL database). 
- For the app, it will automatically copy all the source code and compile/build it with `gradle`. 
- For the MySQL database, 

## Stop Application

To stop the application, simply go to the terminal that have the docker containers running and press `Ctrl+C` a couple of times. To completely stop and remove docker compose containers, network, and volumes, run the following command:

```bash
docker compose down -v
```


## Testing

To manually test the endpoints provided using Swagger, open a browser and access the following address `http://localhost:8080/swagger-ui.html`. 

- To execute those secured endpoints with the lock sign, you need to have a valid credentials. You can either click on the black lock sign next to the endpoint or click on the green `Authorize` button at the top of the page on the right side.

- In the `Available authorizations` form that will open, provide the admin credentials (admin/admin) or user ones (user/user). Then, click `Authorize` and, finally, click `Close` button;


