pipeline {
    agent any

    parameters {
        choice(name: 'TOOL', choices: ['Terraform', 'Ansible', 'Helm'], description: 'Select provisioning tool')
    }

    environment {
        AWS_ACCESS_KEY_ID = "${env.AWS_ACCESS_KEY_ID}"
        AWS_SECRET_ACCESS_KEY = "${env.AWS_SECRET_ACCESS_KEY}"
        KUBECONFIG = "${env.KUBECONFIG}"
        TERRAFORM_EXE = "C:\\Users\\abdir\\Downloads\\terraform_1.13.5_windows_386\\terraform.exe"
        PATH = "${env.TERRAFORM_EXE};C:\\Tools\\Helm;%PATH%"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/GROUPE5AOOP/T1OOP-2.git'
            }
        }

        stage('Setup') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        bat "\"${env.TERRAFORM_EXE}\" --version"
                    } else if (params.TOOL == 'Ansible') {
                        bat 'wsl ansible --version'
                    } else if (params.TOOL == 'Helm') {
                        bat 'helm version'
                    }
                }
            }
        }

        stage('Provision Infrastructure') {
            steps {
                script {
                    if (params.TOOL == 'Terraform') {
                        bat """
                        \"${env.TERRAFORM_EXE}\" -chdir=terraform init
                        \"${env.TERRAFORM_EXE}\" -chdir=terraform plan -out=tfplan
                        \"${env.TERRAFORM_EXE}\" -chdir=terraform apply -auto-approve tfplan
                        """
                    } else if (params.TOOL == 'Ansible') {
                        bat '''
                        wsl cd /mnt/c/ProgramData/Jenkins/.jenkins/workspace/jj/ansible
                        wsl ansible-playbook -i hosts setup.yml
                        '''
                    } else if (params.TOOL == 'Helm') {
                        bat '''
                        helm upgrade --install myapp ./helm/myapp
                        '''
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
