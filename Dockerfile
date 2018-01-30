FROM nginx:1.12-alpine

COPY allure-report /usr/share/nginx/html

EXPOSE 80