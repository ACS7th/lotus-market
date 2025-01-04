#!/bin/bash

# Step 1: Add ngrok GPG key and repository
echo "Adding ngrok GPG key and repository..."
curl -sSL https://ngrok-agent.s3.amazonaws.com/ngrok.asc \
    | sudo tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null \
    && echo "deb https://ngrok-agent.s3.amazonaws.com buster main" \
    | sudo tee /etc/apt/sources.list.d/ngrok.list

# Step 2: Update package list and install ngrok
echo "Updating package list and installing ngrok..."
sudo apt update && sudo apt install -y ngrok

# Step 3: Add ngrok authentication token
echo "Adding ngrok authentication token..."
sudo ngrok config add-authtoken 2qk7EveLUE1LcxDa2eoIDlcvnut_3HVmyn4KU8QnSN5A7CAqt

echo "ngrok installation and configuration complete."

