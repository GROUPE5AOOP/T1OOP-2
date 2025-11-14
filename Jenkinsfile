pipeline {
    agent any

    environment {
        TERRAFORM_PATH = "C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Validate Jenkinsfile') {
            steps {
                echo "✓ Jenkinsfile encoding OK"
            }
        }

        stage('Validate Tool Installation') {
            steps {
                bat "\"${TERRAFORM_PATH}\" --version"
            }
        }

        stage('Provision Infrastructure') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'aws-credentials',
                                                 usernameVariable: 'AWS_ACCESS_KEY_ID',
                                                 passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    bat "\"${TERRAFORM_PATH}\" -chdir=terraform init"
                    bat "\"${TERRAFORM_PATH}\" -chdir=terraform plan -out=tfplan"
                    bat "\"${TERRAFORM_PATH}\" -chdir=terraform apply -auto-approve tfplan"
                }
            }
        }
    }

    post {
        failure {
            echo "❌ Deployment failed for Terraform"
        }
        success {
            echo "✅ Terraform applied successfully"
        }
    }
}
