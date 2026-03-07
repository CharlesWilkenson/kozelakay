pipeline {
    // 1. Target your specific Ansible container node
    agent { label 'ansible-engine' }

    tools {
        maven 'myMaven'
    }

    stages {
        stage('Checkout-Code') {
            steps {
                echo 'Cloning repo.'
                // Using 'checkout scm' is cleaner if your job is already linked to GitHub
                git branch: 'main', url: 'https://github.com/CharlesWilkenson/kozelakay.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Compiling and Testing Code....'
                sh 'mvn clean package' // Combines compile, test, and package
            }
        }

        stage('Execute Playbook (Deploy)') {
            steps {
                echo 'Starting Ansible Deployment...'
                // Use the SSH Agent to inject your EC2 .pem key
                sshagent(['ec2-ansible-key']) {
                    sh '''
                        # Ensure the inventory.ini and deploy.yml are in the root of your repo
                        ansible-playbook -i inventory.ini deploy.yml \
                        -u ubuntu \
                        -e "ansible_ssh_common_args='-o StrictHostKeyChecking=no'"
                    '''
                }
            }
        }
    }
}