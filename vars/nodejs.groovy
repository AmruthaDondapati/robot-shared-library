// This is how we declare a function in groovy.
def LintChecks(COMPONENT)) {
    sh '''
    echo "Installing JSlint"
    echo "lint checks completed for $COMPONENT" 
    '''
}

// Calling the info function and supplying both the values. 
// LintChecks("DevOps", "DevOpsTraining.com")

def call{COMPONENT} {
    pipeline {
        agent any 
        stages {
            stage ('LintChecks') {
                steps {
                    script {
                        LintChecks(COMPONENT)
                    }
                }
            }
        }
    }
}