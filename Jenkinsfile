pipeline {
    agent any

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Validate Jenkinsfile Encoding') {
            steps {
                script {
                    def content = readFile('Jenkinsfile')
                    echo "✅ Jenkinsfile encoding OK. Length: ${content.length()} chars"
                }
            }
        }

        stage('Validate Tool Installation') {
            steps {
                script {
                    bat '"C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe" --version'
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    // Initialize Terraform
                    bat "\"C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe\" -chdir=%WORKSPACE%\\terraform init"
                    
                    // Plan Terraform deployment
                    bat "\"C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe\" -chdir=%WORKSPACE%\\terraform plan -out=tfplan"
                    
                    // Apply Terraform deployment
                    bat "\"C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe\" -chdir=%WORKSPACE%\\terraform apply -auto-approve tfplan"
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment completed successfully!"
        }
        failure {
            echo "❌ Deployment failed for Terraform"
        }
    }
}
