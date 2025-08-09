# Local Dev Setup in IDE
1. Go to `Run Configurations` -> `Edit Configurations` -> `Active Profiles` and add `dev`
2. copy `.env.example` to a new file `.env` in `~/as-2024-exercise/project/project`
3. run `docker compose up project-db` to start the database
4. start the API in your IDE
    -> the API is now running on `localhost:8080`

# Docker Setup
1. copy `.env.example` to a new file `.env` in `~/as-2024-exercise/project/project`
2. run `docker compose up` to start the api and database
    -> the API is now running on `localhost:8081`
