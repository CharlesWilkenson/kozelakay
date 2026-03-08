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
                sshagent(['ec2-ansible-key']) {
                    sh '''
                        ansible-playbook -i inventory.ini deploy.yml \
                        -u ubuntu \
                        -e "ansible_ssh_common_args='-o StrictHostKeyChecking=no'"
                    '''
                }
            }
        }
    }
}