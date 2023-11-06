pipeline {
    agent any
    stages {
        stage ('clone') {
            steps {
                git branch: 'dev', url: 'https://github.com/ruairin/ruairispetitions.git'
            }
        }
        stage ('build') {
            steps {
                sh 'mvn clean:clean'
                sh 'mvn dependency:copy-dependencies'
                sh 'mvn compiler:compile'
            }
        }
        stage ('test') {
          steps {
            sh 'mvn test'
          }
        }
        stage ('package') {
          steps {
            sh 'mvn package'
          }
        }
        stage ('deploy') {
            steps {
                sh 'docker build -f Dockerfile -t ruairispetitions .'
                sh 'docker rm -f "ruairispetitions_container" || true'
                sh 'docker run --name ruairispetitions_container -p 9090:8080 ruairispetitions --detach ruairispetitions:latest
            }
        }
    }
    post {
      success {
        archiveArtifacts allowEmptyArchive: true,
        artifacts: '**/ruairispetitions.war'
      }
    }
}
