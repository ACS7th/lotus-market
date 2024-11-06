#build stage
FROM node:8-alpine AS builder
WORKDIR /backend
COPY . .
RUN npm install

#final stage
FROM node:8-alpine
RUN apk add --no-cache shadow && \
    useradd -ms /bin/bash backuser
RUN mkdir -p /app
WORKDIR /app
COPY --from=builder  /backend /app
COPY --from=builder  /backend/node_modules ./node_modules
RUN chown -R backuser:backuser /app && chmod -R 755 /app
USER backuser
ENTRYPOINT ["node", "app.js"]
