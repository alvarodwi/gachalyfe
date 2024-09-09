FROM nginx:latest
COPY dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d

CMD ["nginx", "-g", "daemon off;"]
