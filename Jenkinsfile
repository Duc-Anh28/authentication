pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                 sh 'mvn clean install -T 1C -Dmaven.test.skip=true'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose -f docker-compose-development.yml up --build -d'
            }
        }
    }
}
