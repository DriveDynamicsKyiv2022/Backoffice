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
                sh 'BACKOFFICE_DNS="ec2-54-93-251-1.eu-central-1.compute.amazonaws.com"'
                sh 'echo $BACKOFFICE_DNS'
                sh 'sudo -S scp -v -o StrictHostKeyChecking=no -i ~/backoffice-bastion-key-pair-personal.pem target/backoffice-0.0.1-SNAPSHOT.jar docker-compose.yml ' +
                        'DockerFile_app ubuntu@$BACKOFFICE_DNS:~'
                sh 'sudo -S ssh -i ~/backoffice-bastion-key-pair-personal.pem ubuntu@$BACKOFFICE_DNS \'bash -s\' < update-server.sh'

            }
        }
    }
}
