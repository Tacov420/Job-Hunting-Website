FROM node:latest

WORKDIR /app

COPY ./frontend ./
RUN yarn

EXPOSE 5173

ENV VITE_API_URL="http://localhost:8005/api"
