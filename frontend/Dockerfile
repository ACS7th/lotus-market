FROM node:8-alpine AS builder
WORKDIR /frontend
COPY package*.json ./
RUN npm install
COPY . .
# Final stage
FROM node:8-alpine
RUN apk add --no-cache shadow && \
    useradd -ms /bin/bash frontuser
WORKDIR /app
COPY --from=builder /frontend /app
USER frontuser

ENTRYPOINT ["node", "app.js"]


