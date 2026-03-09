pipeline {
    agent none
    stages {
        // STEP 1: Get the code from GitHub
        stage('Checkout-Code') {
            agent any // Use the controller just to pull the files
            steps {
                echo 'Cloning repo...'
                git branch: 'main', url: 'https://github.com/CharlesWilkenson/kozelakay.git'
            }
        }

        // STEP 2: Build and Test using a Docker Container (No install needed!)
        stage('Build & Test') {
            agent {
                docker {
                    image 'maven:3.9-eclipse-temurin-17'
                    // Optional: This speeds up builds by caching dependencies on your host
                    args '-v $HOME/.m2:/root/.m2'
                }
            }
            steps {
                echo 'Compiling, Testing, and Packaging...'
                sh 'mvn clean package -DskipTests'
                // The .jar file is now in the 'target/' folder
            }
        }

        // STEP 3: Deploy using an Ansible Container
        stage('Execute Playbook (Deploy)') {
            agent {
                docker { image 'cytopia/ansible:latest' }
            }
            steps {
                echo 'Starting Ansible Deployment...'
                // 'my-ansible-creds' must match the ID in Jenkins Credentials Provider
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ansible-key',
                                                 keyFileVariable: 'SSH_KEY',
                                                 usernameVariable: 'SSH_USER')]) {
                    sh """
                        ansible-playbook -i inventory.ini deploy.yaml \
                        -u ${SSH_USER} \
                        --private-key=${SSH_KEY} \
                        --ssh-common-args='-o StrictHostKeyChecking=no' \
                        -e 'ansible_ssh_private_key_file=${SSH_KEY}'
                    """
                }
            }
        }
    }
}