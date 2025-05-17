@Library('Jenkins-Shared-Lib') _

pipeline {
    agent {
        kubernetes {
            yaml jenkinsAgent([
                'agent-java-21': 'registry.runicrealms.com/jenkins/agent-java-21:latest'
            ])
        }
    }

    environment {
        PROJECT_NAME = 'Menus'
        ARTIFACT_NAME = 'menus'
        REGISTRY = 'registry.runicrealms.com'
        REGISTRY_PROJECT = 'library'
    }

    stages {
        stage('Send Discord Notification (Build Start)') {
            steps {
                discordNotifyStart(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
            }
        }
        stage('Determine Environment') {
            steps {
                script {
                    def branchName = env.GIT_BRANCH.replaceAll(/^origin\//, '').replaceAll(/^refs\/heads\//, '')
                    echo "Using normalized branch name: ${branchName}"

                    if (branchName == 'dev') {
                        env.RUN_MAIN_DEPLOY = 'false'
                    } else if (branchName == 'main') {
                        env.RUN_MAIN_DEPLOY = 'true'
                    } else {
                        error "Unsupported branch: ${branchName}"
                    }
                }
            }
        }
        stage('Build and Publish Client Artifact') {
            steps {
                container('agent-java-21') {
                    dir('client') {
                        script {
                            sh "./gradlew clean build --no-daemon"
                            nexusPublish()
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            discordNotifySuccess(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
        failure {
            discordNotifyFail(env.PROJECT_NAME, env.GIT_URL, env.GIT_BRANCH, env.GIT_COMMIT.take(7))
        }
    }
}
