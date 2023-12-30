// This is how we declare a function in groovy.
def LintChecks(COMPONENT) {
    sh "echo Installing JSlint"
    sh "echo lint checks completed for ${COMPONENT}"
}


def call(COMPONENT) {
    pipeline {
        agent any
        // environment {
        //     SONAR = credentials ('SONAR')
        //     sonar_URL= "172.31.27.120"
        // }
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
            stage('Artifact Validation On Nexus') {
                when { 
                    expression { env.TAG_NAME != null } 
                    }
                steps {
                    sh "echo checking whether artifact exists of not. If it doesnt exist then only proceed with Preparation and Upload"
                    script {
                        env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true" )
                        print UPLOAD_STATUS
                    }
                }
            }

            stage('Preparing the artifact') {
                when { 
                    expression { env.TAG_NAME != null } 
                    expression { env.UPLOAD_STATUS == "" }
                    }
                steps {
                    sh "npm install"
                    sh "zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                    sh "ls -ltr"
                }
            }
            stage('Uploading the artifact') {
                when { 
                    expression { env.TAG_NAME != null } 
                    expression { env.UPLOAD_STATUS == "" }
                    }
                steps {
                    sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
                }
            }
        } // End of Stages
    }
}
    }
}
}