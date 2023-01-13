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
                sh 'sudo -S scp -v -o StrictHostKeyChecking=no -i ~/backoffice-bastion-key-pair-personal.pem target/backoffice-0.0.1-SNAPSHOT.jar docker-compose.yml ' +
                        'DockerFile_app ubuntu@ec2-18-156-2-173.eu-central-1.compute.amazonaws.com:~'
                sh 'sudo -S ssh -i ~/backoffice-bastion-key-pair-personal.pem ubuntu@ec2-18-156-2-173.eu-central-1.compute.amazonaws.com \'bash -s\' < update-server.sh'
            }
        }
    }
}
