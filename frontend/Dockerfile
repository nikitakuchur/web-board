FROM node:16.10-alpine3.11

WORKDIR /frontend
COPY ./ ./

RUN npm install --production --silent
RUN npm run build --silent
RUN npm install -g serve --silent

CMD serve -s build
