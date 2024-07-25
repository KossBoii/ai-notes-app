# AI Notes Application

## Prerequisites
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Run Application
Before running the application, please make sure that Docker Service or Docker Desktop is currently running. Next run the following command and fill in your OpenAI API key in `.env` file

```bash
cp .env.example .env
```

Then, run the following command to start all the docker services needed for the application:

```bash
docker compose up --build --remove-orphans
```

**Note:** Under the hood, it will build the 2 containers (1 for the app and 1 for the MySQL database). 

- For the app, it will automatically copy all the source code and build it with `gradle`. 

- For the MySQL database, the container already initializes the database with 2 default users for testing (admin & user). You can further check the default credentials in `app/src/main/java/com/ai_notes_app/demo/runner/DatabaseInitializer.java`. Will remove this later on to ensure the safety of DB.

## Stop Application

To stop the application, simply go to the terminal that have the docker containers running and press `Ctrl+C` a couple of times. To completely stop and remove docker compose containers, network, and volumes, run the following command:

```bash
docker compose down -v
```


## Testing

To manually test the endpoints provided using Swagger, open a browser and access the following address http://localhost:8080/swagger-ui.html. 

- To execute those secured endpoints with the lock sign, you need to have a valid credentials. You can either click on the black lock sign next to the endpoint or click on the green `Authorize` button at the top of the page on the right side.

- In the `Available authorizations` form that will open, provide the admin credentials (admin/admin) or user ones (user/user). Then, click `Authorize` and, finally, click `Close` button;

## Endpoints

The `note-app-api` has the following endpoints:

#### Authentication
| Endpoint                            | Secured | Roles           |
| ----------------------------------- | ------- | --------------- |
| `POST /auth/authenticate`           | No      |                 |
| `POST /auth/signup`                 | No      |                 |

#### Public Endpoints
| Endpoint                            | Secured | Roles           |
| ----------------------------------- | ------- | --------------- |
| `GET /public/numberOfUsers`         | No      |                 |

#### User Management
| Endpoint                            | Secured | Roles           |
| ----------------------------------- | ------- | --------------- |
| `GET /api/users/me`                 | Yes     | `ADMIN`, `USER` |
| `GET /api/users`                    | Yes     | `ADMIN`         |
| `GET /api/users/{username}`         | Yes     | `ADMIN`         |
| `DELETE /api/users/{username}`      | Yes     | `ADMIN`         |

#### Notes
| Endpoint                            | Secured | Roles           |
| ----------------------------------- | ------- | --------------- |
| `PUT /api/notes/update`             | Yes     | `USER`          |
| `POST /api/notes/create`            | Yes     | `USER`          |
| `GET /api/notes/get-notes`          | Yes     | `USER`          |
| `GET /api/notes/get-all-notes`      | Yes     | `ADMIN`         |
| `DELETE /api/notes/delete/{noteId}` | Yes     | `ADMIN`, `USER` |

Note: 
- These endpoints will automatically pick up the user credential (who was calling the endpoint) to execute the command.
- For the `DELETE /api/notes/delete/{noteId}` endpoint, `ADMIN` user is allowed to delete any user's notes. However, each user can only delete the user belong to themselves. 