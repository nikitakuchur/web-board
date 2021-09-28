# Web Board
This is a simple whiteboard application.
You can create boards and draw something on them with your friends.

![Imgur](https://i.imgur.com/Tp7lbS4.gif)

The main page of the application

![Imgur](https://i.imgur.com/xVpwulC.png)

Please note that this project is not finished yet (and idk when it will be finished).

## How to Setup
You can find two versions of the docker-compose file in the project: `docker-compose.yml` and `docker-compose-dev.yml`.
The dev version allows you to debug application (port 8787), deploy (port 9990), and update frontend code at the runtime.

To run the default version, just execute
1. Build backend
```bash
mvn
```
2. Don't forget to change the default username and password for the database in the `.env` file.

3. Run application
```bash
dokcer-compose up
```

If you need the dev version, follow the instructions below.
1. Build backend
```bash
mvn
```
2. Install frontend dependencies
```bash
cd frontend
npm install 
```
3. Run application
```bash
docker-compose -f docker-compose-dev.yml up 
```

After running the application go to http://localhost:8080/.
