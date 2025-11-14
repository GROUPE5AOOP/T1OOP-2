pipeline {
    agent any

    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select provisioning tool')
    }

    environment {
        AWS_ACCESS_KEY_ID = "${env.AWS_ACCESS_KEY_ID}"
        AWS_SECRET_ACCESS_KEY = "${env.AWS_SECRET_ACCESS_KEY}"
        KUBECONFIG = "${env.KUBECONFIG}"
        TERRAFORM_HOME = "C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386"
        HELM_HOME = "C:\\Tools\\Helm"
        PATH = "${env.TERRAFORM_HOME};${env.HELM_HOME};${env.PATH}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/GROUPE5AOOP/T1OOP-2.git'
            }
        }

        stage('Validate Jenkinsfile Encoding') {
            steps {
                script {
                    try {
                        def content = readFile(file: 'Jenkinsfile', encoding: 'UTF-8')
                        echo "✅ Jenkinsfile encoding OK. Length: ${content.length()} chars"
                    } catch (Exception e) {
                        error "❌ Jenkinsfile encoding invalid. Ensure UTF-8 without BOM. Build stopped."
                    }
                }
            }
        }

        stage('Validate Tool Installation') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        if (!fileExists("${env.TERRAFORM_HOME}/terraform.exe")) {
                            error "❌ Terraform not found in ${env.TERRAFORM_HOME}"
                        }
                        bat '"%TERRAFORM_HOME%\\terraform.exe" --version'
                    } else if (params.TOOL == 'Helm') {
                        if (!fileExists("${env.HELM_HOME}/helm.exe")) {
                            error "❌ Helm not found in ${env.HELM_HOME}"
                        }
                        bat '"%HELM_HOME%\\helm.exe" version'
                    } else if (params.TOOL == 'Ansible') {
                        def status = bat(script: 'wsl ansible --version', returnStatus: true)
                        if (status != 0) {
                            error "❌ Ansible not installed or misconfigured in WSL"
                        }
                        echo "✅ Ansible detected in WSL"
                    }
                }
            }
        }

     stage('Provision Infrastructure') {
    steps {
        script {
            if (params.TOOL == 'Terraform') {
                bat '"C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\deploy_terraform.bat"'
            } 
            // Ansible and Helm stages remain the same
        }
    }
}

        }
    }

    post {
        success {
            echo "✅ Infrastructure deployed successfully using ${params.TOOL}"
        }
        failure {
            echo "❌ Deployment failed for ${params.TOOL}"
        }
    }
}
