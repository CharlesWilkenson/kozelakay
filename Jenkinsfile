pipeline{
    agent any
    tools{
        maven 'myMaven'
    }
    stages{
        stage('Checkout-Code'){
          steps {
              echo 'Cloning repo!'
            git branch: 'main',
                url: 'https://github.com/CharlesWilkenson/kozelakay.git'
        }
        }

        stage('Compile Code'){
            steps{
                sh 'mvn compile'
            }
        }

        stage('Test Code'){
            steps{
                sh 'mvn test'
            }
        }

        stage('Package Code'){
            steps{
                sh 'mvn package'
            }
        }

    }
}
