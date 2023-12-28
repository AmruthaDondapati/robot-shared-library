// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing maven"
    sh "echo lint checks completed for ${COMPONENT}" 
}

def SonarChecks(COMPONENT) {
    sh "echo checking sonarchecks"
    sh "sonar-scanner -Dsonar.host.url=http://${sonar_URL}:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
}


def call(COMPONENT) {
    pipeline {
        agent any
        environment {
            SONAR = credentials ('SONAR')
            sonar_URL= "172.31.27.120"
        } 
        stages {
            stage ('LintChecks') {
                steps {
                    script {
                        LintChecks(COMPONENT)
                    }
                }
            }
            stage ('Sonarchecks') {
                steps {
                    script {
                        SonarChecks(COMPONENT)
                    }
                }
            }
        }
    }
}