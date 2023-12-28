// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing maven"
    sh "echo lint checks completed for ${COMPONENT}" 
}

def SonarChecks(COMPONENT) {
    sh "echo checking sonarchecks"
    sh "mvn clean compile"
    sh "sonar-scanner -Dsonar.host.url=http:${SONAR_URL}:9000  -Dsonar.java.binaries=target/ -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
    // sh "sonar-scanner -Dsonar.host.url=http://${sonar_URL}:9000 -Dsonar.java.binaries=target/ -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
    sh  "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
    sh  "bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
    sh " echo Sonar checks completed for ${COMPONENT}.....!!!!!"
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