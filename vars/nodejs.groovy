// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing JSlint"
    sh "echo lint checks completed for ${COMPONENT}"
}

def sonarChecks(component){
    sh " echo Starting the quality check..."
    sh " sonar-scanner -Dsonar.host.url=http://${sonar_URL}:9000 -Dsonar.source=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=123 "
    sh " curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
    sh " bash -x quality-gate.sh ${sonar_USR} ${sonar_PSW} ${sonar_URL} ${COMPONENT}"
    sh " echo lint checks completed for ${COMPONENT}.....!!!!!"
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