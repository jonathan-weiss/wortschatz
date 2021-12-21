FROM nginx:alpine
ARG DIST_DIRECTORY_NAME=dist/wortschatz-app

# Set working directory to nginx asset directory
WORKDIR /usr/share/nginx/html

# Remove default nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# Copy static assets from angular dist directory
COPY ${DIST_DIRECTORY_NAME} /usr/share/nginx/html

RUN chmod -R ug=rwx,o=+rx /usr/share/nginx/html

# Containers run nginx with global directives and daemon off
ENTRYPOINT ["nginx", "-g", "daemon off;"]
