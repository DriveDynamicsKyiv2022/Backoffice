pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building now'
                sh 'mvn clean install'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
                sh 'scp -i ~/backoffice-bastion-key-pair-personal.pem /target/backoffice-0.0.1-SNAPSHOT.jar /src/main/resources/docker-compose.yml ' +
                        '/src/main/resources/DockerFile_app ubuntu@ec2-18-184-177-187.eu-central-1.compute.amazonaws.com:~'
                sh 'ssh -i ~/backoffice-bastion-key-pair-personal.pem ubuntu@ec2-18-184-177-187.eu-central-1.compute.amazonaws.com'
                sh 'sudo su'
                sh 'for container_id in $(docker ps -a);' +
                        'do docker kill $container_id;' +
                        'done'
                sh 'docker-compose -f docker-compose.yml build --no-cache'
                sh 'docker-compose -f docker-compose.yml up -d'
                sh 'exit'
                sh 'exit'
            }
        }
    }
}
