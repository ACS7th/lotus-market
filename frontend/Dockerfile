FROM node:8-alpine AS builder
WORKDIR /frontend
COPY . .
RUN npm install

# Final stage
FROM node:8-alpine
RUN apk add --no-cache shadow && \
    useradd -ms /bin/bash frontuser
RUN mkdir -p /app
WORKDIR /app
COPY --from=builder /frontend /app
COPY --from=builder /frontend/node_modules ./node_modules
RUN chown -R frontuser:frontuser /app && chmod -R 755 /app
USER frontuser
ENTRYPOINT ["node", "app.js"]
