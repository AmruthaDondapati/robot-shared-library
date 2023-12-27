// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing JSlint"
    sh "echo lint checks completed for ${COMPONENT}"
}

def SonarChecks(COMPONENT) {
    sh "echo Checking the Quality Checks"
    sh "sonar-scanner -Dsonar.host.url=http://172.31.28.17:9000 -Dsonar.projectKey=$("COMPONENT") -Dsonar.sources=. -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
}
// Calling the info function and supplying both the values. 
// LintChecks("DevOps", "DevOpsTraining.com")

def call(COMPONENT) {
    pipeline {
        agent any
        environment {
            Sonar = credentials ('SONAR')
            sonar_URL= "172.31.28.17"
        }
        stages {
            stage ('LintChecks') {
                steps {
                    script {
                        LintChecks(COMPONENT)
                    }
                }
            }
            stage ('SonarChecks') {
                steps {
                    script {
                        SonarChecks(COMPONENT)
                    }
                }
            }
        }
    }
}