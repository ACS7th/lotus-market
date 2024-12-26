#!/bin/bash

find . -type d -name "k8s" | while read -r k8s_dir; do
  echo "Applying resources in: $k8s_dir"
  kubectl apply -f "$k8s_dir"
done

echo "All Kubernetes resources have been applied."
