FROM jenkins/jenkins:lts

# Switch to root to install system tools
USER root

# Install Docker CLI so Jenkins can talk to the Docker engine
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# Fix the 'dubious ownership' issue globally
RUN git config --global --add safe.directory '*'

# Switch back to the jenkins user
USER jenkins