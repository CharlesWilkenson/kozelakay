#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#COPY target/cms-project-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Start with the Ansible image you are already using
FROM cytopia/ansible:latest
# Add any specific ansible collections or python libs you need
RUN ansible-galaxy collection install community.docker
# Install Maven and OpenJDK (Java)
RUN apk add --no-cache maven openjdk17

# Switch to root to install packages
#USER root

# Install OpenJDK 17 (Required for modern Jenkins agents)
# We use 'apk' because the cytopia image is based on Alpine Linux
# 1. Install OpenJDK and OpenSSH
#RUN apk update && \
#    apk add --no-cache openjdk17-jre openssh && \
#    # 2. Prepare SSH folder
#    mkdir -p /var/run/sshd && \
#    # 3. Generate host keys (required for SSH)
#    ssh-keygen -A && \
#    # 4. Set root password (or you can use authorized_keys)
#    echo 'root:password123' | chpasswd && \
#    # 5. Allow root login via SSH
#    sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# 6. Expose the standard SSH port
#EXPOSE 22

# 7. Start SSH service in the background
#CMD ["/usr/sbin/sshd", "-D"]

# (Optional) If you need Maven as well, add it here:
# RUN apk add --no-cache maven

# Switch back to the default user if the image requires it
#USER ansible