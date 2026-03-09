pipeline {
    agent any // Runs directly on the Jenkins container

    stages {
        stage('Checkout-Code') {
            steps {
                echo 'Cloning repo...'
                git branch: 'main', url: 'https://github.com/CharlesWilkenson/kozelakay.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Compiling and Packaging with Maven...'
                // 'mvn' is already inside your custom Dockerfile!
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Execute Playbook (Deploy)') {
            steps {
                echo 'Starting Ansible Deployment...'
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ansible-key',
                                                 keyFileVariable: 'SSH_KEY',
                                                 usernameVariable: 'SSH_USER')]) {
                    // 'ansible-playbook' is also already inside your custom Dockerfile!
                    sh """
                        ansible-playbook -i inventory.ini deploy.yaml \
                        -u ${SSH_USER} \
                        --private-key=${SSH_KEY} \
                        --ssh-common-args='-o StrictHostKeyChecking=no'
                    """
                }
            }
        }
    }
}