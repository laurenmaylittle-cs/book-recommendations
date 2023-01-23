FROM node:latest as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM nginx
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
