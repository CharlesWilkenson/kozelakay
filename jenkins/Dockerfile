FROM jenkins/jenkins:lts

USER root

# Install Docker CLI and other essentials
RUN apt-get update && \
    apt-get install -y docker.io maven ansible openssh-client && \
    rm -rf /var/lib/apt/lists/*

# IMPORTANT: Add the jenkins user to the docker group
RUN groupadd -g 999 docker && \
    usermod -aG docker jenkins

# Fix the 'dubious ownership' issue
RUN git config --global --add safe.directory '*'

USER jenkins