FROM node:latest as build-stage
WORKDIR /app
ARG VUE_APP_AUTH0_DOMAIN
ENV VUE_APP_AUTH0_DOMAIN=$VUE_APP_AUTH0_DOMAIN
ARG VUE_APP_AUTH0_CLIENT_ID
ENV VUE_APP_AUTH0_CLIENT_ID=$VUE_APP_AUTH0_CLIENT_ID
COPY package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM nginx
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
