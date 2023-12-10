FROM node:latest

WORKDIR /app

COPY ./frontend ./
RUN yarn

EXPOSE 5173

ENV VITE_API_URL="http://35.221.223.176:8005/api"
