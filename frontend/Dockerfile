FROM node:8
RUN useradd -ms /bin/bash frontuser
WORKDIR /frontend

COPY package*.json ./
RUN npm install

COPY . .
USER frontuser

ENTRYPOINT ["node", "--inspect=9229", "app.js"]

