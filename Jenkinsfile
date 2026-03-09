pipeline {
    agent any
    environment {
        // Remplace par tes vraies infos
        DOCKER_IMAGE = "wilkidocker2019/mon-app-springboot:${env.BUILD_NUMBER}"
        REGISTRY_CREDS = 'docker-hub-creds'
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build Artifact') {
            steps { sh 'mvn clean package -DskipTests' }
        }
        stage('Build & Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', REGISTRY_CREDS) {
                        def customImage = docker.build("${DOCKER_IMAGE}")
                        customImage.push()
                        customImage.push('latest') // On garde aussi un tag latest
                    }
                }
            }
        }
        stage('Deploy with Ansible') {
            steps {
                // On passe le tag de l'image en variable à Ansible
              //  sh "ansible-playbook -i inventory.ini deploy.yml -e 'image_name=${DOCKER_IMAGE}'"

                echo 'Starting Ansible Deployment...'
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ansible-key',
                                                 keyFileVariable: 'SSH_KEY',
                                                 usernameVariable: 'SSH_USER')]) {
                    // 'ansible-playbook' is also already inside your custom Dockerfile!
                    sh """
                        ansible-playbook -i inventory.ini deploy.yaml -e 'image_name=${DOCKER_IMAGE}\
                        -u ${SSH_USER} \
                        --private-key=${SSH_KEY} \
                        --ssh-common-args='-o StrictHostKeyChecking=no'
                    """
                }
            }
        }
    }
}