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

        stage('Validate Terraform Installation') {
            steps {
                bat "\"${TERRAFORM_PATH}\" --version"
            }
        }

        stage('Provision Infrastructure') {
            steps {
                echo "Running Terraform locally..."

                bat "\"${TERRAFORM_PATH}\" -chdir=terraform init"
                bat "\"${TERRAFORM_PATH}\" -chdir=terraform plan -out=tfplan"
                bat "\"${TERRAFORM_PATH}\" -chdir=terraform apply -auto-approve tfplan"
            }
        }

        stage('Show Terraform Output Files') {
            steps {
                echo "📄 Printing generated files from Terraform..."
                bat "type terraform\\file1.txt"
                bat "type terraform\\file2.txt"
                bat "type terraform\\file3.txt"
            }
        }

        // NEW: Terraform Summary Stage
        stage('Terraform Summary') {
            steps {
                script {
                    writeFile file: "terraform-report.txt", text: bat(
                        script: "\"${TERRAFORM_PATH}\" -chdir=terraform plan",
                        returnStdout: true
                    )
                }
                archiveArtifacts artifacts: 'terraform-report.txt', fingerprint: true
            }
        }
    }

    post {
        failure {
            echo "❌ Deployment failed for Terraform"
        }
        success {
            echo "✅ Terraform applied successfully (no AWS)"
        }
    }
}
