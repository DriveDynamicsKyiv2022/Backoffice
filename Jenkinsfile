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
                sh 'sudo -S scp -v -o StrictHostKeyChecking=no -i ~/backoffice-bastion-key-pair-personal.pem target/backoffice-0.0.1-SNAPSHOT.jar src/main/resources/docker-compose.yml ' +
                        'src/main/resources/DockerFile_app ubuntu@ec2-52-59-255-130.eu-central-1.compute.amazonaws.com:~'
                sh 'sudo -S ssh -i ~/backoffice-bastion-key-pair-personal.pem ubuntu@ec2-52-59-255-130.eu-central-1.compute.amazonaws.com'
                sh 'ls -la'
                sh 'sudo su'
                sh 'for container_id in $(docker ps -a);' +
                        'do docker kill $container_id;' +
                        'done'
                sh 'docker-compose -f docker-compose.yml build --no-cache'
                sh 'docker-compose -f docker-compose.yml up -d'
                sh 'exit'
                sh 'exit'
                sh 'exit'
            }
        }
    }
}
