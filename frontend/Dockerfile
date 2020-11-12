FROM node:15.2.0-alpine3.10

WORKDIR /frontend
COPY ./ ./

RUN npm install --production --silent
RUN npm run build --silent
RUN npm install -g serve --silent

CMD serve -s build
