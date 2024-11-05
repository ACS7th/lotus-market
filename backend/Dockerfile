# Use base node 8 image from Docker hub
FROM node:8
RUN useradd -ms /bin/bash backuser


WORKDIR /backend

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy rest of the application csource code
COPY . .
USER backuser
# Run app.js with debugging port when container launches
ENTRYPOINT ["node", "--inspect=9229", "app.js"]

# Comment above and uncomment below to run app.js without debugger port when container launches
# ENTRYPOINT ["node", "app.js"]
