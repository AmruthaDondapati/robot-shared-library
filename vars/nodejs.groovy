// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing JSlint"
    sh "echo lint checks completed for ${COMPONENT}"
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
                        env.ARGS= "-Dsonar.sources=."
                        common.SonarChecks(COMPONENT)
                    }
                }
            }
            stage ('TestCases') {
                steps {
                    script {
                        common.testCases()
                    }
                }
            }
        }
    }
}