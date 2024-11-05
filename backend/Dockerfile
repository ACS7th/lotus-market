# Use a lightweight Node.js image (alpine version)
FROM node:8-alpine AS builder

# Set working directory and install dependencies
WORKDIR /backend
COPY package*.json ./
RUN npm install --production

# Copy the rest of the application source code
COPY . .

# Use a minimal Node.js image for running the app (production stage)
FROM node:8-alpine

# Set working directory and copy only necessary files from builder stage
WORKDIR /backend
COPY --from=builder /backend .

# Set a non-root user for better security
RUN adduser -D backuser
USER backuser

# Run the application
CMD ["node", "app.js"]


