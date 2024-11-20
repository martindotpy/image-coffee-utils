# Config git
git config --global --add safe.directory /workspaces/image-coffee-utils || echo "Error: git config"

# Build java microservices
./mvnw clean install -DskipTests
