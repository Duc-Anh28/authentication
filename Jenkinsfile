pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose -f docker-compose.yml up --build -d'
                sh 'docker-compose -f docker-compose.yml up --build -d'
            }
        }
    }
}
