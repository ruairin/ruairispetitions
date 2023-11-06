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
    }
    post {
      success {
        archiveArtifacts allowEmptyArchive: true,
        artifacts: '**/ruairispetitions.war'
      }
    }
}
