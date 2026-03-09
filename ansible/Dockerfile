FROM cytopia/ansible:latest

# Install OpenSSH client (for deployment) and OpenJDK 17 + Maven (for building)
RUN apk add --no-cache openssh-client openjdk17 maven

# Set working directory
WORKDIR /ansible